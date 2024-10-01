package com.team3webnovel.controllers;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3webnovel.dao.ImageDao;
import com.team3webnovel.services.NovelService;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
public class NovelController {

    @Autowired
    private NovelService novelService;

    @Autowired
    private ImageDao imageDao;  // ImageDao 주입

    @GetMapping("/my_storage")
    public String showMyStoragePage(HttpSession session, Model model) {
        // 세션에서 로그인한 사용자 정보 가져오기
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            // 로그인이 되어 있지 않으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 사용자의 이미지 리스트 가져오기
        CreationVo creationVo = new CreationVo();
        creationVo.setUserId(user.getUserId());
        creationVo.setArtForm(2); // 예: 소설 형식을 나타내는 코드 2
        
        // 이미지 데이터 모델에 추가 및 로그 출력
        List<ImageVo> imageList = imageDao.getImageDataByUserId(creationVo);
        model.addAttribute("imageList", imageList);
        System.err.println("Image List: " + imageList);  // 이미지 리스트 로그 출력

        // 사용자 ID로 소설 리스트 가져오기
        List<NovelVo> novelList = novelService.getNovelListByUserId(user.getUserId());
        model.addAttribute("novelList", novelList);
        System.err.println("Novel List: " + novelList);  // 소설 리스트 로그 출력

        // 마이 스토리지 페이지로 이동
        return "ystest/my_storage"; // JSP 파일 경로
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
                        @RequestParam("intro") String intro,
                        @RequestParam("genre") String genre) {
        
        // 세션에서 사용자 정보 가져오기
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            // 사용자가 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 전달받은 값으로 NovelVo 객체 설정
        vo.setUserId(user.getUserId()); // 작성자 ID로 설정
        vo.setTitle(title);             // 소설 제목 설정
        vo.setIntro(intro);             // 소설 소개 설정
        vo.setGenre(genre);             // 소설 장르 설정

        // 생성일을 현재 시간으로 설정 (Timestamp로 변경)
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        vo.setCreatedAt(currentTime);   // 생성일 설정

        // 디버깅용 출력
        System.err.println(vo.toString());

        // NovelService를 통해 소설 삽입
        novelService.insertNovel(vo);

        return "redirect:/my_storage"; // 작성 후 보관함 페이지로 리다이렉트
    }

    
    // 글쓰기 페이지로 이동
    @GetMapping("/cover")
    public String insertCoverPage() {
        return "ystest/cover";
    }
    
    // 소설 상세페이지로 이동
    @GetMapping("/novel_detail")
    public String detailPage() {
        return "ystest/novel_detail";
    }

    

}
