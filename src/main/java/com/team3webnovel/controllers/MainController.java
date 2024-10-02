package com.team3webnovel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
    @RequestMapping("/")
    public String showIndexPage() {
    	System.err.println("메인 진입");
        return "index";  // index.jsp로 이동
    }
    
    
}
