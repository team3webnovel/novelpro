package com.team3webnovel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.team3webnovel.vo.MusicVo;

public interface MusicDao {
    // creation 데이터를 삽입할 때 Map으로 전달
    void insertCreation(Map<String, Object> params);

    // 마지막으로 삽입된 creation_id 가져오기
    Integer getLastCreationId();

    // music_data 테이블에 삽입할 때도 Map으로 전달
    void insertMusicData(Map<String, Object> params);
    
    // 새로운 메서드: 사용자 ID와 artForm에 맞는 음악 리스트를 가져옴
    List<MusicVo> getMusicByUserIdAndArtForm(@Param("userId") Integer userId, @Param("artForm") int artForm);
    
    // creationId로 특정 음악 정보 조회
    MusicVo getMusicByCreationId(@Param("creationId") int creationId);
    
}