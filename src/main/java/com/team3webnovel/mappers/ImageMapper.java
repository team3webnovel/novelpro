package com.team3webnovel.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;

public interface ImageMapper {
    // creation 데이터를 삽입할 때 Map으로 전달
    void insertCreation(Map<String, Object> params);

    // 마지막으로 삽입된 creation_id 가져오기
    Integer getLastCreationId();

    // image_data 테이블에 삽입할 때도 Map으로 전달
    void insertImageData(Map<String, Object> params);
    
    void insertFontData(Map<String, Object> params);
    
    List<ImageVo> getImageDataByUserId(CreationVo vo);
    
    // 새로운 메서드: 사용자 ID와 artForm에 맞는 이미지 리스트를 가져옴
    List<ImageVo> getImageByUserIdAndArtForm(@Param("userId") Integer userId, @Param("artForm") int artForm);
    
    // creationId로 특정 이미지 정보 조회
    ImageVo getImageByCreationId(@Param("creationId") int creationId);
    

    ImageVo getAllInformation(int creationId);
}
