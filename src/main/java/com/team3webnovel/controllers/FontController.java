package com.team3webnovel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FontController {

    @GetMapping("/generate-font")
    public String generateFontPage(Model model) {
        return "generate/generate_font"; // JSP 파일 경로
    }
}
