package com.team3webnovel.vo;

public class VideoVo {

	private int creationId; // 비디오 생성 ID
	private int userId; // 사용자 ID
	private String videoUrl; // 비디오 URL
	private String videoFilename; // 비디오 파일명
	private String prompt; // 비디오 생성에 사용된 프롬프트
	private String modelCheck; // 체크포인트 (모델 이름)
	private String sampler; // 샘플러
	private String initImage; // 초기 이미지
	private int steps; // 생성 단계 수
	private double cfg; // CFG 스케일
	private int seed; // 랜덤 시드
	private int width; // 비디오 프레임 너비
	private int height; // 비디오 프레임 높이
	private int videoFrames; // 비디오 프레임 수
	private int motionBucketId; // 모션 버킷 ID
	private int fps; // 초당 프레임 수 (FPS)

	// 기본 생성자
	public VideoVo() {
	}

	// 매개변수가 있는 생성자
	public VideoVo(int creationId, int userId, String videoUrl, String videoFilename, String prompt, String modelCheck,
			String sampler, String initImage, int steps, double cfg, int seed, int width, int height, int videoFrames,
			int motionBucketId, int fps) {
		this.creationId = creationId;
		this.userId = userId;
		this.videoUrl = videoUrl;
		this.videoFilename = videoFilename;
		this.prompt = prompt;
		this.modelCheck = modelCheck;
		this.sampler = sampler;
		this.initImage = initImage;
		this.steps = steps;
		this.cfg = cfg;
		this.seed = seed;
		this.width = width;
		this.height = height;
		this.videoFrames = videoFrames;
		this.motionBucketId = motionBucketId;
		this.fps = fps;
	}

	// Getter 및 Setter 메서드
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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoFilename() {
		return videoFilename;
	}

	public void setVideoFilename(String videoFilename) {
		this.videoFilename = videoFilename;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getModelCheck() {
		return modelCheck;
	}

	public void setModelCheck(String modelCheck) {
		this.modelCheck = modelCheck;
	}

	public String getSampler() {
		return sampler;
	}

	public void setSampler(String sampler) {
		this.sampler = sampler;
	}

	public String getInitImage() {
		return initImage;
	}

	public void setInitImage(String initImage) {
		this.initImage = initImage;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public double getCfg() {
		return cfg;
	}

	public void setCfg(double cfg) {
		this.cfg = cfg;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getVideoFrames() {
		return videoFrames;
	}

	public void setVideoFrames(int videoFrames) {
		this.videoFrames = videoFrames;
	}

	public int getMotionBucketId() {
		return motionBucketId;
	}

	public void setMotionBucketId(int motionBucketId) {
		this.motionBucketId = motionBucketId;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	@Override
	public String toString() {
		return "VideoVo{" + "creationId=" + creationId + ", userId=" + userId + ", videoUrl='" + videoUrl + '\''
				+ ", videoFilename='" + videoFilename + '\'' + ", prompt='" + prompt + '\'' + ", modelCheck='"
				+ modelCheck + '\'' + ", sampler='" + sampler + '\'' + ", initImage='" + initImage + '\'' + ", steps="
				+ steps + ", cfg=" + cfg + ", seed=" + seed + ", width=" + width + ", height=" + height
				+ ", videoFrames=" + videoFrames + ", motionBucketId=" + motionBucketId + ", fps=" + fps + '}';
	}
}
