package com.team3webnovel.controllers.gije_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class gijeImageGenerator {
	
	private static final String IMAGE_SAVE_FOLDER = "C:/generated_images"; // 이미지 저장 경로
	
	@GetMapping("/image")
	public String imageGenerator(Model model) {
		try {
			List<String> checkPointModels = getCheckpointModels();
			model.addAttribute("models", checkPointModels);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "gijeTest/create_tool";
	}
	
	@PostMapping("/create/image")
	public void getImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    
	    // 클라이언트에서 받은 json 요청 데이터
	    BufferedReader reader = req.getReader();
	    StringBuilder json = new StringBuilder();
	    String line;
	    while((line = reader.readLine()) != null) {
	        json.append(line);
	    }
	    
	    try {
	        // API 요청을 보낼 URL
	        String apiUrl = "http://192.168.0.241:7860/sdapi/v1/txt2img";
	        
	        JSONObject jsonData = new JSONObject(json.toString());
	        
	        // 요청 보내기 설정
	        URL url = new URL(apiUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setDoOutput(true);
	        
	        // 요청 전송
	        try (OutputStream os = connection.getOutputStream()) {
	            byte[] input = jsonData.toString().getBytes("utf-8");
	            os.write(input, 0, input.length);
	        }
	        
	        int responseCode = connection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
	            StringBuilder apiResponse = new StringBuilder();
	            String responseLine;
	            while ((responseLine = in.readLine()) != null) {
	                apiResponse.append(responseLine.trim());
	            }
	            
	            JSONObject apiResponseJson = new JSONObject(apiResponse.toString());
	            JSONArray imagesArray = apiResponseJson.getJSONArray("images");  // 여러 이미지 데이터를 배열로 받음
	            
	            // 현재 날짜 및 시간 기준으로 파일명 생성
	            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	            
	            // 이미지 저장 폴더가 존재하는지 확인하고, 없으면 생성
	            File saveFolder = new File(IMAGE_SAVE_FOLDER);
	            if (!saveFolder.exists()) {
	                saveFolder.mkdirs(); // 경로 생성
	            }

	            // 여러 이미지를 저장
	            List<String> base64ImageList = new ArrayList<>();
	            for (int i = 0; i < imagesArray.length(); i++) {
	                String base64Image = imagesArray.getString(i);
	                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	                
	                String imagePath = Paths.get(IMAGE_SAVE_FOLDER, "generated_image_" + timestamp + "_" + i + ".png").toString();
	                
	                // 이미지 파일로 저장
	                try (FileOutputStream fos = new FileOutputStream(imagePath)) {
	                    fos.write(imageBytes);
	                }
	                
	                // 이미지 데이터를 리스트에 추가
	                base64ImageList.add(base64Image);
	                
	                System.out.println("Image saved to: " + imagePath);
	            }
	            
	            // 메타데이터 JSON 객체 생성
	            JSONObject metadata = apiResponseJson.getJSONObject("parameters");
	            String metadataPath = Paths.get(IMAGE_SAVE_FOLDER, "metadata_" + timestamp + ".json").toString();
	            
	            // 메타데이터 파일로 저장
	            try (FileWriter file = new FileWriter(metadataPath)) {
	                file.write(metadata.toString(4)); // Pretty print with 4 spaces
	            }

	            // 응답을 JSON 형식으로 클라이언트에게 반환 (여러 이미지 포함)
	            resp.setContentType("application/json");
	            resp.setCharacterEncoding("UTF-8");
	            resp.getWriter().write(new JSONObject().put("images", base64ImageList).toString());
	            
	        } else {
	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            resp.getWriter().write("Failed to generate images");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
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
	
	@PostMapping("/change/checkpoint")
	public void changeCheckPoint(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    // 클라이언트에서 받은 JSON 요청 데이터
	    BufferedReader reader = req.getReader();
	    StringBuilder json = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        json.append(line);
	    }

	    String apiUrl = "http://192.168.0.241:7860/sdapi/v1/options";

	    HttpURLConnection connection = null;
	    try {
	        JSONObject jsonData = new JSONObject(json.toString());

	        // 요청 보내기 설정
	        URL url = new URL(apiUrl);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setDoOutput(true);

	        // 요청 전송
	        try (OutputStream os = connection.getOutputStream()) {
	            byte[] input = jsonData.toString().getBytes("utf-8");
	            os.write(input, 0, input.length);
	        }
	        
	        // 응답 코드 확인
	        int responseCode = connection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            // 성공적으로 처리된 경우 200 OK 응답
	            resp.setStatus(HttpServletResponse.SC_OK);
	        } else {
	            // 실패한 경우 적절한 응답 코드 설정
	            resp.sendError(responseCode, "Failed to update checkpoint");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
	    } finally {
	        if (connection != null) {
	            connection.disconnect(); // 연결 종료
	        }
	    }
	}
	
}