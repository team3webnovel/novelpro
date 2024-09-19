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
    public List<MusicVo> generateMusic(String prompt, boolean makeInstrumental) throws Exception {
        // 세션에서 user_id 가져오기
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            throw new RuntimeException("로그인한 사용자만 음악 생성을 할 수 있습니다.");
        }
        int artForm = 1;  // music의 art_form은 1로 설정
        logger.debug("세션에서 가져온 user_id: {}", userId);

        // 삽입된 데이터를 creation 테이블에 추가하고, 방금 생성된 creation_id를 가져올 준비
        MusicMapper mapper = sqlSession.getMapper(MusicMapper.class);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("artForm", artForm);
        
        // 데이터 삽입 및 생성된 creation_id 반환
        mapper.insertCreation(params);
        

        
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
        
        // 삽입된 creation_id를 가져옴
        Integer creationId = mapper.getLastCreationId();
        if (creationId == null) {
            throw new RuntimeException("Creation ID를 가져오는 데 실패했습니다.");
        }
        
        // 음악 정보를 music_data 테이블에 삽입
     // 음악 정보를 music_data 테이블에 삽입
        List<MusicVo> musicList = new ArrayList<>();
        for (JsonNode music : jsonNode) {
            String title = music.path("title").asText("제목 없음");
            String lyric = music.path("lyric").asText("가사 없음").replace("\n", "<br/>");
            String audioUrl = music.path("audio_url").asText("URL 없음");
            String imageUrl = music.path("image_url").asText("커버 없음");
            String modelName = music.path("model_name").asText("모델 없음");
            String gptDescriptionPrompt = music.path("gpt_description_prompt").asText("");
            String type = music.path("type").asText("gen");
            String tags = music.path("tags").asText("");
            String errorMessage = music.path("error_message").asText("");

            logger.info("Generated music - Title: {}, Lyric: {}, Audio URL: {}, Image URL: {}", title, lyric, audioUrl, imageUrl);

            // 매개변수를 Map으로 묶어서 전달
            Map<String, Object> params2 = new HashMap<>();
            params2.put("creationId", creationId);
            params2.put("title", title);
            params2.put("lyric", lyric);
            params2.put("audioUrl", audioUrl);
            params2.put("imageUrl", imageUrl);
            params2.put("modelName", modelName);
            params2.put("gptDescriptionPrompt", gptDescriptionPrompt);
            params2.put("type", type);
            params2.put("tags", tags);
            params2.put("errorMessage", errorMessage);

            // music_data 테이블에 데이터 삽입
            mapper.insertMusicData(params2);

            musicList.add(new MusicVo(title, lyric, audioUrl, imageUrl));
        }


        return musicList;
    }
}
