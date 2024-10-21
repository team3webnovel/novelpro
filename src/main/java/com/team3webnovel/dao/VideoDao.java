package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.VideoVo;

public interface VideoDao {
    
    // Map을 이용하여 생성된 비디오를 DB에 저장하는 메서드
    void insertCreation(Map<String, Object> creationData);
    
    // 가장 최근 생성된 creation_id를 반환하는 메서드
    int getMax();
    
    // 비디오 생성 프롬프트를 DB에 저장하는 메서드
    void videoGenerate(Map<String, Object> videoData);
    
    // 특정 사용자의 비디오 데이터를 조회하는 메서드
    List<VideoVo> getVideoDataByUserId(CreationVo vo);
    
    // creationId로 특정 비디오 정보를 조회하는 메서드
    VideoVo getAllInformation(int creationId);

    void updateVideoTitle(VideoVo videoVo);
    
    void deleteVideoById(int creationId);
    
    void deleteCreationById(int creationId);
    
    void updateCreationId(int creationId);

}
