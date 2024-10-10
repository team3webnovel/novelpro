package com.team3webnovel.services;

import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.VideoVo;

import java.util.List;
import java.util.Map;

public interface VideoService {

    // 비디오 생성 요청
    List<VideoVo> generateVideo(String prompt, boolean makeHighResolution, Map<String, String> errorMap);

    // 사용자 ID로 저장된 비디오 가져오기
    List<VideoVo> getStoredVideoByUserId(Integer userId);

    // creationId로 특정 비디오 정보 조회
    VideoVo getVideoByCreationId(int creationId);
    
    // Map을 이용하여 생성된 비디오를 DB에 저장하는 메서드
    void insertCreation(Map<String, Object> creationData);
    
    // creation_id의 max 값 받아오기
    int getMax();
    
    // 비디오 생성 프롬프트 db 저장
    void videoGenerate(Map<String, Object> videoData);
    
    List<VideoVo> getVideoDataByUserId(CreationVo vo);
    
    VideoVo getAllInformation(int creationId);
}
