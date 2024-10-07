package com.team3webnovel.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team3webnovel.services.NovelService;
import com.team3webnovel.vo.GenreVo;
import com.team3webnovel.vo.NovelVo;

@Controller
public class MainController {
	
	@Autowired
	private NovelService novelService;
	
    @RequestMapping({"/", "/main", ""})
    public String showIndexPage(Model model) {
    	// 장르 리스트를 생성
        List<GenreVo> genres = new ArrayList<>();
        genres.add(new GenreVo("로판", "로맨스판타지"));
        genres.add(new GenreVo("현판", "현대판타지"));
        genres.add(new GenreVo("판타지", "판타지"));
        genres.add(new GenreVo("무협", "무협"));
        genres.add(new GenreVo("로맨스", "로맨스"));

        // 모델에 장르 리스트 추가
        model.addAttribute("genres", genres);
        List<NovelVo> vo = novelService.getMainNovelList();
        model.addAttribute("novelList", vo);
        
        return "index";  // index.jsp로 이동
    }
    
}