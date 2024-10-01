package com.team3webnovel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.team3webnovel.dao.ImageDao;
import com.team3webnovel.dao.NovelDao;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
public class NovelController {
	
	@Autowired
	private NovelDao boardDao;
	
	@Autowired
	private ImageDao imageDao;

    // my_storage.jsp로 이동하는 매핑
    @GetMapping("/my_storage")
    public String showMyStoragePage(HttpSession session, Model model) {
    	UserVo user = (UserVo)session.getAttribute("user");
    	
    	CreationVo vo = new CreationVo();
    	vo.setUserId(user.getUserId());
    	vo.setArtForm(2);
    	imageDao.getImageDataByUserId(vo);
    	
    	model.addAttribute("imageList", imageDao.getImageDataByUserId(vo));
    	
        return "ystest/my_storage";
    }
    
    // 글쓰기 페이지로 이동
    @GetMapping("/write")
    public String showWritePage() {
    	return "ystest/write";
    }
}
