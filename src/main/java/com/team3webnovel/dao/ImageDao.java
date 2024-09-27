package com.team3webnovel.dao;

import java.util.Map;

public interface ImageDao {
    // Map을 이용하여 생성된 이미지를 DB에 저장하는 메서드
    void insertCreation(Map<String, Object> creationData);
    
    // creation_id max 받아오기
    int getMax();
    
    // 이미지 생성 프롬프트 db 저장
    void imageGenerate(Map<String, Object> imageData);

}
