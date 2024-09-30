package com.team3webnovel.vo;

import java.sql.Timestamp;

public class NovelVo {
    // novel 테이블 필드
    private String genre;        // GENRE
    private String status;       // STATUS
    private int userId;          // USER_ID
    private String title;        // TITLE (novel 테이블의 제목)
    private int creationId;      // CREATION_ID (novel 테이블의 생성자 ID)
    private String intro;        // INTRO (소설 소개)
    private Timestamp createdAt; // CREATED_AT (novel 테이블의 생성 날짜)

    // novel_detail 테이블 필드
    private int novelId;         // NOVEL_ID
    private int episodeNo;       // EPISODE_NO
    private String contents;     // CONTENTS
    private int creationIdDetail;  // CREATION_ID (novel_detail 테이블의 생성자 ID)
    private String titleDetail;    // TITLE (novel_detail의 제목)

    // 기본 생성자
    public NovelVo() {
    }

    // novel_detail 필드 Getter/Setter
    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public int getEpisodeNo() {
        return episodeNo;
    }

    public void setEpisodeNo(int episodeNo) {
        this.episodeNo = episodeNo;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Timestamp getCreatedAtDetail() {
        return createdAt;
    }

    public void setCreatedAtDetail(Timestamp createdAtDetail) {
        this.createdAt = createdAtDetail;
    }

    public int getCreationIdDetail() {
        return creationIdDetail;
    }

    public void setCreationIdDetail(int creationIdDetail) {
        this.creationIdDetail = creationIdDetail;
    }

    public String getTitleDetail() {
        return titleDetail;
    }

    public void setTitleDetail(String titleDetail) {
        this.titleDetail = titleDetail;
    }

    // novel 테이블 필드 Getter/Setter
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getCreationId() {
        return creationId;
    }

    public void setCreationId(int creationId) {
        this.creationId = creationId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "NovelVo [genre=" + genre + ", status=" + status + ", userId=" + userId + ", title=" + title
                + ", creationId=" + creationId + ", intro=" + intro + ", createdAt=" + createdAt + ", novelId="
                + novelId + ", episodeNo=" + episodeNo + ", contents=" + contents + ", creationIdDetail="
                + creationIdDetail + ", titleDetail=" + titleDetail + "]";
    }
}
