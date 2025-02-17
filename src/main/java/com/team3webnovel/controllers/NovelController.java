package com.team3webnovel.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team3webnovel.services.ImageService;
import com.team3webnovel.services.MusicService;
import com.team3webnovel.services.NovelService;
import com.team3webnovel.services.OpenAiService;
import com.team3webnovel.services.VideoService;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.MusicVo;
import com.team3webnovel.vo.NovelVo;
import com.team3webnovel.vo.UserVo;
import com.team3webnovel.vo.VideoVo;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/novel")
@SessionAttributes("chatHistory")
public class NovelController {

	@Autowired
	private NovelService novelService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private MusicService musicService;

	@Autowired
	private OpenAiService openAiService;
	
	@Autowired
	private VideoService videoService;

	// 글쓰기 페이지로 이동
	@GetMapping("/write/{novelId}")
	public String showWritePage(@PathVariable("novelId") int novelId, HttpSession session, Model model) {

		UserVo user = (UserVo) session.getAttribute("user");
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
	    @RequestParam(value = "illust", required = false, defaultValue = "0") int illust,  
	    @RequestParam(value = "bgm", required = false, defaultValue = "0") int bgm, // 기본값 설정
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
	    vo.setTitle(title); // 소설 제목 설정
	    vo.setImageId(illust); // 소설 표지 설정
	    vo.setBgmId(bgm); // BGM 설정
	    vo.setEpisodeNo(episode); // 에피소드 번호 설정
	    vo.setContents(content); // 소설 내용 설정

	    // 디버깅용 출력
	    System.err.println(vo.toString());

	    // NovelService를 통해 소설 삽입
	    novelService.insertNovelDetail(vo);

	    return "redirect:/novel/novel-detail/" + vo.getNovelId(); // 작성 후 보관함 페이지로 리다이렉트
	}

    
    // 글쓰기 페이지로 이동
    @GetMapping("/new-novel")
    public String insertCoverPage(HttpSession session, Model model, RedirectAttributes redirectAttribute) {
    	UserVo user = (UserVo)session.getAttribute("user");
    	int userId = user.getUserId();
    	
    	// 이미지 리스트 가져오기
    	CreationVo vo = new CreationVo();
    	vo.setUserId(userId);
    	List<ImageVo> imageList = imageService.getImageDataByUserId(vo);
    	List<VideoVo> videoList = videoService.getVideoDataByUserId(vo);

    	
        if (model.containsAttribute("AImessage")) {
            redirectAttribute.addFlashAttribute("AImessage"); // 이미지 생성 페이지로 리다이렉트
        }
        
        // 디버깅용 출력
        System.err.println(imageList);

        
        // 모델에 이미지 및 비디오 리스트 추가
        model.addAttribute("imageList", imageList);
        model.addAttribute("videoList", videoList);
        
        return "storage/new_novel";
    }
    
    @PostMapping("/new-novel")
    public String write(@ModelAttribute NovelVo vo, 
                        @RequestParam(value = "AImessage", required = false) String AImessage,
                        HttpSession session, 
                        Model model, 
                        @RequestParam(value = "illust", required = false, defaultValue = "0") int illust,  
                        @RequestParam("title") String title,
                        @RequestParam("intro") String intro,
                        @RequestParam("genre") String genre, 
                        RedirectAttributes redirectAttributes) {
        
        // 세션에서 사용자 정보 가져오기
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // 사용자가 로그인하지 않은 경우 로그인 페이지로 리다이렉트
        }
        
        // 줄바꿈 문자를 <br>로 변환하여 intro 저장
    	String formattedIntro = intro.replaceAll("\n", "<br>");
        
        // 전달받은 값으로 NovelVo 객체 설정
        vo.setUserId(user.getUserId());
        vo.setTitle(title);
        vo.setIntro(formattedIntro);
        vo.setGenre(genre);
        vo.setCreationId(illust);
        
        // 생성일을 현재 시간으로 설정
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        vo.setCreatedAt(currentTime);

        // 디버깅용 출력
        System.err.println(vo.toString());
        
        // NovelService를 통해 소설 삽입
        novelService.insertNovel(vo);
        
