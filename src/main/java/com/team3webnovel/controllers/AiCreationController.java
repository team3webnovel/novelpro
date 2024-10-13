package com.team3webnovel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/creation-studio")
public class AiCreationController {

    @GetMapping
    public String showCreationStudio(Model model) {
        // 필요한 데이터를 모델에 추가
        model.addAttribute("AImessage", "AI 창작 스튜디오에 오신 것을 환영합니다!");

        // AI 창작 스튜디오 페이지로 이동
        return "/generate/aiCreationStudio"; // aiCreationStudio.jsp로 이동
    }
    
    @PostMapping("/novel")
    public String goToNewNovel(Model model) {
    	model.addAttribute("AImessage", "AI 창작 스튜디오에 오신 것을 환영합니다!");
    	return "/storage/new_novel";
    }
    
    @PostMapping("/image")
    public String goToImage(Model model) {
    	model.addAttribute("AImessage","AI 창작 스튜디오에 오신 것을 환영합니다!");
    	return "/generate/image_simple_form";
    }
    
    @PostMapping("/font")
    public String goToFont(Model model) {
    	model.addAttribute("AImessage", "AI 창작 스튜디오에 오신 것을 환영합니다!");
    	return "/generate/generate_font";
    }
}
