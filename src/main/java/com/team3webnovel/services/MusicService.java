package com.team3webnovel.services;

import java.util.List;
import java.util.Map;

import com.team3webnovel.vo.MusicVo;

public interface MusicService {
    List<MusicVo> generateMusic(String prompt, boolean makeInstrumental, Map<String, String> errorMap) throws Exception;

    // 새롭게 추가된 저장된 음악을 불러오는 메서드
    List<MusicVo> getStoredMusicByUserId(Integer userId);
    
    // 특정 음악 정보 조회 메서드 추가
    MusicVo getMusicDetailsByCreationId(int creationId);
    
}