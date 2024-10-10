package com.team3webnovel.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;

@Service
public class ImageUploadService {

    private static final String SERVER_IP = "192.168.0.237";  // ComfyUI 서버 IP
    private static final String SERVER_PORT = "8188";  // ComfyUI 서버 포트

    public String uploadImageToComfyUI(File imageFile, String fileName) throws IOException {
        String uploadUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/upload/image";
        
        RestTemplate restTemplate = new RestTemplate();

        // 파일을 업로드할 수 있도록 FileSystemResource 사용
        FileSystemResource fileResource = new FileSystemResource(imageFile);

        // 헤더 설정 (Content-Type: multipart/form-data)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // MultiValueMap에 파일과 파일 이름을 담음
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", fileResource);  // 파일 데이터
        body.add("fileName", fileName);   // 파일명 추가

        // 파일과 이름을 포함한 엔터티 생성
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // 서버로 파일 업로드 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, entity, String.class);

        // 응답 상태 확인
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();  // 성공적으로 업로드된 경우 서버에서 반환된 내용을 리턴
        } else {
            throw new IOException("Failed to upload image to ComfyUI. Status code: " + response.getStatusCode());
        }
    }
}
