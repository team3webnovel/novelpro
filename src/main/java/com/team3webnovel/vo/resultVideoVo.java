package com.team3webnovel.vo;

public class resultVideoVo {

    private String videoUrl;   // 비디오 URL을 저장할 필드
    private String filename;   // 파일명을 저장할 필드

    // 기본 생성자
    public resultVideoVo() {
    }

    // 매개변수가 있는 생성자 (비디오 URL과 파일명 초기화)
    public resultVideoVo(String videoUrl, String filename) {
        this.videoUrl = videoUrl;
        this.filename = filename;
    }

    // Getter 및 Setter 메서드
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "resultVideoVo{" +
                "videoUrl='" + videoUrl + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }

    public static void main(String[] args) {
        // 테스트
        resultVideoVo result = new resultVideoVo("http://example.com/video.png", "video.png");
        System.out.println("Video URL: " + result.getVideoUrl());
        System.out.println("Filename: " + result.getFilename());
    }

	
		
	
}
