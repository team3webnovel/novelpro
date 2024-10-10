package com.team3webnovel.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team3webnovel.dao.ImageDao;
import com.team3webnovel.services.ImageService;
import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.UserVo;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FontController {

    private static final Logger logger = LoggerFactory.getLogger(FontController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageDao imageDao;
    
    @GetMapping("/generate-font")
    public String generateFontPage(Model model, HttpSession session) {
    	UserVo user = (UserVo) session.getAttribute("user");
    	
    	// 사용자의 이미지 리스트 가져오기
        CreationVo creationVo = new CreationVo();
        creationVo.setUserId(user.getUserId());
        creationVo.setArtForm(2); // 예: 소설 형식을 나타내는 코드 2
        
        // 이미지 데이터 모델에 추가 및 로그 출력
        List<ImageVo> imageList = imageDao.getImageDataByUserId(creationVo);
        model.addAttribute("imageList", imageList);
        System.err.println("Image List: " + imageList);  // 이미지 리스트 로그 출력

    	
        logger.info("Accessing generate-font page");
        return "generate/generate_font"; // JSP 파일 경로
    }

    @PostMapping("/generate-font")
    public ResponseEntity<String> saveFileName(@RequestBody Map<String, String> request, HttpSession session) {
        logger.info("Session ID: {}", session.getId());
        logger.info("Received request to save file name with data: {}", request);

        // 세션에서 UserVo 객체를 가져옴
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            logger.error("User not authenticated. Session does not contain valid user.");
            return ResponseEntity.status(400).body("User not authenticated");
        }

        // UserVo에서 user_id를 가져옴
        Integer userId = user.getUserId();
        logger.info("UserId from session: {}", userId);  // user_id 로그 출력

        String fileName = request.get("fileName");
        String imageUrl = "http://192.168.0.237:8188/view?filename=" + fileName + "&subfolder=&type=input&nocache=" + System.currentTimeMillis();

        // Creation 데이터를 삽입하기 위한 파라미터 준비
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("artForm", 3);  // artForm은 3으로 고정

        // creation 테이블에 데이터 삽입
        imageService.insertCreation(paramMap);

        // 가장 마지막으로 삽입된 creation_id 가져오기
        int creationId = imageService.getMax();

        // 이미지 데이터 삽입을 위한 추가 파라미터 설정
        paramMap.put("creationId", creationId);
        paramMap.put("imageUrl", imageUrl);
        paramMap.put("fileName", fileName);
        paramMap.put("title", fileName);

        // image_generation_data 테이블에 이미지 데이터를 삽입
        imageService.fontGenerate(paramMap);

        return ResponseEntity.ok("File name and data saved successfully.");
    }

}