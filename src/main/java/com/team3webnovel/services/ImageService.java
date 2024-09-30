package com.team3webnovel.services;

import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;

import java.util.List;
import java.util.Map;

public interface ImageService {
    // 이미지 생성 요청
    List<ImageVo> generateImage(String prompt, boolean makeHighResolution, Map<String, String> errorMap);

    // 사용자 ID로 저장된 이미지 가져오기
    List<ImageVo> getStoredImageByUserId(Integer userId);

    // creationId로 특정 이미지 정보 조회
    ImageVo getImageByCreationId(int creationId);
    
    // Map을 이용하여 생성된 이미지를 DB에 저장하는 메서드
    void insertCreation(Map<String, Object> creationData);
    
    // creation_id max 받아오기
    int getMax();
    
    // 이미지 생성 프롬프트 db 저장
    void imageGenerate(Map<String, Object> imageData);
    
    List<ImageVo> getImageDataByUserId(CreationVo vo);
    
}
