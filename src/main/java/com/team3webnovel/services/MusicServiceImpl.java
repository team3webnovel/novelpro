package com.team3webnovel.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
        int userId = (int) session.getAttribute("user_id");
        int artForm = 1;  // music의 art_form은 1로 설정

        // 생성된 데이터를 creation 테이블에 삽입
        MusicMapper mapper = sqlSession.getMapper(MusicMapper.class);
        mapper.insertCreation(userId, artForm);

        // 현재 작업 디렉토리 (프로젝트 루트 디렉토리)
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("suno_functions.py").getFile());
        String pythonScriptPath = file.getAbsolutePath();
        
        // Python 스크립트 파일 경로 확인
        if (!file.exists()) {
            throw new RuntimeException("Python 스크립트 파일을 찾을 수 없습니다: " + file.getAbsolutePath());
        }
        
        logger.info("Python 스크립트 파일 경로: {}", file.getAbsolutePath());

        // Python 명령어 실행 시 사용할 커맨드 리스트 생성
        List<String> command = new ArrayList<>();
        command.add("python");  // 필요 시 "python3"으로 변경
        command.add(pythonScriptPath);
        command.add(prompt);
        command.add(String.valueOf(makeInstrumental));  // boolean을 문자열로 변환

        logger.info("Python 스크립트 실행: {}", command);  // Python 스크립트 실행 로그 추가

        // 서브프로세스를 통해 Python 스크립트 실행
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);  // stderr을 stdout으로 병합
        processBuilder.environment().put("PYTHONIOENCODING", "utf-8");

        Process process = processBuilder.start();

        // 프로세스 출력값을 읽음
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        // 프로세스 출력 읽기
        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("{") || line.trim().startsWith("[")) {
                output.append(line).append("\n");
            } else {
                logger.info("비JSON 출력 무시: {}", line);  // 비JSON 출력 로그
            }
        }

        // 프로세스가 종료될 때까지 대기
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
        int creationId = mapper.getLastCreationId();

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

            // music_data 테이블에 데이터 삽입
            mapper.insertMusicData(creationId, title, lyric, audioUrl, imageUrl, modelName, gptDescriptionPrompt, type, tags, errorMessage);

            musicList.add(new MusicVo(title, lyric, audioUrl, imageUrl));
        }

        return musicList;
    }
}
