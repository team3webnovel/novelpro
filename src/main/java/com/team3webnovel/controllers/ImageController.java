package com.team3webnovel.controllers;

import com.team3webnovel.services.ImageService;
import com.team3webnovel.vo.ImageVo;
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
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/generate-image")
    public String showGenerateImagePage() {
        return "generate/generate_image"; // 이미지 생성 페이지로 이동
    }

    @PostMapping("/generate-image")
    public String generateImage(@RequestParam("prompt") String prompt,
                                @RequestParam(value = "make_high_resolution", required = false) boolean makeHighResolution,
                                Model model, HttpServletRequest request) {
        try {
            // 에러 메시지를 담을 맵 생성
            Map<String, String> errorMap = new HashMap<>();

            // ImageService를 통해 이미지 생성 요청
            List<ImageVo> imageList = imageService.generateImage(prompt, makeHighResolution, errorMap);

            // 에러가 있을 경우, 모델에 에러 메시지 추가
            if (errorMap.containsKey("error")) {
                model.addAttribute("errorMessage", errorMap.get("error"));
                return "generate/generate_image";  // 다시 이미지 생성 페이지로 이동
            }
            
            // 경고가 있을 경우, 모델에 경고 메시지 추가
            if (errorMap.containsKey("warning")) {
                model.addAttribute("warningMessage", errorMap.get("warning"));
            }

            // 생성된 이미지 리스트를 모델에 추가하여 결과 페이지로 전달
            model.addAttribute("imageList", imageList);
            return "generate/image_result"; // 이미지 결과 페이지로 이동
        } catch (Exception e) {
            // 예외 메시지를 출력하여 디버깅할 수 있도록 수정
            e.printStackTrace();  // 예외의 스택 트레이스를 출력
            model.addAttribute("errorMessage", "이미지 생성 중 오류가 발생했습니다. 에러 메시지: " + e.getMessage());
            return "generate/generate_image"; // 다시 이미지 생성 페이지로 이동
        }
    }

    // GET 요청: /storage-image 페이지 (저장된 이미지를 가져오는 로직)
    @GetMapping("/storage-image")
    public String showImageStorage(HttpSession session, Model model) {
    	
        if (!isLoggedIn(session)) {
            return "redirect:/login"; // 세션이 없으면 로그인 페이지로 리다이렉트
        }

        // 세션에서 user_id 가져오기
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "storage/image_storage";
        }

        // userId와 artForm = 2인 이미지들을 가져오기
        List<ImageVo> imageList = imageService.getStoredImageByUserId(userId);

        // 가져온 이미지 데이터를 모델에 추가
        model.addAttribute("imageList", imageList);
        return "storage/image_storage"; // 저장된 이미지 페이지로 이동
    }

    private boolean isLoggedIn(HttpSession session) {
		// TODO Auto-generated method stub
		return false;
	}

	// POST 요청: 만약 POST 방식으로 데이터를 추가하거나 수정해야 한다면 여기에 작성
    @PostMapping("/storage-image")
    public String updateImageStorage(HttpSession session, Model model) {
        // POST 요청이 필요할 경우 로직을 여기에 추가 (예: 이미지 삭제 또는 업데이트)
        return "redirect:/storage-image";
    }
}
