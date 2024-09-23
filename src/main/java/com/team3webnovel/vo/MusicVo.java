package com.team3webnovel.vo;

public class MusicVo {
    private String title;
    private String lyric;
    private String audioUrl;
    private String imageUrl;  // 커버 이미지를 저장하는 필드

    public MusicVo(String title, String lyric, String audioUrl, String imageUrl) {
        this.title = title;
        this.lyric = lyric;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
