package com.team3webnovel.controllers.gije_test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3webnovel.services.GenerativeAIService;

@Controller
public class AiController {
	
	@Autowired
	private GenerativeAIService aiService;
	
	@GetMapping("/gije/ai")
	public void test() {
		String str = aiService.generateContent("한 명의 소녀가 주인공을 향해 화내는 장면을 그려줘. 그녀는 금발의 트윈테일이고, 머리는 붉은 리본으로 묶고 있어. 상체를 앞으로 내밀고 있는 구도로 한 손을 주먹쥐고 때릴 듯이 올리고 있어/ 그녀는 푸른 색 눈동자를 가지고 있고, 교복을 입고 있어. 배경은 하굣길 정도가 좋겠다.");
		System.err.println(str);
	}
	
	@RequestMapping(value="/gije/ai", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getPrompt(@RequestBody Map<String, String> input) {
		String inputStr = input.get("input");
		String translateStr = aiService.generateContent(inputStr);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> resultMap = null;
	    
	    try {
	        // JSON 문자열을 Map으로 변환
	        resultMap = objectMapper.readValue(translateStr, Map.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return resultMap;
	}

}
