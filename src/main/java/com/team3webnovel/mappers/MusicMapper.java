package com.team3webnovel.mappers;

import java.util.Map;

public interface MusicMapper {
    // creation 데이터를 삽입할 때 Map으로 전달
    void insertCreation(Map<String, Object> params);

    // 마지막으로 삽입된 creation_id 가져오기
    Integer getLastCreationId();

    // music_data 테이블에 삽입할 때도 Map으로 전달
    void insertMusicData(Map<String, Object> params);
}
