package com.team3webnovel.vo;

public class resultVo {

    private String imageUrl;   // 이미지 URL을 저장할 필드
    private String filename;   // 파일명을 저장할 필드

    // 기본 생성자
    public resultVo() {
    }

    // 매개변수가 있는 생성자 (이미지 URL과 파일명 초기화)
    public resultVo(String imageUrl, String filename) {
        this.imageUrl = imageUrl;
        this.filename = filename;
    }

    // Getter 및 Setter 메서드
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "resultVo{" +
                "imageUrl='" + imageUrl + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }

    public static void main(String[] args) {
        // 테스트
        resultVo result = new resultVo("http://example.com/image.png", "image.png");
        System.out.println("Image URL: " + result.getImageUrl());
        System.out.println("Filename: " + result.getFilename());
    }
}
