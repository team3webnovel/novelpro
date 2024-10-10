package com.team3webnovel.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    
    @Autowired
    private OpenAiService openAiService; 

    @GetMapping("/my_storage")
    public String showMyStoragePage(HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        CreationVo creationVo = new CreationVo();
        creationVo.setUserId(user.getUserId());
        creationVo.setArtForm(2); 
        
        List<ImageVo> imageList = imageService.getImageDataByUserId(creationVo);
        model.addAttribute("imageList", imageList);
        System.err.println("Image List: " + imageList);  

        List<NovelVo> novelList = novelService.getNovelListByUserId(user.getUserId());
        model.addAttribute("novelList", novelList);
        System.err.println("Novel List: " + novelList);  // 소설 리스트 로그 출력
        
        // userId와 artForm = 1인 음악들을 가져오기
        List<MusicVo> musicList = musicService.getStoredMusicByUserId(user.getUserId()); // null을 사용하여 전체 데이터를 가져올 수도 있음

        // 가져온 음악 데이터를 모델에 추가
        model.addAttribute("musicList", musicList);

        // 마이 스토리지 페이지로 이동
        return "storage/my_storage"; // JSP 파일 경로
    }

    @GetMapping("/write/{novelId}")
    public String showWritePage(@PathVariable("novelId") int novelId, HttpSession session, Model model) {
        UserVo user = (UserVo)session.getAttribute("user");
        int userId = user.getUserId();
        CreationVo vo = new CreationVo();
        vo.setUserId(userId);
        List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
        System.err.println("write" + imageList);
        model.addAttribute("imageList", imageList);
        
        List<MusicVo> musicList = musicService.getStoredMusicByUserId(user.getUserId());
        model.addAttribute("musicList", musicList);
    	
    	return "storage/new_episode";
    }

    @PostMapping("/write/{novelId}")
    public String write(@ModelAttribute NovelVo vo, HttpSession session,
    					@RequestParam("illust") int illust,
    					@RequestParam("bgm") int bgm,
                        @RequestParam("title") String title,
                        @RequestParam("episode") int episode,
                        @RequestParam("content") String content) {
        
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        vo.setUserId(user.getUserId());
        vo.setTitle(title);             
        vo.setImageId(illust);
        vo.setBgmId(bgm);
        vo.setEpisodeNo(episode);
        vo.setContents(content);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        vo.setCreatedAt(currentTime);   

		/*
		 * // 생성일을 현재 시간으로 설정 (Timestamp로 변경) Timestamp currentTime = new
		 * Timestamp(System.currentTimeMillis()); vo.setCreatedAt(currentTime); // 생성일
		 * 설정
		 */
        // 디버깅용 출력
        System.err.println(vo.toString());

        novelService.insertNovelDetail(vo);

        return "redirect:/my_storage"; 
    }
    
    @GetMapping("/new_novel")
    public String insertCoverPage(HttpSession session, Model model) {
    	UserVo user = (UserVo)session.getAttribute("user");
    	int userId = user.getUserId();
    	CreationVo vo = new CreationVo();
    	vo.setUserId(userId);
    	List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
    	System.err.println(imageList);
    	model.addAttribute("imageList", imageList);
        return "storage/new_novel";
    }

    @PostMapping("/new_novel")
    public String write(@ModelAttribute NovelVo vo, HttpSession session,
                        @RequestParam("illust") int illust,
                        @RequestParam("title") String title,
                        @RequestParam("intro") String intro,
                        @RequestParam("genre") String genre) {

        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        vo.setUserId(user.getUserId());    
        vo.setTitle(title);                
        vo.setIntro(intro);       
        vo.setGenre(genre);                
        vo.setCreationId(illust);          

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        vo.setCreatedAt(currentTime);      

        System.err.println(vo.toString());

        novelService.insertNovel(vo);

        return "redirect:/my_storage";
    }
    
    @PostMapping("/new_novel/api")
    @ResponseBody
    public ResponseEntity<String> generateIntro(@RequestBody Map<String, String> requestBody) {
        String userMessage = requestBody.get("userMessage");
        String genre = requestBody.get("genre");

        // 디버깅 로그 추가
        System.out.println("Received userMessage: " + userMessage);
        System.out.println("Received genre: " + genre);

        // 입력 값 검증
        if (userMessage == null || genre == null || userMessage.trim().isEmpty() || genre.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\": \"userMessage 또는 genre가 누락되었거나 비어있습니다.\"}");
        }

        // OpenAI API를 호출하여 intro 생성
        String generatedIntro;
        try {
            // OpenAI API의 응답을 content 부분만 추출하는 방식으로 변경
            generatedIntro = openAiService.generateIntroFromApi(userMessage, genre);
            System.out.println("Generated intro: " + generatedIntro);  // 추가: 생성된 intro 로그

            // intro 검증
            if (generatedIntro == null || generatedIntro.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"intro 생성 실패: 응답이 비어있습니다.\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();  // 예외 로그 출력
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\": \"intro 생성 실패: " + e.getMessage() + "\"}");
        }

        // 응답을 텍스트 형식으로 반환 (JSON의 content 필드만 반환)
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"intro\": \"" + generatedIntro + "\"}");  // intro 필드만 반환
    }
 
    
    @GetMapping("/novel_detail/{novelId}")
    public String detailPage(@PathVariable("novelId") int novelId, Model model, HttpSession session) {
        
    	// novelId 기반 커버 찾기
    	NovelVo novelVo = novelService.getNovelByNovelId(novelId);
    	List<NovelVo> detailList = novelService.getNovelDetailByNovelId(novelId);

    	// novelCover를 모델에 추가
    	model.addAttribute("novelCover", novelVo);

    	UserVo user = (UserVo) session.getAttribute("user");

    	// 사용자 로그인 상태 확인 및 소설 작성자와 비교
    	if (user == null || novelVo.getUserId() != user.getUserId()) {
    	    // 로그인되지 않았거나 작성자가 아닐 경우
    	    // 원래 리스트에서 status가 "public"인 항목들만 필터링
    	    detailList = detailList.stream()
    	        .filter(novel -> "public".equals(novel.getVisibility()))
    	        .collect(Collectors.toList());
    	}

    	// 필터링된 혹은 원래의 detailList를 모델에 추가
    	model.addAttribute("detailList", detailList);

    	// 로그인 상태 및 작성자 여부에 따라 다른 뷰를 반환
    	if (user == null || novelVo.getUserId() != user.getUserId()) {
    	    return "storage/detail_user";
    	} else {
    	    return "storage/novel_detail";
    	}
    }

    
    @PostMapping("/updateStatus")
    public void updateStatus(@RequestParam("novelId") int novelId, @RequestParam("status") String status) {
        try {
            NovelVo vo = new NovelVo();
            vo.setNovelId(novelId);
            vo.setStatus(status);
            novelService.updateStatus(vo);
        } catch (Exception e) {
            // 오류 발생 시에도 아무런 응답을 보내지 않음
        }
    }

    
    @GetMapping("/novel/episode/{novelId}/{episodeNo}")
    public String episodeUpdate(
            @PathVariable int novelId, 
            @PathVariable int episodeNo,
            Model model, HttpSession session) {
    	UserVo user = (UserVo)session.getAttribute("user");
    	
    	NovelVo novelVo = new NovelVo();
    	novelVo.setNovelId(novelId);
    	novelVo.setEpisodeNo(episodeNo);
    	novelVo = novelService.getNovelDetail(novelVo);
    	model.addAttribute("episode", novelVo);
    	System.err.println(novelVo);
    	model.addAttribute("maxEpisode", 3);
    	
    	if (user == null) {
    		return "storage/episode_user";
    	} else if (user.getUserId() != novelVo.getUserId()) {
    		return "storage/episode_user";
    	}

    	CreationVo vo = new CreationVo();
    	vo.setUserId(novelVo.getUserId());
    	List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
    	System.err.println("write" + imageList);
    	model.addAttribute("imageList", imageList);
    	
        // userId와 artForm = 1인 음악들을 가져오기
        List<MusicVo> musicList = musicService.getStoredMusicByUserId(novelVo.getUserId()); // null을 사용하여 전체 데이터를 가져올 수도 있음

        // 가져온 음악 데이터를 모델에 추가
        model.addAttribute("musicList", musicList);
        
    	
    	return "storage/update_episode";
    }
    
    @PostMapping("/episode/update/{novelId}/{episodeNo}")
    public String episodeUpdateString(
            @PathVariable int novelId, 
            @PathVariable int episodeNo,
            Model model, HttpSession session,
            @RequestParam("illust") int illust,
			@RequestParam("bgm") int bgm,
            @RequestParam("title") String title,
            @RequestParam("content") String content) {
    	
    	NovelVo vo = new NovelVo();
    	vo.setNovelId(novelId);
        vo.setTitle(title);             // 소설 제목 설정
        vo.setImageId(illust);		// 소설 표지 설정
        vo.setBgmId(bgm);
        vo.setEpisodeNo(episodeNo);
        vo.setContents(content);

		/*
		 * // 생성일을 현재 시간으로 설정 (Timestamp로 변경) Timestamp currentTime = new
		 * Timestamp(System.currentTimeMillis()); vo.setCreatedAt(currentTime); // 생성일
		 * 설정
		 */
        // 디버깅용 출력
        System.err.println(vo.toString());

        // NovelService를 통해 소설 삽입
        novelService.updateNovelDetail(vo);
    	
    	return "redirect:/novel_detail/" + novelId;
    	
    }
    
    // 에피소드 공개/비공개 상태 업데이트
    @PostMapping("/updateVisibility")
    public void updateVisibility(
            @RequestParam int novelId,
            @RequestParam int episodeNo,
            @RequestParam String visibility) {
        
        NovelVo vo = new NovelVo();
        vo.setNovelId(novelId);
        vo.setEpisodeNo(episodeNo);
        vo.setVisibility(visibility);

        // 서비스 호출하여 에피소드 공개/비공개 상태 업데이트
        novelService.updateEpisodeVisibility(vo);
    }
    
    @GetMapping("/edit_new_novel/{novelId}")
    public String editNovel(@PathVariable("novelId") int novelId, Model model, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserVo user = (UserVo)session.getAttribute("user");

        // novelId로 소설 데이터를 가져옴
        NovelVo novelVo = novelService.getNovelByNovelId(novelId);
        
        System.out.println("Fetched novel: " + novelVo);  // novelVo가 null인지 확인
        
        if (novelVo == null) {
            return "storage/episode_user"; // 소설을 찾지 못한 경우
        }
        
        // 소설 데이터를 모델에 추가
        model.addAttribute("novelCover", novelVo);
        
        
        if (user == null) {
    		return "storage/episode_user";
    	} else if (user.getUserId() != novelVo.getUserId()) {
    		return "storage/episode_user";
    	}

        // CreationVo를 이용해 이미지 데이터 가져오기
        CreationVo vo = new CreationVo();
        vo.setUserId(novelVo.getUserId());
        List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
        
        model.addAttribute("imageList", imageList);

        // JSP 파일로 이동
        return "storage/edit_new_novel";
    }
    
    @PostMapping("/edit_new_novel/{novelId}")
    public String updateNovel(
            @PathVariable int novelId, 
            Model model, HttpSession session,
            @RequestParam("title") String title,
            @RequestParam("genre") String genre,
            @RequestParam("illust") int illust,
            @RequestParam("intro") String intro) {

        // 세션에서 사용자 정보 가져오기
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";  // 로그인 페이지로 리다이렉트
        }

        // 기존 소설 데이터를 가져옴
        NovelVo existingNovel = novelService.getNovelByNovelId(novelId);

        if (existingNovel == null) {
            return "error";  // 만약 해당 소설이 없으면 에러 페이지로 이동
        }

        // 수정된 부분만 덮어쓰기
        existingNovel.setTitle(title);
        existingNovel.setGenre(genre);
        existingNovel.setImageId(illust);  // 소설 표지 이미지 ID 설정
        existingNovel.setIntro(intro);
        existingNovel.setUserId(user.getUserId());  // 현재 로그인된 사용자 ID 설정

        // 디버깅용 출력
        System.err.println(existingNovel.toString());

        // NovelService를 통해 소설 수정
        novelService.updateNovel(existingNovel);

        // 수정 후 소설 상세 페이지로 리다이렉트
        return "redirect:/novel_detail/" + novelId;  // 수정된 소설의 상세 페이지로 리다이렉트
    }

    @PostMapping("/delete_novel/{novelId}")
    public String deleteNovel(@PathVariable int novelId, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserVo user = (UserVo) session.getAttribute("user");
        System.out.println("Delete Request for Novel ID: " + novelId);

        if (user == null) {
            return "redirect:/login";  // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        }

        // NovelService를 통해 소설 삭제
        novelService.deleteNovel(novelId);

        // 삭제 후 저장소 페이지로 리다이렉트
        return "redirect:/my_storage";
    }



}
