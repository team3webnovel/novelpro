package com.team3webnovel.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3webnovel.mappers.MusicMapper;
import com.team3webnovel.vo.MusicVo;

import jakarta.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicServiceImpl implements MusicService {

    private final Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Autowired
    private SqlSession sqlSession;  // MyBatis 세션 주입

    @Autowired
    private HttpSession session;    // 사용자 세션 주입

    @Override
    public List<MusicVo> generateMusic(String prompt, boolean makeInstrumental, Map<String, String> errorMap) throws Exception {
        // 세션에서 user_id 가져오기
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            throw new RuntimeException("로그인한 사용자만 음악 생성을 할 수 있습니다.");
        }
        int artForm = 1;  // music의 art_form은 1로 설정
        logger.debug("세션에서 가져온 user_id: {}", userId);

        MusicMapper mapper = sqlSession.getMapper(MusicMapper.class);

        // 첫 번째 creation 삽입
        Map<String, Object> params1 = new HashMap<>();
        params1.put("userId", userId);
        params1.put("artForm", artForm);
        mapper.insertCreation(params1);

        // 첫 번째 creationId 가져오기
        Integer creationId1 = mapper.getLastCreationId();
        if (creationId1 == null) {
            throw new RuntimeException("첫 번째 Creation ID를 가져오는 데 실패했습니다.");
        }

        // 두 번째 creation 삽입
        Map<String, Object> params2 = new HashMap<>();
        params2.put("userId", userId);
        params2.put("artForm", artForm);
        mapper.insertCreation(params2);

        // 두 번째 creationId 가져오기
        Integer creationId2 = mapper.getLastCreationId();
        if (creationId2 == null) {
            throw new RuntimeException("두 번째 Creation ID를 가져오는 데 실패했습니다.");
        }

        // Python 스크립트 파일 경로 설정
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("suno_functions.py").getFile());
        String pythonScriptPath = file.getAbsolutePath();

        if (!file.exists()) {
            throw new RuntimeException("Python 스크립트 파일을 찾을 수 없습니다: " + file.getAbsolutePath());
        }

        logger.info("Python 스크립트 파일 경로: {}", pythonScriptPath);

        // Python 명령어 실행 리스트 생성
        List<String> command = new ArrayList<>();
        command.add("python");
        command.add(pythonScriptPath);
        command.add(prompt);
        command.add(String.valueOf(makeInstrumental));  // boolean 값을 문자열로 변환

        logger.info("Python 스크립트 실행: {}", command);

        // 서브 프로세스를 통해 Python 스크립트 실행
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);  // stderr과 stdout 병합
        processBuilder.environment().put("PYTHONIOENCODING", "utf-8");

        Process process = processBuilder.start();

        // 프로세스의 출력값을 읽음
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("{") || line.trim().startsWith("[")) {
                output.append(line).append("\n");
            } else {
                logger.info("비JSON 출력 무시: {}", line);
            }
        }

        // 프로세스 종료 대기
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            logger.error("Python 스크립트 실행 실패. 종료 코드: {}", exitCode);
            logger.error("출력 내용: {}", output.toString());
            throw new RuntimeException("Python 스크립트 실행 실패. 종료 코드: " + exitCode);
        }

        logger.info("Python 스크립트 출력: {}", output.toString());

        // JSON 데이터 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(output.toString());

        // 두 개의 creationId와 음악 정보를 music_data 테이블에 각각 삽입
        List<MusicVo> musicList = new ArrayList<>();
        boolean firstMusicGenerated = false;
        boolean secondMusicGenerated = false;

        // 첫 번째 음악 처리
        JsonNode music1 = jsonNode.get(0);
        if (music1 != null) {
            firstMusicGenerated = processMusicData(mapper, music1, creationId1, musicList, errorMap);
        } else {
            logger.error("첫 번째 음악 데이터가 없습니다.");
            errorMap.put("error", "첫 번째 음악 데이터가 없습니다.");
        }

        // 두 번째 음악 처리
        JsonNode music2 = jsonNode.get(1);
        if (music2 != null) {
            secondMusicGenerated = processMusicData(mapper, music2, creationId2, musicList, errorMap);
        } else {
            logger.error("두 번째 음악 데이터가 없습니다.");
            errorMap.put("error", "두 번째 음악 데이터가 없습니다.");
        }

        // 음악 생성 결과 처리
        if (!firstMusicGenerated && !secondMusicGenerated) {
            errorMap.put("error", "음악 생성이 실패했습니다.");
            throw new RuntimeException("음악 생성이 실패했습니다.");
        } else if (firstMusicGenerated && !secondMusicGenerated) {
            errorMap.put("warning", "하나의 음악만 생성되었습니다.");
        } else if (!firstMusicGenerated && secondMusicGenerated) {
            errorMap.put("warning", "하나의 음악만 생성되었습니다.");
        }

        return musicList;
    }

    private boolean processMusicData(MusicMapper mapper, JsonNode music, Integer creationId, List<MusicVo> musicList, Map<String, String> errorMap) {
        String title = music.path("title").asText("제목 없음");
        String lyric = music.path("lyric").asText("가사 없음").replace("\n", "<br/>");
        String audioUrl = music.path("audio_url").asText("URL 없음");
        String imageUrl = music.path("image_url").asText("커버 없음");
        String modelName = music.path("model_name").asText("모델 없음");
        String gptDescriptionPrompt = music.path("gpt_description_prompt").asText("");
        String type = music.path("type").asText("gen");
        String tags = music.path("tags").asText("");
        String errorMessage = music.path("error_message").asText("");

        // 에러 메시지가 있는지 확인
        if (!errorMessage.isEmpty()) {
            logger.error("음악 생성 중 에러 발생: {}", errorMessage);
            errorMap.put("error", errorMessage);
            return false;
        }

        // 음악 데이터 삽입
        Map<String, Object> params = new HashMap<>();
        params.put("creationId", creationId);
        params.put("title", title);
        params.put("lyric", lyric);
        params.put("audioUrl", audioUrl);
        params.put("imageUrl", imageUrl);
        params.put("modelName", modelName);
        params.put("gptDescriptionPrompt", gptDescriptionPrompt);
        params.put("type", type);
        params.put("tags", tags);
        params.put("errorMessage", errorMessage);
        mapper.insertMusicData(params);

        musicList.add(new MusicVo(title, lyric, audioUrl, imageUrl));

        return true;
    }

    @Override
    public List<MusicVo> getStoredMusicByUserId(Integer userId) {
        // MyBatis Mapper 가져오기
        MusicMapper mapper = sqlSession.getMapper(MusicMapper.class);
        // userId와 artForm = 1에 맞는 음악 데이터 가져오기
        return mapper.getMusicByUserIdAndArtForm(userId, 1);  // artForm 1은 음악
    }
    
    @Override
    public MusicVo getMusicDetailsByCreationId(int creationId) {
        MusicMapper mapper = sqlSession.getMapper(MusicMapper.class);
        return mapper.getMusicByCreationId(creationId);
    }
    
}