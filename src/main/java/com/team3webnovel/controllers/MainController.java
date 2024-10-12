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
    public String searchPage(Model model) {
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
        
    	return "search";
    }
    
    @GetMapping("/generate-search/{index}")
    public String generateSearch(@PathVariable String index, Model model) {
        // 검색어에서 앞뒤 공백 제거하고 중복 공백을 하나의 공백으로 변경
        String cleanedIndex = index.trim().replaceAll("\\s+", " ");

        // 검색어가 공백만 있을 경우 처리
        if (cleanedIndex.isEmpty()) {
            // 공백만 있으면 전체 소설 리스트 반환
            List<NovelVo> novelList = novelService.searchNovels(""); // 모든 소설 반환 메서드
            model.addAttribute("results", novelList);
        } else {
            // 공백 외에 검색어가 있으면 해당 검색어로 검색
            List<NovelVo> novelList = novelService.searchNovels(cleanedIndex);
            model.addAttribute("results", novelList);
        }

        // 장르 리스트 생성 및 모델에 추가
        List<GenreVo> genres = new ArrayList<>();
        genres.add(new GenreVo("로판", "로판"));
        genres.add(new GenreVo("현판", "현판"));
        genres.add(new GenreVo("판타지", "판타지"));
        genres.add(new GenreVo("무협", "무협"));
        genres.add(new GenreVo("로맨스", "로맨스"));
        genres.add(new GenreVo("일반", "일반"));
        model.addAttribute("genres", genres);

        model.addAttribute("searchQuery", index);  // 원본 검색어 전달
        return "search";  // search.jsp로 이동
    }


    
}