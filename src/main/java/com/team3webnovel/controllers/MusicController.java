package com.team3webnovel.controllers;

import com.team3webnovel.services.MusicService;
import com.team3webnovel.vo.MusicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

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
            // MusicService를 통해 Suno API로 음악 생성 요청
            List<MusicVo> musicList = musicService.generateMusic(prompt, makeInstrumental);
            System.out.println(musicList);
            
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
}
