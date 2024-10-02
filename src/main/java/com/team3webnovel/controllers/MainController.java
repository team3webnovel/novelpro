package com.team3webnovel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
    @RequestMapping({"/", "/main", ""})
    public String showIndexPage() {
    	System.out.print("기제");
        return "index";  // index.jsp로 이동
    }
    
}