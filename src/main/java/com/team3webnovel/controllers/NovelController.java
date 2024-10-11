package com.team3webnovel.controllers;

import java.sql.Timestamp;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/novel")
public class NovelController {

    @Autowired
    private NovelService novelService;
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private MusicService musicService;
    
    @Autowired
    private OpenAiService openAiService;

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
    					@RequestParam(value="bgm", required = false) int bgm,
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

        return "redirect:/storage"; // 작성 후 보관함 페이지로 리다이렉트
    }
    
    // 글쓰기 페이지로 이동
    @GetMapping("/new-novel")
    public String insertCoverPage(HttpSession session, Model model) {
    	UserVo user = (UserVo)session.getAttribute("user");
    	int userId = user.getUserId();
    	
    	// 이미지 리스트 가져오기
    	CreationVo vo = new CreationVo();
    	vo.setUserId(userId);
    	List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
    	
 
        
        // 디버깅용 출력
        System.err.println(imageList);

        
        // 모델에 이미지 및 비디오 리스트 추가
        model.addAttribute("imageList", imageList);

        
        return "storage/new_novel";
    }

    // 소설 생성
    @PostMapping("/new-novel")
    public String write(@ModelAttribute NovelVo vo, HttpSession session, Model model,
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
    	
    	if (model.containsAttribute("AImessage")) {
    		return "/generate/image_simple_form";
    	}
    	
    	return "redirect:/storage"; // 작성 후 보관함 페이지로 리다이렉트
    }
    
 // 소설 상세페이지로 이동
    @GetMapping("/novel-detail/{novelId}")
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
    
    @GetMapping("/episode/{novelId}/{episodeNo}")
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
    @GetMapping("/episodeview/{novelId}/{episodeNo}")
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
            @RequestParam(value="bgm", required = false) int bgm,
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
    	
    	return "redirect:/novel/novel-detail/" + novelId;
    	
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
        System.err.println("? 삭제 넘어옴?");
        try {
            int creationId = (int) requestData.get("creationId");

            // 이미지 삭제 서비스 호출
            imageService.updateCreationId(creationId);

            response.put("success", true);  // 성공 응답
        } catch (Exception e) {
            response.put("success", false);  // 실패 응답
        }
        return response;
    }
    
    
    
    @PostMapping("/new-novel/api")
    @ResponseBody
    public ResponseEntity<String> generateIntro(@RequestBody Map<String, String> requestBody) {
        String userMessage = requestBody.get("userMessage");
        String genre = requestBody.get("genre");

        // 디버깅 로그 추가
        System.out.println("Received userMessage: " + userMessage);
        System.out.println("Received genre: " + genre);

        // 입력 값 검증
        if (userMessage == null || genre == null || userMessage.trim().isEmpty() || genre.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST) // 수정: HttpStatus.BAD_REQUEST 사용
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

    
    @GetMapping("/edit-new-novel/{novelId}")
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
    
    @PostMapping("/edit-new-novel/{novelId}")
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
        return "redirect:/novel/novel-detail/" + novelId;  // 수정된 소설의 상세 페이지로 리다이렉트
    }



    // 24.10.10
    @DeleteMapping("/delete-novel/{novelId}")
    public ResponseEntity<String> deleteNovel(@PathVariable int novelId) {
        try {
            System.out.println("Deleting novel with ID: " + novelId); // 로그 추가
            novelService.deleteNovel(novelId);
            return ResponseEntity.ok("Novel deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Failed to delete novel");
        }


    }
    
    // 24.10.11 에피소드 삭제
    @DeleteMapping("/{novelId}/delete-episode/{episodeNo}")
    public ResponseEntity<String> deleteEpisode(@PathVariable int novelId, @PathVariable int episodeNo) {
        try {
            // 에피소드 삭제 서비스 호출
            novelService.deleteEpisode(novelId, episodeNo);

            return ResponseEntity.ok("에피소드가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("에피소드 삭제에 실패했습니다.");
        }
    }

}



    


