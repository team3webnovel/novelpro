package com.team3webnovel.controllers;

import java.sql.Timestamp;
import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3webnovel.services.ImageService;
import com.team3webnovel.services.MusicService;
import com.team3webnovel.services.NovelService;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.MusicVo;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
public class NovelController {

    @Autowired
    private NovelService novelService;
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private MusicService musicService;

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
        List<ImageVo> imageList = imageService.getImageDataByUserId(creationVo);
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
    @GetMapping("/write/{novelId}")
    public String showWritePage(@PathVariable("novelId") int novelId, HttpSession session, Model model) {
    	
    	UserVo user = (UserVo)session.getAttribute("user");
    	int userId = user.getUserId();
    	CreationVo vo = new CreationVo();
    	vo.setUserId(userId);
    	List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
    	System.err.println("write" + imageList);
    	model.addAttribute("imageList", imageList);
    	
        // userId와 artForm = 1인 음악들을 가져오기
        List<MusicVo> musicList = musicService.getStoredMusicByUserId(user.getUserId()); // null을 사용하여 전체 데이터를 가져올 수도 있음

        // 가져온 음악 데이터를 모델에 추가
        model.addAttribute("musicList", musicList);
    	
    	return "ystest/write";
    }

    // 글쓰기 처리
    @PostMapping("/write/{novelId}")
    public String write(@ModelAttribute NovelVo vo, HttpSession session,
    					@RequestParam("illust") int illust,
    					@RequestParam("bgm") int bgm,
                        @RequestParam("title") String title,
                        @RequestParam("episode") int episode,
                        @RequestParam("content") String content) {
        
        // 세션에서 사용자 정보 가져오기
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            // 사용자가 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 전달받은 값으로 NovelVo 객체 설정
        vo.setUserId(user.getUserId()); // 작성자 ID로 설정
        vo.setTitle(title);             // 소설 제목 설정
        vo.setImageId(illust);		// 소설 표지 설정
        vo.setBgmId(bgm);
        vo.setEpisodeNo(episode);
        vo.setContents(content);

        // 생성일을 현재 시간으로 설정 (Timestamp로 변경)
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        vo.setCreatedAt(currentTime);   // 생성일 설정

        // 디버깅용 출력
        System.err.println(vo.toString());

        // NovelService를 통해 소설 삽입
        novelService.insertNovelDetail(vo);

        return "redirect:/my_storage"; // 작성 후 보관함 페이지로 리다이렉트
    }
    
    // 글쓰기 페이지로 이동
    @GetMapping("/cover")
    public String insertCoverPage(HttpSession session, Model model) {
    	UserVo user = (UserVo)session.getAttribute("user");
    	int userId = user.getUserId();
    	CreationVo vo = new CreationVo();
    	vo.setUserId(userId);
    	List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
    	System.err.println(imageList);
    	model.addAttribute("imageList", imageList);
        return "ystest/cover";
    }

    // 소설 생성
    @PostMapping("/cover")
    public String write(@ModelAttribute NovelVo vo, HttpSession session,
    		@RequestParam("illust") int illust,
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
    	vo.setCreationId(illust);		// 소설 표지 설정
    	
    	// 생성일을 현재 시간으로 설정 (Timestamp로 변경)
    	Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    	vo.setCreatedAt(currentTime);   // 생성일 설정
    	
    	// 디버깅용 출력
    	System.err.println(vo.toString());
    	
    	// NovelService를 통해 소설 삽입
    	novelService.insertNovel(vo);
    	
    	return "redirect:/my_storage"; // 작성 후 보관함 페이지로 리다이렉트
    }
    
 // 소설 상세페이지로 이동
    @GetMapping("/novel_detail/{novelId}")
    public String detailPage(@PathVariable("novelId") int novelId, Model model, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("user");
        
        // 사용자 ID로 소설 리스트 가져오기
        List<NovelVo> novelList = novelService.getNovelListByUserId(user.getUserId());
        model.addAttribute("novelList", novelList);  // 전체 소설 리스트를 모델에 추가 (만약 필요하다면)
        System.err.println("Novel List: " + novelList);  // 소설 리스트 로그 출력

        // novelList에서 novelId와 일치하는 소설을 필터링
        NovelVo novelCover = novelList.stream()
                                 .filter(n -> n.getNovelId() == novelId)
                                 .findFirst()
                                 .orElse(null);  // 만약 찾지 못하면 null 반환
        
        if (novelCover == null) {
            // 만약 해당 novelId의 소설이 없다면 404 페이지로 리다이렉트하거나 오류 처리
            return "redirect:/404";  // 404 페이지로 리다이렉트 예시
        }
        System.err.println(novelCover);
        // 조회한 소설 데이터를 모델에 추가
        model.addAttribute("novelCover", novelCover);
        
        System.err.println(novelService.getNovelDetailByNovelId(novelId));
        
        model.addAttribute("detailList", novelService.getNovelDetailByNovelId(novelId));
        
        
        // 소설 상세페이지로 이동
        return "ystest/novel_detail";
    }
    
    @PostMapping("/updateStatus")
    public void updateStatus(@RequestParam("novelId") int novelId, @RequestParam("status") String status) {
        try {
            // 상태 업데이트 로직 수행
            NovelVo vo = new NovelVo();
            vo.setNovelId(novelId);
            vo.setStatus(status);
            novelService.updateStatus(vo);
            // 응답을 반환하지 않음
        } catch (Exception e) {
            // 오류 발생 시에도 아무런 응답을 보내지 않음
        }
    }




    

}
