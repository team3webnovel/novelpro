package com.team3webnovel.controllers;

import com.team3webnovel.services.MusicService;
import com.team3webnovel.vo.MusicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/generate-music")
    public String showGenerateMusicPage() {
        return "generate/generate_music"; // 음악 생성 페이지로 이동
    }

    @PostMapping("/generate-music")
    public String generateMusic(@RequestParam("prompt") String prompt,
                                @RequestParam(value = "make_instrumental", required = false) boolean makeInstrumental,
                                Model model, HttpServletRequest request) {
        try {
            // 에러 메시지를 담을 맵 생성
            Map<String, String> errorMap = new HashMap<>();

            // MusicService를 통해 Suno API로 음악 생성 요청
            List<MusicVo> musicList = musicService.generateMusic(prompt, makeInstrumental, errorMap);

            // 에러가 있을 경우, 모델에 에러 메시지 추가
            if (errorMap.containsKey("error")) {
                model.addAttribute("errorMessage", errorMap.get("error"));
                return "generate/generate_music";  // 다시 음악 생성 페이지로 이동
            }
            
            // 경고가 있을 경우, 모델에 경고 메시지 추가
            if (errorMap.containsKey("warning")) {
                model.addAttribute("warningMessage", errorMap.get("warning"));
            }

            // 생성된 음악 리스트를 모델에 추가하여 결과 페이지로 전달
            model.addAttribute("musicList", musicList);
            return "generate/music_result"; // 음악 결과 페이지로 이동
        } catch (Exception e) {
            // 예외 메시지를 출력하여 디버깅할 수 있도록 수정
            e.printStackTrace();  // 예외의 스택 트레이스를 출력
            model.addAttribute("errorMessage", "음악 생성 중 오류가 발생했습니다. 에러 메시지: " + e.getMessage());
            return "generate/generate_music"; // 다시 음악 생성 페이지로 이동
        }
    }

    // GET 요청: /storage-music 페이지 (저장된 음악을 가져오는 로직)
    @GetMapping("/storage-music")
    public String showMusicStorage(HttpSession session, Model model) {
    	
        if (!isLoggedIn(session)) {
            return "redirect:/login"; // 세션이 없으면 로그인 페이지로 리다이렉트
        }

        // 세션에서 user_id 가져오기
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "storage/music_storage";
        }

        // userId와 artForm = 1인 음악들을 가져오기
        List<MusicVo> musicList = musicService.getStoredMusicByUserId(userId);

        // 가져온 음악 데이터를 모델에 추가
        model.addAttribute("musicList", musicList);
        return "storage/music_storage"; // 저장된 음악 페이지로 이동
    }

    // POST 요청: 만약 POST 방식으로 데이터를 추가하거나 수정해야 한다면 여기에 작성
    @PostMapping("/storage-music")
    public String updateMusicStorage(HttpSession session, Model model) {
        // POST 요청이 필요할 경우 로직을 여기에 추가 (예: 음악 삭제 또는 업데이트)
        return "redirect:/storage-music";
    }
    
    // 개별 음악 정보 조회
    @GetMapping("/music_detail/{creationId}")
    public String getMusicDetails(@PathVariable("creationId") int creationId, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login"; // 세션이 없으면 로그인 페이지로 리다이렉트
        }
        MusicVo music = musicService.getMusicDetailsByCreationId(creationId);
        model.addAttribute("music", music);
        return "storage/music_detail"; // 상세 정보 페이지로 이동
    }
    
    // 세션 유효성 확인
    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user_id") != null;
    }
    
}
