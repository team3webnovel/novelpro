package com.team3webnovel.vo;

import java.sql.Timestamp;

public class ImageVo {
    private int creationId;
    private String title;
    private String imageUrl;
    private String description;
    private String modelName;
    private String gptDescriptionPrompt;
    private String type;
    private String tags;
    private Timestamp createdAt;
    private String errorMessage;

    // 기본 생성자
    public ImageVo() {}

    // 이미지 생성 시 필요한 주요 필드만 받는 생성자
    public ImageVo(String title, String imageUrl, String description) {
        super();
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // 생성된 이미지 정보로 사용할 생성자
    public ImageVo(int creationId, String title, String imageUrl, Timestamp createdAt) {
        super();
        this.creationId = creationId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    // 전체 필드를 받는 생성자
    public ImageVo(int creationId, String title, String imageUrl, String description,
                   String modelName, String gptDescriptionPrompt, String type, String tags,
                   Timestamp createdAt, String errorMessage) {
        this.creationId = creationId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.modelName = modelName;
        this.gptDescriptionPrompt = gptDescriptionPrompt;
        this.type = type;
        this.tags = tags;
        this.createdAt = createdAt;
        this.errorMessage = errorMessage;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
