package com.team3webnovel.vo;

import java.sql.Timestamp;

public class NovelVo {
    private int novelId;         // 소설 ID
    private int userId;          // 작성자(사용자) ID
    private String title;        // 소설 제목
    private String content;      // 소설 내용
    private String coverImageUrl; // 커버 이미지 URL
    private Timestamp createdAt; // 생성일
    private Timestamp updatedAt; // 수정일
    private int viewCount;       // 조회수

    // 기본 생성자
    public NovelVo() {}

    // 생성자
    public NovelVo(int novelId, int userId, String title, String content, String coverImageUrl, Timestamp createdAt, Timestamp updatedAt, int viewCount) {
        this.novelId = novelId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.coverImageUrl = coverImageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.viewCount = viewCount;
    }

    // Getter 및 Setter 메서드

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    // ToString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "NovelVo{" +
                "novelId=" + novelId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", viewCount=" + viewCount +
                '}';
    }
}