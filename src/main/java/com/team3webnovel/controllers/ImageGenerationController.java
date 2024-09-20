package com.team3webnovel.controllers;

import com.team3webnovel.comfyui.ComfyUIImageGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/images")
public class ImageGenerationController {

    private final ComfyUIImageGenerator comfyUIImageGenerator;

    public ImageGenerationController() {
        this.comfyUIImageGenerator = new ComfyUIImageGenerator();
    }

    // GET 요청으로 JSP 페이지 렌더링
    @GetMapping("/generate")
    public String showGeneratePage() {
        return "generate"; // generate.jsp 페이지로 이동
    }

    // POST 요청으로 프롬프트를 받아 이미지 생성 요청
    @PostMapping("/generate")
    public String generateImage(@RequestParam("prompt") String prompt, Model model) {
        try {
            if (comfyUIImageGenerator.isConnected()) {
                // ComfyUI에 프롬프트 전달
                String response = comfyUIImageGenerator.queuePrompt(prompt);
                model.addAttribute("message", "Image generation request sent successfully: " + response);
            } else {
                model.addAttribute("message", "WebSocket is not connected.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error generating image: " + e.getMessage());
        }
        return "result"; // 결과를 표시할 JSP 페이지로 이동
    }
    
    @GetMapping("/test")
    public String test() {
        return "test"; // generate.jsp 페이지로 이동
    }

}
