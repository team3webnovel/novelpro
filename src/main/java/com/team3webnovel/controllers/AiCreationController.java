package com.team3webnovel.controllers;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/creation-studio")
public class AiCreationController {

	
    @GetMapping
    public String showCreationStudio(Model model) {
        model.addAttribute("AImessage", "AI 창작 스튜디오에 오신 것을 환영합니다!");
        return "/generate/aiCreationStudio"; // aiCreationStudio.jsp로 이동
    }
    
    @GetMapping("/novel")
    public String sendToNewNovel(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("AImessage", "AI 창작 스튜디오에 오신 것을 환영합니다!");
        return "redirect:/novel/new-novel";
    }
    
    @GetMapping("/image")
    public String goToImage(RedirectAttributes redirectAttributes) {
    	redirectAttributes.addFlashAttribute("AImessage","AI 창작 스튜디오에 오신 것을 환영합니다!");
    	return "redirect:/images";
    }
    
    @GetMapping("/font")
    public String goToFont(RedirectAttributes redirectAttributes) {
    	redirectAttributes.addFlashAttribute("AImessage", "AI 창작 스튜디오에 오신 것을 환영합니다!");
    	return "redirect:/generate-font";
    }
    
    @GetMapping("/music")
    public String goToMusic(RedirectAttributes redirectAttributes) {
    	redirectAttributes.addFlashAttribute("AImessage", "AI 창작 스튜디오에 오신 것을 환영합니다!");
    	return "redirect:/generate-music";
    }
}
