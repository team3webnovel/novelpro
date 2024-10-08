package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;

public interface ImageDao {
    // Map을 이용하여 생성된 이미지를 DB에 저장하는 메서드
    void insertCreation(Map<String, Object> creationData);
    
    // creation_id max 받아오기
    int getMax();
    
    // 이미지 생성 프롬프트 db 저장
    void imageGenerate(Map<String, Object> imageData);
    
    void fontGenerate(Map<String, Object> imageData);
    
    public List<ImageVo> getImageDataByUserId(CreationVo vo);  // 메서드 선언
    
    public ImageVo getAllInformation(int creationId);
    
    void updateImageTitle(ImageVo imageVo);
    
    void deleteImageById(int creationId);
    
    void deleteCreationById(int creationId);

}
