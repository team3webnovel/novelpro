package com.team3webnovel.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3webnovel.comfyui.ComfyUIImageGenerator;
import com.team3webnovel.services.ImageService;
import com.team3webnovel.vo.UserVo;
import com.team3webnovel.vo.resultVo;

import jakarta.servlet.http.HttpSession;

@Controller
public class SimpleImageController {

	@Autowired
	private ImageService imageService;
	
	private final ComfyUIImageGenerator comfyUIImageGenerator;
	
	public SimpleImageController() {
        this.comfyUIImageGenerator = new ComfyUIImageGenerator();
    }
	
	@GetMapping("/images")
	public String form(HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("user");
		int clientId = userVo.getUserId();
    	comfyUIImageGenerator.connectWebSocket(clientId);
    	session.setAttribute("clientId", clientId);
    	return "generate/image_simple_form";
	}
	
	@RequestMapping(value="/images", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map <String, Object> genet(@RequestBody Map<String, Object> requestData, Model model, HttpSession session) {
		int clientId = (int)session.getAttribute("clientId");
		
		Map<String, Object> response = new HashMap<>();
		
		String prompt = (String) requestData.get("prompt");
	    String negativePrompt = (String) requestData.get("negative_prompt");
	    String samplerIndex = (String) requestData.get("sampler_index");
	    int steps = (int) requestData.get("steps");
	    int width = (int) requestData.get("width");
	    int height = (int) requestData.get("height");
	    int cfgScale = (int) requestData.get("cfg_scale");
	    int seed = (int) requestData.get("seed");
	    String checkpoint = (String) requestData.get("checkpoint");
	    
	    try {
            if (comfyUIImageGenerator.isConnected()) {
                // ComfyUIImageGenerator에 필요한 파라미터를 모두 넘겨서 처리
                CompletableFuture<resultVo> resultVoFuture = comfyUIImageGenerator.queuePrompt(
                        prompt, 
                        negativePrompt, 
                        samplerIndex, 
                        steps, 
                        width, 
                        height, 
                        cfgScale, 
                        seed,
                        checkpoint,
                        clientId
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
                
                response.put("success", true);
            } else {
                model.addAttribute("message", "WebSocket is not connected.");
            }
        } catch (Exception e) {
            // 예외 처리: 이미지 생성 중 오류가 발생했을 때
            model.addAttribute("message", "Error generating image: " + e.getMessage());
            response.put("success", false);
        }

        return response;  // 결과 페이지로 이동
	}
}
