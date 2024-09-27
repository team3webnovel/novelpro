package com.team3webnovel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.team3webnovel.dao.NovelDao;

@Controller
public class NovelController {
	
	@Autowired
	private NovelDao boardDao;

    // my_storage.jsp로 이동하는 매핑
    @GetMapping("/my_storage")
    public String showMyStoragePage() {
        // my_storage.jsp 파일을 렌더링
        return "ystest/my_storage";
    }
    
    // 글쓰기 페이지로 이동
    @GetMapping("/write")
    public String showWritePage() {
    	return "ystest/write_novel";
    }
}
