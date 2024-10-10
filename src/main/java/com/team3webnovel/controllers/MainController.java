package com.team3webnovel.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        genres.add(new GenreVo("로판", "로판"));
        genres.add(new GenreVo("현판", "현판"));
        genres.add(new GenreVo("판타지", "판타지"));
        genres.add(new GenreVo("무협", "무협"));
        genres.add(new GenreVo("로맨스", "로맨스"));
        genres.add(new GenreVo("일반", "일반"));

        // 모델에 장르 리스트 추가
        model.addAttribute("genres", genres);
        List<NovelVo> vo = novelService.getMainNovelList();
        model.addAttribute("novelList", vo);
        
        return "index";  // index.jsp로 이동
    }
    
    @GetMapping("/generate-search")
    public String searchPage() {
    	return "search";
    }
    
    @GetMapping("/generate-search/{index}")
    public String generateSearch(@PathVariable String index, Model model) {
        // novelService를 통해 검색 결과 가져오기
        List<NovelVo> novelList = novelService.searchNovels(index);
        
        // JSP로 검색어와 결과 전달
        model.addAttribute("searchQuery", index);  // 검색어 전달
        model.addAttribute("results", novelList);  // 검색 결과 전달
        
        return "search";  // search.jsp로 이동
    }
    
}