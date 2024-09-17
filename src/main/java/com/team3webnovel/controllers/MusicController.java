package com.team3webnovel.controllers;

import com.team3webnovel.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MusicController {

    @Autowired
    private MusicService musicService; // 인터페이스 타입으로 DI

    // GET 요청: 음악 생성 페이지를 보여줌
    @GetMapping("/generate-music")
    public String showGenerateMusicPage() {
        return "generate/generate_music"; // /WEB-INF/views/generate/generate_music.jsp로 이동
    }

    // POST 요청: 사용자가 입력한 정보를 처리하여 음악을 생성
    @PostMapping("/generate-music")
    public String generateMusic(@RequestParam("prompt") String prompt,
                                @RequestParam(value = "make_instrumental", required = false) boolean makeInstrumental,
                                Model model, HttpServletRequest request) {
        try {
            // MusicService를 통해 Suno API로 음악 생성 요청
            String musicUrl = musicService.generateMusic(prompt, makeInstrumental);

            // 생성된 음악 URL을 모델에 추가하여 결과 페이지로 전달
            model.addAttribute("musicUrl", musicUrl);
            return "generate/music_result"; // 음악 결과 페이지로 이동
        } catch (Exception e) {
            // 오류가 발생한 경우 에러 메시지를 모델에 추가
            model.addAttribute("errorMessage", "음악 생성 중 오류가 발생했습니다.");
            return "generate/generate_music"; // 다시 음악 생성 페이지로 이동
        }
    }
}
