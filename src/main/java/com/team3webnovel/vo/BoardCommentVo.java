package com.team3webnovel.vo;

import java.util.Date;

public class BoardCommentVo {

	private int commentId;
	private int boardId;
	private int userId;
	private String userName;
	private String content;
	private Date createdAt;
	
	public BoardCommentVo(int commentId, int boardId, int userId, String userName, String content, Date createdAt) {
		super();
		this.commentId = commentId;
		this.boardId = boardId;
		this.userId = userId;
		this.userName = userName;
		this.content = content;
		this.createdAt = createdAt;
	}
	
	public BoardCommentVo() {
		
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
