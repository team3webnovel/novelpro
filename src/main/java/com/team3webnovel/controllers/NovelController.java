package com.team3webnovel.controllers;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/storage")
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
        
        // userId와 artForm = 1인 음악들을 가져오기
        List<MusicVo> musicList = musicService.getStoredMusicByUserId(user.getUserId()); // null을 사용하여 전체 데이터를 가져올 수도 있음

        // 가져온 음악 데이터를 모델에 추가
        model.addAttribute("musicList", musicList);

        // 마이 스토리지 페이지로 이동
        return "storage/my_storage"; // JSP 파일 경로
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
    	
    	return "storage/new_episode";
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

		/*
		 * // 생성일을 현재 시간으로 설정 (Timestamp로 변경) Timestamp currentTime = new
		 * Timestamp(System.currentTimeMillis()); vo.setCreatedAt(currentTime); // 생성일
		 * 설정
		 */
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
        return "storage/new_novel";
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
        
/*      수정해야할듯
        // 사용자 ID로 소설 리스트 가져오기
        List<NovelVo> novelList = novelService.getNovelListByUserId(user.getUserId());
        model.addAttribute("novelList", novelList);  // 전체 소설 리스트를 모델에 추가 (만약 필요하다면)
        System.err.println("Novel List: " + novelList);  // 소설 리스트 로그 출력

        // novelList에서 novelId와 일치하는 소설을 필터링
        NovelVo novelCover = novelList.stream()
                                 .filter(n -> n.getNovelId() == novelId)
                                 .findFirst()
                                 .orElse(null);  // 만약 찾지 못하면 null 반환

        // 조회한 소설 데이터를 모델에 추가 (novelCover가 null이 아닐 경우만 추가)
        if (novelCover != null) {
            model.addAttribute("novelCover", novelCover);
        }
*/        
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
    	NovelVo maxNovelVo = novelService.getNovelByNovelId(novelId);
    	model.addAttribute("maxEpisode", maxNovelVo.getEpisodeNo());
    	
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
    @GetMapping("/novel/episodeview/{novelId}/{episodeNo}")
    public String episodeView(
            @PathVariable int novelId, 
            @PathVariable int episodeNo,
            Model model) {
    	NovelVo novelVo = new NovelVo();
    	novelVo.setNovelId(novelId);
    	novelVo.setEpisodeNo(episodeNo);
    	novelVo = novelService.getNovelDetail(novelVo);
    	model.addAttribute("episode", novelVo);
    	return "storage/episode_user";
    	
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
    
    @PostMapping("/update-image-title")
    @ResponseBody
    public Map<String, Object> updateImageTitle(@RequestBody ImageVo imageVo) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 이미지 제목 업데이트 로직 호출
            imageService.updateImageTitle(imageVo);

            response.put("success", true);  // 성공 응답
        } catch (Exception e) {
            response.put("success", false);  // 실패 응답
        }
        return response;
    }
    
    @PostMapping("/delete-image")
    @ResponseBody
    public Map<String, Object> deleteImage(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            int creationId = (int) requestData.get("creationId");

            // 이미지 삭제 서비스 호출
            imageService.deleteImageById(creationId);
            imageService.deleteCreationById(creationId);

            response.put("success", true);  // 성공 응답
        } catch (Exception e) {
            response.put("success", false);  // 실패 응답
        }
        return response;
    }


    

}
