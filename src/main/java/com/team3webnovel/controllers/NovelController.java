package com.team3webnovel.controllers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3webnovel.services.NovelService;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
public class NovelController {

    @Autowired
    private NovelService novelService; // NovelService를 Autowired로 주입받습니다.

    // my_storage.jsp로 이동하는 매핑
    @GetMapping("/my_storage")
    public String showMyStoragePage() {
        return "ystest/my_storage";
    }

    // 글쓰기 페이지로 이동
    @GetMapping("/write")
    public String showWritePage() {
        return "ystest/write";
    }

 // 글쓰기 처리
    @PostMapping("/write")
    public String write(@ModelAttribute NovelVo vo, HttpSession session,
                        @RequestParam("title") String title,
                        @RequestParam("episode") int episode,
                        @RequestParam("genre") String genre,
                        @RequestParam("content") String content) {
        // 세션에서 사용자 정보 가져오기
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            // 사용자가 로그인하지 않은 경우 처리 (예: 로그인 페이지로 리다이렉트)
            return "redirect:/login";
        }

        // 세션에서 사용자 ID 가져오기
        int userId = user.getUserId();

        // 전달받은 값으로 NovelVo 객체 설정
        NovelVo novelVo = new NovelVo();
        novelVo.setUserId(userId);
        novelVo.setTitle(title);
        novelVo.setContent(content);

        // 커버 이미지와 조회수는 기본값으로 설정 (필요한 경우 설정할 수 있음)
        novelVo.setCoverImageUrl(""); // 커버 이미지 URL이 없는 경우 빈 값
        novelVo.setViewCount(0);      // 초기 조회수 0

        // 생성일과 수정일은 현재 시간으로 설정
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        novelVo.setCreatedAt(currentTime);
        novelVo.setUpdatedAt(currentTime);

        // 디버깅용 출력
        System.err.println(novelVo.toString());

        // NovelService 인스턴스를 통해 소설 삽입
        novelService.insertNovel(novelVo);

        // 저장소로 리다이렉트
        return "redirect:/my_storage";
    }

    

    
}
