package com.team3webnovel.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3webnovel.comfyui.ComfyUIImageGenerator;
import com.team3webnovel.services.ImageService;
import com.team3webnovel.vo.UserVo;
import com.team3webnovel.vo.resultVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/images")
public class ImageGenerationController {
	
    @Autowired
    private ImageService imageService;

    private final ComfyUIImageGenerator comfyUIImageGenerator;

    public ImageGenerationController() {
        this.comfyUIImageGenerator = new ComfyUIImageGenerator();
    }

    // GET 요청으로 JSP 페이지 렌더링
    @GetMapping("/generate")
    public String showGeneratePage() {
        return "sungmin/generate"; // generate.jsp 페이지로 이동
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
            Model model, HttpSession session) {

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
                        checkpoint
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
                int userId = vo.getUserId();
                int artForm = 1;
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
                
            } else {
                model.addAttribute("message", "WebSocket is not connected.");
            }
        } catch (Exception e) {
            // 예외 처리: 이미지 생성 중 오류가 발생했을 때
            model.addAttribute("message", "Error generating image: " + e.getMessage());
        }

        return "sungmin/result";  // 결과 페이지로 이동
    }
    
    
    @GetMapping("/alert")
    public String alert(Model model) {
    	String clientId = comfyUIImageGenerator.getClientId();
    	System.err.println(clientId);
    	model.addAttribute(clientId);
    	return "sungmin/alert";
    }
}