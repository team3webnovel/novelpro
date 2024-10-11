package com.team3webnovel.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3webnovel.comfyui.ComfyUIVideoGenerator;
import com.team3webnovel.services.ImageService;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.UserVo;
import com.team3webnovel.vo.resultVideoVo;
import com.team3webnovel.vo.resultVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/videos")
public class VideoGenerationController {

	@Autowired
	private ImageService imageService;

	private final ComfyUIVideoGenerator comfyUIVideoGenerator;

	public VideoGenerationController() {
		this.comfyUIVideoGenerator = new ComfyUIVideoGenerator();
	}


	// 작업 상태를 확인하는 API
	@GetMapping("/checkStatus")
	@ResponseBody
	public Map<String, Object> checkStatus(HttpSession session) {
		Map<String, Object> response = new HashMap<>();

		Boolean imageGenerated = (Boolean) session.getAttribute("imageGenerated");
		if (imageGenerated != null && imageGenerated) {
			response.put("status", "completed");
			response.put("imageUrl", session.getAttribute("imageUrl"));
			session.removeAttribute("imageGenerated"); // 상태 체크 후 세션에서 제거
		} else {
			response.put("status", "in_progress");
		}

		return response;
	}

	@GetMapping("/getClientId")
	@ResponseBody
	public Map<String, Object> getClientId(HttpSession session) {
		UserVo vo = (UserVo) session.getAttribute("user");
		int clientId = vo.getUserId();
		Map<String, Object> response = new HashMap<>();
		response.put("clientId", clientId);
		return response;
	}

	@GetMapping("/video")
	public String asd(HttpSession session, Model model) {
		UserVo vo = (UserVo) session.getAttribute("user");
		int clientId = vo.getUserId();
		comfyUIVideoGenerator.connectWebSocket(clientId);

		// 사용자의 이미지 리스트 가져오기
		CreationVo creationVo = new CreationVo();
		creationVo.setUserId(clientId);
		creationVo.setArtForm(2); // 예: 소설 형식을 나타내는 코드 2

		// 사용자의 이미지 목록 가져오기
		List<ImageVo> imageList = imageService.getImageDataByUserId(creationVo);
		model.addAttribute("imageList", imageList);

		return "jiwon/video_generate";
	}

	@PostMapping("/vidgenerate")
	public String generateVideo(@RequestParam("sampler_index") String samplerIndex, @RequestParam("steps") int steps,
			@RequestParam("width") int width, @RequestParam("height") int height,
			@RequestParam("cfg_scale") int cfgScale, @RequestParam("seed") int seed,
			@RequestParam("selectedFilename") String filenam, @RequestParam("fps") int fps,
			@RequestParam("videoFrames") int videoFrames, Model model, HttpSession session) {

		// 세션에서 클라이언트 ID 가져오기
		UserVo userVo = (UserVo) session.getAttribute("user");
		int clientId = userVo.getUserId();
		System.err.println("이거 넘어오긴 함");
		try {
			// WebSocket 연결 확인
			if (comfyUIVideoGenerator.isConnected()) {
				// 비디오 생성 요청 시작 로그
				System.out.println("비디오 생성 요청 시작 - Client ID: " + clientId);

				// 비디오 생성 요청 비동기 실행
				CompletableFuture<resultVo> resultVideoVoFuture = comfyUIVideoGenerator.vidqueuePrompt(samplerIndex,
						steps, width, height, cfgScale, seed, clientId, filenam);

				System.out.println("비디오 생성 요청 전송 완료");

				// WebSocket 응답을 기다리고 처리
				resultVo result = resultVideoVoFuture.join(); // 결과가 올 때까지 무제한 대기

				// 성공 시 비디오 URL 및 파일명을 모델에 추가 (현재는 임시 출력)
				String videoUrl = result.getImageUrl(); // 비디오 URL을 리턴하는 구조로 설정되어 있다고 가정
				String generatedFilename = result.getFilename();

				model.addAttribute("videoUrl", videoUrl);
				model.addAttribute("filename", generatedFilename);
				model.addAttribute("message", "Video generation successful.");

				// UserVo 세션에서 가져오기
				UserVo vo = (UserVo) session.getAttribute("user");
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("userId", vo.getUserId());
				paramMap.put("artForm", 4); // artForm은 4로 지정
				imageService.insertCreation(paramMap);

				// 비디오 데이터 저장
				int maxId = imageService.getMax();
				Map<String, Object> videoDataMap = new HashMap<>();
				videoDataMap.put("creationId", maxId); // creationId를 userId로 가정
				videoDataMap.put("imageUrl", videoUrl);
				videoDataMap.put("fileName", generatedFilename);
				videoDataMap.put("modelCheck", "svd.safetensors"); // 필요에 따라 설정
				videoDataMap.put("sampler", samplerIndex);
				videoDataMap.put("prompt", "");
				videoDataMap.put("nPrompt", "");
				videoDataMap.put("steps", steps);
				videoDataMap.put("cfg", cfgScale);
				videoDataMap.put("seed", seed);
				videoDataMap.put("width", width);
				videoDataMap.put("height", height);

				// 세션에 완료 상태 저장 (이후의 작업을 위해)
				// 이미지 데이터 삽입
				imageService.imageGenerate(videoDataMap);
				session.setAttribute("videoGenerated", true);
				session.setAttribute("videoUrl", videoUrl);

				System.out.println("비디오 생성 완료 - URL: " + videoUrl + ", Filename: " + generatedFilename);

			} else {
				// WebSocket 연결 실패 시 메시지 설정
				model.addAttribute("message", "WebSocket is not connected.");
				System.err.println("WebSocket 연결 실패 - Client ID: " + clientId);
			}
		} catch (Exception e) {
			// 예외 발생 시 로그 출력 및 에러 메시지 설정
			model.addAttribute("message", "Error generating video: " + e.getMessage());
			e.printStackTrace();
		}

		// 결과 페이지로 이동
		return "jiwon/video_result";
	}

}
