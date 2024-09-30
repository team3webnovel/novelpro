package com.team3webnovel.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FontController {

    @GetMapping("/generate-font")
    public String generateFontPage(Model model) {
        return "generate/generate_font"; // JSP 파일 경로
    }
    
    @PostMapping("/generate-font")
    public ResponseEntity<String> saveFileName(@RequestBody Map<String, String> request) {
        String fileName = request.get("fileName");
        
        // 파일명 로깅이나 DB 저장 처리 등을 추가
        System.out.println("Received file name: " + fileName);

        // 성공 응답 반환
        return ResponseEntity.ok("File name received");
    }
    
}
