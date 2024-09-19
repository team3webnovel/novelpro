package com.team3webnovel.vo;

public class CreationVo {
    private int creationId;
    private int userId;
    private int artForm;

    // 기본 생성자
    public CreationVo() {}

    // 매개변수 있는 생성자
    public CreationVo(int creationId, int userId, int artForm) {
        this.creationId = creationId;
        this.userId = userId;
        this.artForm = artForm;
    }

    // Getters and Setters
    public int getCreationId() {
        return creationId;
    }

    public void setCreationId(int creationId) {
        this.creationId = creationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArtForm() {
        return artForm;
    }

    public void setArtForm(int artForm) {
        this.artForm = artForm;
    }

    @Override
    public String toString() {
        return "CreationVo{" +
                "creationId=" + creationId +
                ", userId=" + userId +
                ", artForm=" + artForm +
                '}';
    }
}
