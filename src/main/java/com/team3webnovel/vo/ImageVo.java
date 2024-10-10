package com.team3webnovel.vo;

import java.sql.Timestamp;

public class ImageVo {
    private int creationId;
    private String imageUrl;
    private String modelCheck;
    private String sampler;
    private String prompt;
    private String nPrompt;
    private Timestamp createdAt;
    private int steps;
    private int cfg;
    private int seed;
    private int width;
    private int height;
    private String filename;
    private String title;

    // 기본 생성자
    public ImageVo() {
    	
    }

	public ImageVo(int creationId, String imageUrl, String modelCheck, String sampler, String prompt, String nPrompt,
			Timestamp createdAt, int steps, int cfg, int seed, int width, int height, String filename) {
		super();
		this.creationId = creationId;
		this.imageUrl = imageUrl;
		this.modelCheck = modelCheck;
		this.sampler = sampler;
		this.prompt = prompt;
		this.nPrompt = nPrompt;
		this.createdAt = createdAt;
		this.steps = steps;
		this.cfg = cfg;
		this.seed = seed;
		this.width = width;
		this.height = height;
		this.filename = filename;
	}

	public int getCreationId() {
		return creationId;
	}

	public void setCreationId(int creationId) {
		this.creationId = creationId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getnPrompt() {
		return nPrompt;
	}

	public void setnPrompt(String nPrompt) {
		this.nPrompt = nPrompt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getCfg() {
		return cfg;
	}

	public void setCfg(int cfg) {
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "ImageVo [creationId=" + creationId + ", imageUrl=" + imageUrl + ", modelCheck=" + modelCheck
				+ ", sampler=" + sampler + ", prompt=" + prompt + ", nPrompt=" + nPrompt + ", createdAt=" + createdAt
				+ ", steps=" + steps + ", cfg=" + cfg + ", seed=" + seed + ", width=" + width + ", height=" + height
				+ ", filename=" + filename + ", title=" + title + "]";
	}
	
	
    
    

}
