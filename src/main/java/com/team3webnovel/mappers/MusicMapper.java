package com.team3webnovel.mappers;

public interface MusicMapper {
    void insertCreation(int userId, int artForm);  // creation 테이블에 삽입

    int getLastCreationId();  // 마지막으로 삽입된 creation_id 가져오기

    void insertMusicData(int creationId, String title, String lyric, String audioUrl, String imageUrl, 
                         String modelName, String gptDescriptionPrompt, String type, String tags, String errorMessage);  // music_data 테이블에 삽입
}
