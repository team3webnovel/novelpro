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
    private String title;


	// 기본 생성자
    public ImageVo() {}
    
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
    
    

}
