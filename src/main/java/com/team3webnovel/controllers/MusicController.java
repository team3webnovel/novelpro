package com.team3webnovel.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3webnovel.services.MusicService;
import com.team3webnovel.vo.MusicVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
                                @RequestParam(value = "AImessage", required = false) String AImessage, // AImessage 추가
                                Model model, HttpServletRequest request) {
        
        List<MusicVo> musicList;
        try {
            // MusicService를 통해 Suno API로 음악 생성 요청
            musicList = musicService.generateMusic(prompt, makeInstrumental, model);

            // 에러가 있으면 다시 생성 페이지로
            if (model.containsAttribute("errorMessage")) {
                return "generate/generate_music";
            }
            if (model.containsAttribute("warningMessage")) {
                return "generate/generate_music";
            }

            // 생성된 음악 리스트를 모델에 추가하여 결과 페이지로 전달
            model.addAttribute("musicList", musicList);

            // AImessage가 있는 경우
            if (AImessage != null && !AImessage.isEmpty()) {
                model.addAttribute("AImessage", AImessage);  // AImessage를 모델에 추가
                return "generate/music_result";
            }
            
            if (model.containsAttribute("AImessage")) {
            	model.addAttribute("AImessage", AImessage);
            	return "generate/music_result";
            }
            
            return "generate/music_result"; // 음악 결과 페이지로 이동
            
        } catch (Exception e) {
            e.printStackTrace();  // 예외의 스택 트레이스를 출력
            model.addAttribute("error", "음악 생성 중 오류가 발생했습니다. 에러 메시지: " + e.getMessage());
            return "generate/generate_music"; // 다시 음악 생성 페이지로 이동
        }
    }


    // GET 요청: /storage-music 페이지 (저장된 음악을 가져오는 로직)
    @GetMapping("/storage-music")
    public String showMusicStorage(HttpSession session, Model model) {
    	 // 세션에서 user_id를 가져옴
        UserVo user = (UserVo) session.getAttribute("user");
        // userId와 artForm = 1인 음악들을 가져오기
        List<MusicVo> musicList = musicService.getStoredMusicByUserId(user.getUserId()); // null을 사용하여 전체 데이터를 가져올 수도 있음

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
        MusicVo music = musicService.getMusicDetailsByCreationId(creationId);
        model.addAttribute("music", music);
        return "storage/music_detail"; // 상세 정보 페이지로 이동
    }
    
    @GetMapping("/api/music_detail/{creationId}")
    @ResponseBody
    public ResponseEntity<MusicVo> getMusicDetailsApi(@PathVariable("creationId") int creationId) {
        MusicVo music = musicService.getMusicDetailsByCreationId(creationId);
        if (music != null) {
            return ResponseEntity.ok(music);  // JSON 데이터 반환
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @RequestMapping(value = "/music_detail/{creationId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<MusicVo> getMusicDetail(@PathVariable("creationId") int creationId) {
        MusicVo musicDetail = musicService.getMusicDetailsByCreationId(creationId);
        return ResponseEntity.ok(musicDetail);
    }


    
    // 세션 유효성 확인
    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user_id") != null;
    }
    
}
