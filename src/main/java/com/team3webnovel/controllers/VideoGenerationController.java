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

	// GET 요청으로 JSP 페이지 렌더링
	@GetMapping("/generate")
	public String showGeneratePage(HttpSession session) {
		UserVo vo = (UserVo) session.getAttribute("user");
		int clientId = vo.getUserId();
		comfyUIVideoGenerator.connectWebSocket(clientId);
		return "jiwon/video_generate"; // generate.jsp 페이지로 이동
	}

//    @GetMapping("/upload")
//    public String test() {
//       return "sungmin/upload";
//    }

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

    // POST 요청으로 프롬프트를 받아 이미지 생성 요청
    @PostMapping("/generate")
    public String generateImage(
            @RequestParam("prompt") String prompt,
            @RequestParam("negative_prompt") String negativePrompt,
            @RequestParam("sampler_index") String samplerIndex,
            @RequestParam("steps") int steps,
            @RequestParam("width") int width,
            @RequestParam("height") int height,
            @RequestParam("cfg_scale") int cfgScale,
            @RequestParam("seed") int seed,
            @RequestParam("checkpoint") String checkpoint,
            @RequestParam("selectedFilename") String filenam,
            Model model, HttpSession session) {
       UserVo userVo = (UserVo)session.getAttribute("user");
        int clientId = userVo.getUserId();
        try {
            if (comfyUIVideoGenerator.isConnected()) {
                // ComfyUIImageGenerator에 필요한 파라미터를 모두 넘겨서 처리
                CompletableFuture<resultVo> resultVoFuture = comfyUIVideoGenerator.vidqueuePrompt( 
                        samplerIndex, 
                        steps, 
                        width, 
                        height, 
                        cfgScale, 
                        seed,
                        clientId,
                        filenam
                );

                // WebSocket에서 'execution_success' 메시지를 무제한 대기
                resultVo result = resultVoFuture.join();  // join()으로 결과가 나올 때까지 무제한 대기

                // resultVo에서 이미지 URL과 파일명을 각각 가져와서 모델에 추가
                String imageUrl = result.getImageUrl();
                String filename = result.getFilename();

                model.addAttribute("imageUrl", imageUrl);
                model.addAttribute("filename", filename);  // 파일명도 필요시 출력
                model.addAttribute("message", "Image generation successful.");

                // UserVo 세션에서 가져오기
                UserVo vo = (UserVo) session.getAttribute("user");
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("userId", vo.getUserId());
                paramMap.put("artForm", 2);  // artForm은 2로 지정
                imageService.insertCreation(paramMap);

                int maxId = imageService.getMax();
                Map<String, Object> imageDataMap = new HashMap<>();
                imageDataMap.put("creationId", maxId);
                imageDataMap.put("imageUrl", imageUrl);
                imageDataMap.put("fileName", filename);
                imageDataMap.put("modelCheck", checkpoint);
                imageDataMap.put("sampler", samplerIndex);
                imageDataMap.put("prompt", prompt);
                imageDataMap.put("nPrompt", negativePrompt);
                imageDataMap.put("steps", steps);
                imageDataMap.put("cfg", cfgScale);
                imageDataMap.put("seed", seed);
                imageDataMap.put("width", width);
                imageDataMap.put("height", height);

                System.err.println(imageDataMap);

                // 이미지 데이터 삽입
                imageService.imageGenerate(imageDataMap);

                session.setAttribute("imageGenerated", true);  // 세션에 완료 상태 저장
                session.setAttribute("imageUrl", imageUrl);    // 세션에 URL 저장
            } else {
                model.addAttribute("message", "WebSocket is not connected.");
            }
        } catch (Exception e) {
            // 예외 처리: 이미지 생성 중 오류가 발생했을 때
            model.addAttribute("message", "Error generating image: " + e.getMessage());
        }

        return "sungmin/result";  // 결과 페이지로 이동
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
    public String generateVideo(@RequestParam("sampler_index") String samplerIndex, 
                                @RequestParam("steps") int steps,
                                @RequestParam("width") int width, 
                                @RequestParam("height") int height,
                                @RequestParam("cfg_scale") int cfgScale, 
                                @RequestParam("seed") int seed,
                                @RequestParam("selectedFilename") String filenam, 
                                @RequestParam("fps") int fps,
                                @RequestParam("videoFrames") int videoFrames, 
                                Model model, HttpSession session) {
        
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
                CompletableFuture<resultVo> resultVideoVoFuture = comfyUIVideoGenerator.vidqueuePrompt(
                    samplerIndex, 
                    steps, 
                    width, 
                    height, 
                    cfgScale, 
                    seed, 
                    clientId, 
                    filenam
                );
                
                System.out.println("비디오 생성 요청 전송 완료");
                
                // WebSocket 응답을 기다리고 처리
                resultVo result = resultVideoVoFuture.join(); // 결과가 올 때까지 무제한 대기
                
                // 성공 시 비디오 URL 및 파일명을 모델에 추가 (현재는 임시 출력)
                String videoUrl = result.getImageUrl();  // 비디오 URL을 리턴하는 구조로 설정되어 있다고 가정
                String generatedFilename = result.getFilename();
                
                model.addAttribute("videoUrl", videoUrl);
                model.addAttribute("filename", generatedFilename);
                model.addAttribute("message", "Video generation successful.");
                
                // 세션에 완료 상태 저장 (이후의 작업을 위해)
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
