package com.team3webnovel.controllers.gije_test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testController {

	public List<String> getCheckpointModels() throws IOException {
	    URL url = new URL("http://192.168.0.241:7860/sdapi/v1/sd-models");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.connect();
	    
	    // 응답 코드 확인
	    int responseCode = conn.getResponseCode();
	    if (responseCode != 200) {
	        throw new RuntimeException("HttpResponseCode: " + responseCode);
	    }

	    // 응답 데이터 가져오기
	    Scanner sc = new Scanner(url.openStream());
	    StringBuilder inline = new StringBuilder();
	    while (sc.hasNext()) {
	        inline.append(sc.nextLine());
	    }
	    sc.close();
	    
	    // JSON 응답을 JSONArray로 변환
	    JSONArray jsonArray = new JSONArray(inline.toString());
	    
	    // model_name 값을 저장할 리스트
	    List<String> modelNames = new ArrayList<>();
	    
	    // 각 JSON 객체에서 "model_name" 값을 추출
	    for (int i = 0; i < jsonArray.length(); i++) {
	        JSONObject modelObject = jsonArray.getJSONObject(i);
	        String modelName = modelObject.getString("model_name");
	        modelNames.add(modelName);
	    }
	    
	    return modelNames;
	}
	
	@GetMapping("/image/model")
	public String imageGenerator(Model model) {
		try {
			List<String> checkPointModels = getCheckpointModels();
			System.out.println("Loaded models: " + checkPointModels);  // 디버깅용 출력
			model.addAttribute("models", checkPointModels);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "gijeTest/model_select";
	}
	
	private String generatedImageData; // Base64로 인코딩된 이미지 데이터
	private String prompt;
	private List<String> generatedImageDataList;
	
	@PostMapping("/image/result")
	@ResponseBody // JSON 응답을 위해 추가
	public ResponseEntity<String> receiveImages(@RequestBody Map<String, Object> payload) {
	    List<String> images = (List<String>) payload.get("images"); // 여러 이미지 데이터를 리스트로 받음
	    this.generatedImageDataList = images; // 리스트로 저장 (여러 이미지 처리)
	    this.prompt = (String) payload.get("prompt"); // 프롬프트 정보 저장
	    return ResponseEntity.ok("Images received"); // 성공적으로 수신했음을 응답
	}
	
    // 이미지 결과를 표시하는 GET 메서드
	@GetMapping("/image/result")
	public String imageResult(Model model) {
	    model.addAttribute("imageDataList", generatedImageDataList); // 여러 이미지 데이터를 모델에 추가
	    model.addAttribute("prompt", prompt);
	    return "gijeTest/tuto_result"; // JSP 페이지 반환
	}
	
}
