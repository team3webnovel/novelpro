package com.team3webnovel.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ImageBoardVo {
	private int boardId;
	private int userId;
	private int creationId;
	private Date createAt;
	private int viewCount;
	private String content;
	private String imageUrl;
	private int publicCheck;
	private BigDecimal like;
	
	public ImageBoardVo() {
		
	}
	
	public ImageBoardVo(int userId, int creationId, String content, int publicCheck) {
		this.userId = userId;
		this.creationId = creationId;
		this.content = content;
		this.publicCheck = publicCheck;
	}

	public ImageBoardVo(int boardId, int userId, int creationId, Date createAt, int viewCount, String content, String imageUrl, int publicCheck, BigDecimal like) {
		super();
		this.boardId = boardId;
		this.userId = userId;
		this.creationId = creationId;
		this.createAt = createAt;
		this.viewCount = viewCount;
		this.content = content;
		this.imageUrl = imageUrl;
		this.publicCheck = publicCheck;
		this.like = like;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCreationId() {
		return creationId;
	}

	public void setCreationId(int creationId) {
		this.creationId = creationId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getPublicCheck() {
		return publicCheck;
	}

	public void setPublicCheck(int publicCheck) {
		this.publicCheck = publicCheck;
	}

    public void setLike(BigDecimal like) {
        this.like = like; // BigDecimal을 int로 변환하여 설정
    }

    public BigDecimal getLike() {
        return like;
    }

	public void setLike(int i) {
		BigDecimal bd = new BigDecimal(i);
		this.like = bd;
	}
}
