package com.team3webnovel.vo;

public class MusicVo {
<<<<<<< HEAD
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
=======
    private int creationId;  // creation_id를 위한 필드
    private String title;
    private String lyric;
    private String audioUrl;
    private String imageUrl;  // 커버 이미지를 저장하는 필드
    private String modelName;  // 모델 이름
    private String gptDescriptionPrompt;  // GPT 생성 프롬프트
    private String type;  // 요청 타입
    private String tags;  // 음악 관련 태그
    private String errorMessage;  // 오류 메시지

    // 기본 생성자
    public MusicVo() {}

    // 생성자
    public MusicVo(int creationId, String title, String lyric, String audioUrl, String imageUrl,
                   String modelName, String gptDescriptionPrompt, String type, String tags, String errorMessage) {
        this.creationId = creationId;
        this.title = title;
        this.lyric = lyric;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.modelName = modelName;
        this.gptDescriptionPrompt = gptDescriptionPrompt;
        this.type = type;
        this.tags = tags;
        this.errorMessage = errorMessage;
    }

    // Getters and Setters

    public MusicVo(String title, String lyric, String audioUrl, String imageUrl) {
		super();
		this.title = title;
		this.lyric = lyric;
		this.audioUrl = audioUrl;
		this.imageUrl = imageUrl;
	}

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
>>>>>>> refs/remotes/kyogre/kyogre
    }
}
