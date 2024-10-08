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
    private HttpSession session;

    @Autowired
    private MusicDao musicDao;
    
    @Override
    public List<MusicVo> generateMusic(String prompt, boolean makeInstrumental, Model model) throws Exception {
        logger.info("음악 생성 시작. Prompt: {}, Instrumental: {}", prompt, makeInstrumental);
        
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            logger.error("로그인한 사용자가 아닙니다.");
            model.addAttribute("errorMessage", "로그인한 사용자만 음악 생성을 할 수 있습니다.");
            return new ArrayList<>();
        }
        Integer userId = user.getUserId();
        logger.debug("세션에서 가져온 user_id: {}", userId);

        int artForm = 1;
        
        try {
            logger.info("Creation 데이터를 DB에 삽입 중...");
            // 첫 번째 creation 삽입
            Map<String, Object> params1 = new HashMap<>();
            params1.put("userId", userId);
            params1.put("artForm", artForm);
            musicDao.insertCreation(params1);

            Integer creationId1 = musicDao.getLastCreationId();
            if (creationId1 == null) {
                logger.error("첫 번째 Creation ID를 가져오는 데 실패했습니다.");
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
                logger.error("두 번째 Creation ID를 가져오는 데 실패했습니다.");
                model.addAttribute("errorMessage", "두 번째 Creation ID를 가져오는 데 실패했습니다.");
                return new ArrayList<>();
            }

            // Python 스크립트 실행
            logger.info("Python 스크립트 실행 준비 중...");
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("suno_functions.py").getFile());
            String pythonScriptPath = file.getAbsolutePath();

            if (!file.exists()) {
                logger.error("Python 스크립트 파일을 찾을 수 없습니다. 경로: {}", pythonScriptPath);
                model.addAttribute("errorMessage", "Python 스크립트 파일을 찾을 수 없습니다.");
                return new ArrayList<>();
            }

            logger.info("Python 스크립트 파일 경로: {}", pythonScriptPath);
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
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                logger.error("Python 스크립트 실행 실패. 종료 코드: {}", exitCode);
                model.addAttribute("errorMessage", "Python 스크립트 실행 실패. 종료 코드: " + exitCode);
                return new ArrayList<>();
            }

            logger.info("Python 스크립트 실행 완료. 출력 내용: {}", output.toString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(output.toString());

            // 두 개의 creationId와 음악 정보를 music_data 테이블에 삽입
            List<MusicVo> musicList = new ArrayList<>();
            boolean firstMusicGenerated = processMusicData(musicDao, jsonNode.get(0), creationId1, musicList, model);
            boolean secondMusicGenerated = processMusicData(musicDao, jsonNode.get(1), creationId2, musicList, model);

            if (!firstMusicGenerated && !secondMusicGenerated) {
                logger.error("음악 생성이 실패했습니다.");
                model.addAttribute("errorMessage", "음악 생성이 실패했습니다.");
            } else if (firstMusicGenerated && !secondMusicGenerated) {
                logger.warn("하나의 음악만 생성되었습니다.");
                model.addAttribute("warningMessage", "하나의 음악만 생성되었습니다.");
            }
            
            if (!firstMusicGenerated) {
                // 첫 번째 음악 생성 실패 시, error_message가 있는지 확인하여 사용자에게 전달
                String errorMessage = jsonNode.get(0).path("error_message").asText();
                if (!errorMessage.isEmpty()) {
                    model.addAttribute("errorMessage", errorMessage);
                }
                
            }
            
            return musicList;
        } catch (Exception e) {
            logger.error("음악 생성 중 예외 발생: ", e);
            model.addAttribute("errorMessage", "음악 생성 중 오류가 발생했습니다: " + e.getMessage());
            return new ArrayList<>();
        } finally {
        	System.err.println("[model contents]: " + model.asMap());
        }
    }

    private boolean processMusicData(MusicDao musicDao, JsonNode music, Integer creationId, List<MusicVo> musicList, Model model) {
        String title = music.path("title").asText("제목 없음");
        String lyric = music.path("lyric").asText("가사 없음").replace("\n", "<br/>");
        String audioUrl = music.path("audio_url").asText("URL 없음");
        String imageUrl = music.path("image_url").asText("커버 없음");
        String errorMessage = music.path("error_message").asText("");

        // 에러 메시지가 있는지 확인
        if (!errorMessage.isEmpty()) {
            logger.error("음악 생성 중 에러 발생: {}", errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return false;
        }

        // 음악 데이터 삽입
        Map<String, Object> params = new HashMap<>();
        params.put("creationId", creationId);
        params.put("title", title);
        params.put("lyric", lyric);
        params.put("audioUrl", audioUrl);
        params.put("imageUrl", imageUrl);
        musicDao.insertMusicData(params);

        logger.info("음악 데이터 저장 완료. Title: {}, Audio URL: {}", title, audioUrl);
        musicList.add(new MusicVo(title, lyric, audioUrl, imageUrl));
        return true;
    }

    @Override
    public List<MusicVo> getStoredMusicByUserId(Integer userId) {
        logger.info("저장된 음악 데이터 가져오기. User ID: {}", userId);
        return musicDao.getMusicByUserIdAndArtForm(userId, 1);  // artForm 1은 음악
    }

    @Override
    public MusicVo getMusicDetailsByCreationId(int creationId) {
        logger.info("Creation ID로 음악 세부 정보 가져오기. Creation ID: {}", creationId);
        return musicDao.getMusicByCreationId(creationId);
    }

}
