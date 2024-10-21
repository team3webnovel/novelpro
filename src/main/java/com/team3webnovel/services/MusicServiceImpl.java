package com.team3webnovel.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3webnovel.dao.MusicDao;
import com.team3webnovel.vo.MusicVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Service
public class MusicServiceImpl implements MusicService {

    private final Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Autowired
    private MusicDao musicDao;
    
    @Autowired
    private HttpSession session;    // 사용자 세션 주입

    @Override
    public List<MusicVo> generateMusic(String prompt, boolean makeInstrumental, Model model) throws Exception {
        // 세션에서 user_id 가져오기
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorMessage", "로그인한 사용자만 음악 생성을 할 수 있습니다.");
            return new ArrayList<>();
        }

        Integer userId = user.getUserId();
        int artForm = 1;
        logger.debug("세션에서 가져온 user_id: {}", userId);

        // 첫 번째 creation 삽입
        Map<String, Object> params1 = new HashMap<>();
        params1.put("userId", userId);
        params1.put("artForm", artForm);
        musicDao.insertCreation(params1);

        Integer creationId1 = musicDao.getLastCreationId();
        if (creationId1 == null) {
            model.addAttribute("errorMessage", "첫 번째 Creation ID를 가져오는 데 실패했습니다.");
            return new ArrayList<>();
        }

        // 두 번째 creation 삽입
        Map<String, Object> params2 = new HashMap<>();
        params2.put("userId", userId);
        params2.put("artForm", artForm);
        musicDao.insertCreation(params2);

        Integer creationId2 = musicDao.getLastCreationId();
        if (creationId2 == null) {
            model.addAttribute("errorMessage", "두 번째 Creation ID를 가져오는 데 실패했습니다.");
            return new ArrayList<>();
        }

        // Python 스크립트 파일 경로 설정
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("suno_functions.py").getFile());
        String pythonScriptPath = file.getAbsolutePath();

        if (!file.exists()) {
            model.addAttribute("errorMessage", "Python 스크립트 파일을 찾을 수 없습니다.");
            return new ArrayList<>();
        }

        List<String> command = new ArrayList<>();
        command.add("python");
        command.add(pythonScriptPath);
        command.add(prompt);
        command.add(String.valueOf(makeInstrumental));

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        processBuilder.environment().put("PYTHONIOENCODING", "utf-8");

        Process process = processBuilder.start();
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
            model.addAttribute("errorMessage", "Python 스크립트 실행 실패. 종료 코드: " + exitCode);
            return new ArrayList<>();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(output.toString());

        // 에러 메시지가 있는지 확인
        if (jsonNode.get(0).has("error_message")) {
            String errorMessage = jsonNode.get(0).get("error_message").asText();
            if (!errorMessage.isEmpty()) {
                logger.error("음악 생성 중 에러 발생: {}", errorMessage);
                model.addAttribute("errorMessage", errorMessage);  // 에러 메시지를 사용자에게 전달
                return new ArrayList<>();
            }
        }

        logger.info("Python 스크립트 출력: {}", output.toString());

        // 두 개의 creationId와 음악 정보를 music_data 테이블에 각각 삽입
        List<MusicVo> musicList = new ArrayList<>();
        boolean firstMusicGenerated = processMusicData(musicDao, jsonNode.get(0), creationId1, musicList, model);
        boolean secondMusicGenerated = processMusicData(musicDao, jsonNode.get(1), creationId2, musicList, model);

        // 음악 생성 결과 처리
        if (!firstMusicGenerated && !secondMusicGenerated) {
            model.addAttribute("errorMessage", "음악 생성이 실패했습니다.");
            return new ArrayList<>();
        } else if (firstMusicGenerated && !secondMusicGenerated) {
            model.addAttribute("warningMessage", "하나의 음악만 생성되었습니다.");
        }

        return musicList;
    }


    private boolean processMusicData(MusicDao musicDao, JsonNode music, Integer creationId, List<MusicVo> musicList, Model model) {
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
            model.addAttribute("error", errorMessage);
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
        musicDao.insertMusicData(params);

        musicList.add(new MusicVo(title, lyric, audioUrl, imageUrl));

        return true;
    }

    @Override
    public List<MusicVo> getStoredMusicByUserId(Integer userId) {
        // userId와 artForm = 1에 맞는 음악 데이터 가져오기
        return musicDao.getMusicByUserIdAndArtForm(userId, 1);  // artForm 1은 음악
    }
    
    @Override
    public MusicVo getMusicDetailsByCreationId(int creationId) {
        return musicDao.getMusicByCreationId(creationId);
    }

}