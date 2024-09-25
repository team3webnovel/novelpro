package com.team3webnovel.services;

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
}
