package com.team3webnovel.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.team3webnovel.vo.CreationVo;
import com.team3webnovel.vo.ImageVo;
import com.team3webnovel.vo.VideoVo;

public interface VideoMapper {

    // creation 데이터를 삽입할 때 Map으로 전달
    void insertCreation(Map<String, Object> params);

    // 마지막으로 삽입된 creation_id 가져오기
    Integer getLastCreationId();

    // video_data 테이블에 삽입할 때도 Map으로 전달
    void insertVideoData(Map<String, Object> params);
    
    // 특정 사용자 ID에 대한 비디오 데이터를 가져옴
    List<VideoVo> getVideoDataByUserId(CreationVo vo);
    
    // 새로운 메서드: 사용자 ID와 artForm에 맞는 비디오 리스트를 가져옴
    List<VideoVo> getVideoByUserIdAndArtForm(@Param("userId") Integer userId, @Param("artForm") int artForm);
    
    // creationId로 특정 비디오 정보 조회
    VideoVo getVideoByCreationId(@Param("creationId") int creationId);
    
    // creationId로 비디오의 모든 정보 조회
    VideoVo getAllInformation(int creationId);
    
    void updateVideoTitle(VideoVo vo);
    
    void deleteVideoById(int creationId);
    
    void deleteCreationById(int creationId);
    
    void updateCreationId(int creationId);
}
