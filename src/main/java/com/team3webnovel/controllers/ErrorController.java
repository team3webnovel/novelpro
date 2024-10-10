package com.team3webnovel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String handleError(Model model) {
        // GlobalExceptionHandler에서 설정한 모델 속성을 그대로 사용
        return "error"; // error.jsp로 이동
    }
}
