package com.team3webnovel.vo;

public class BoardVo {
    private int id;
    private String title;
    private String author;
    private String createdDate;

    // 필요한 생성자 추가
    public BoardVo(int id, String title, String author, String createdDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createdDate = createdDate;
    }

    // Getter와 Setter 메서드 추가
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