        if (model.containsAttribute("AImessage")) {
        	return "redirect:/creation-studio/image"; // 이미지 생성 페이지로 리다이렉트
        }
        // AImessage가 있을 경우 처리
        if (AImessage != null && !AImessage.isEmpty()) {
            return "redirect:/creation-studio/image"; // AImessage가 있으면 이미지 생성 페이지로 리다이렉트
        }

        return "redirect:/storage"; // 일반적으로 보관함 페이지로 리다이렉트

    }

    
 // 소설 상세페이지로 이동
    @GetMapping("/novel-detail/{novelId}")
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
			detailList = detailList.stream().filter(novel -> "public".equals(novel.getVisibility()))
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
	public String episodeUpdate(@PathVariable int novelId, @PathVariable int episodeNo, Model model,
			HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");

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
		List<MusicVo> musicList = musicService.getStoredMusicByUserId(novelVo.getUserId()); // null을 사용하여 전체 데이터를 가져올 수도
																							// 있음

		// 가져온 음악 데이터를 모델에 추가
		model.addAttribute("musicList", musicList);

		return "storage/update_episode";
	}

	@GetMapping("/episodeview/{novelId}/{episodeNo}")
	public String episodeView(@PathVariable int novelId, @PathVariable int episodeNo, Model model) {
		NovelVo novelVo = new NovelVo();
		novelVo.setNovelId(novelId);
		novelVo.setEpisodeNo(episodeNo);
		novelVo = novelService.getNovelDetail(novelVo);
		model.addAttribute("episode", novelVo);
		
		NovelVo maxNovelVo = novelService.getNovelByNovelId(novelId);
		model.addAttribute("maxEpisode", maxNovelVo.getEpisodeNo());

		return "storage/episode_user";

	}

	@PostMapping("/episode/update/{novelId}/{episodeNo}")
	public String episodeUpdateString(@PathVariable int novelId, @PathVariable int episodeNo, Model model,
			HttpSession session, @RequestParam("illust") int illust,
			@RequestParam(value = "bgm", required = false) int bgm, @RequestParam("title") String title,
			@RequestParam("content") String content) {

		NovelVo vo = new NovelVo();
		vo.setNovelId(novelId);
		vo.setTitle(title); // 소설 제목 설정
		vo.setImageId(illust); // 소설 표지 설정
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
	public void updateVisibility(@RequestParam int novelId, @RequestParam int episodeNo,
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

			response.put("success", true); // 성공 응답
		} catch (Exception e) {
			response.put("success", false); // 실패 응답
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

			response.put("success", true); // 성공 응답
		} catch (Exception e) {
			response.put("success", false); // 실패 응답
		}
		return response;
	}

	@PostMapping("/delete-video")
    @ResponseBody
    public Map<String, Object> deleteVideo(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();
        System.err.println("비디오 삭제 요청 수신됨");

        try {
            int creationId = (int) requestData.get("creationId");

            
            videoService.updateCreationId(creationId);

            response.put("success", true);  // 성공 응답
        } catch (Exception e) {
            response.put("success", false);  // 실패 응답
            e.printStackTrace();  // 예외 출력 (디버깅 용도)
        }

        return response;
	}
	


	    @ModelAttribute("chatHistory")
	    public List<String> initializeChatHistory() {
	        return new ArrayList<>();
	    }

	    @PostMapping("/new_novel/api")
	    @ResponseBody
	    public ResponseEntity<String> generateIntro(
	        @RequestBody Map<String, String> requestBody,
	        @ModelAttribute("chatHistory") List<String> chatHistory // chatHistory를 세션에서 가져옴
	    ) {
	        String userMessage = requestBody.get("userMessage");
	        String genre = requestBody.get("genre");

	        // 디버깅 로그 추가
	        System.out.println("Received userMessage: " + userMessage);
	        System.out.println("Received genre: " + genre);

	        // 입력 값 검증
	        if (userMessage == null || genre == null || userMessage.trim().isEmpty() || genre.trim().isEmpty()) {
	            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST) // HttpStatus.BAD_REQUEST 사용
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .body("{\"error\": \"userMessage 또는 genre가 누락되었거나 비어있습니다.\"}");
	        }

	        // chatHistory에 새로운 메시지를 추가하고 이전 대화도 함께 포함
	        chatHistory.add(userMessage);
	        String messages = String.join(",", chatHistory);

	        // OpenAI API를 호출하여 intro 생성
	        String generatedIntro;
	        try {
	            // OpenAI API를 호출하여 intro 생성
	            generatedIntro = openAiService.generateIntroFromApi(messages, genre); // messages를 사용
	            System.out.println("Generated intro: " + generatedIntro); // 생성된 intro 로그

	            // intro 검증: 응답이 비어있는 경우 오류 반환
	            if (generatedIntro == null || generatedIntro.trim().isEmpty()) {
	                return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .body("{\"error\": \"intro 생성 실패: 응답이 비어있습니다.\"}");
	            }

	        } catch (Exception e) {
	            // 예외 처리 및 오류 메시지 반환
	            e.printStackTrace(); // 예외 로그 출력
	            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .body("{\"error\": \"intro 생성 실패: " + e.getMessage() + "\"}");
	        }

	        // 문단 구분: 특정 키워드를 기준으로 줄바꿈 추가
	        String introWithParagraphs = generatedIntro
	                .replace("제목", "\\n\\n제목")
	                .replace("등장인물", "\\n\\n등장인물")
	                .replace("줄거리", "\\n\\n줄거리");

	        // 응답을 JSON 형식으로 반환 (문단 구분된 intro 반환)
	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_JSON)
	                .body("{\"intro\": \"" + introWithParagraphs + "\"}");
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
        
        List<VideoVo> videoList = videoService.getVideoDataByUserId(vo);
        model.addAttribute("videoList", videoList);

        // JSP 파일로 이동
        return "storage/edit_new_novel";
    }
    
    @PostMapping("/edit-new-novel/{novelId}")
    public String updateNovel(
            @PathVariable int novelId, 
            Model model, HttpSession session,
            @RequestParam("title") String title,
            @RequestParam("genre") String genre,
            @RequestParam(value = "illust", required = false, defaultValue = "0") int illust,
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
        
        // 줄바꿈 문자를 <br>로 변환하여 intro 저장
        String formattedIntro = intro.replaceAll("\n", "<br>");

        // 수정된 부분만 덮어쓰기
        existingNovel.setTitle(title);
        existingNovel.setGenre(genre);
        existingNovel.setImageId(illust);  // 소설 표지 이미지 ID 설정
        existingNovel.setIntro(formattedIntro);
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
    
	/*
	 * // 24.10.11 에피소드 삭제
	 * 
	 * @DeleteMapping("/{novelId}/delete-episode/{episodeNo}") public
	 * ResponseEntity<String> deleteEpisode(@PathVariable int novelId, @PathVariable
	 * int episodeNo) { try { // 에피소드 삭제 서비스 호출 novelService.deleteEpisode(novelId,
	 * episodeNo);
	 * 
	 * return ResponseEntity.ok("에피소드가 성공적으로 삭제되었습니다."); } catch (Exception e) {
	 * return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).
	 * body("에피소드 삭제에 실패했습니다."); } }
	 */
    
    @PostMapping("/deleteEpisode/{novelId}/{episodeNo}")
    public String deleteEpisode(@PathVariable int novelId, @PathVariable int episodeNo) {
        // 삭제 로직 실행
        novelService.deleteEpisode(novelId, episodeNo);
        
        // 삭제 후 리다이렉트
        return "redirect:/novel/novel-detail/" + novelId;
    }
    
    @PostMapping("/like/{novelId}")
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable int novelId, HttpSession session) {
        System.err.println("넘어옴 " + novelId);
        
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            // 로그인하지 않은 경우 JSON 응답으로 리다이렉트 정보를 반환
            Map<String, Object> response = new HashMap<>();
            response.put("redirect", "/team3webnovel/login"); // 로그인 페이지 URL을 명시적으로 설정
            return ResponseEntity.status(401).body(response); // 상태 코드를 숫자로 사용
        }

        int userId = user.getUserId();
        System.err.println(userId);
        boolean liked = novelService.toggleLike(userId, novelId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("liked", liked); // true: 좋아요 추가됨, false: 취소됨

        return ResponseEntity.ok(response);
    }





}