package com.team3webnovel.vo;

import java.util.Date;

public class MusicVo {
    private int creationId;
    private String title;
    private String lyric;
    private String audioUrl;
    private String imageUrl;
    private Date createdAt;  // 생성 날짜 필드
    private String modelName;
    private String gptDescriptionPrompt;
    private String type;
    private String tags;
    private String errorMessage;

    public MusicVo(String title, String lyric, String audioUrl, String imageUrl) {
		super();
		this.title = title;
		this.lyric = lyric;
		this.audioUrl = audioUrl;
		this.imageUrl = imageUrl;
	}

	// Getters and Setters
    public int getCreationId() {
        return creationId;
    }

    public void setCreationId(int creationId) {
        this.creationId = creationId;
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getGptDescriptionPrompt() {
        return gptDescriptionPrompt;
    }

    public void setGptDescriptionPrompt(String gptDescriptionPrompt) {
        this.gptDescriptionPrompt = gptDescriptionPrompt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
