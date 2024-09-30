package com.team3webnovel.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BoardCommentVo {

	private int commentId;
	private int boardId;
	private int userId;
	private String userName;
	private String content;
	private Date createdAt;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
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
	
	public String getFormattedCreatedAt() {
        if (createdAt == null) {
            return "";
        }

        Date now = new Date();
        long diffInMillis = now.getTime() - createdAt.getTime();
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
        long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis);

        if (diffInSeconds < 60) {
            return "1분 미만";
        } else if (diffInMinutes < 60) {
            return diffInMinutes + "분 전";
        } else if (diffInHours < 24) {
            return diffInHours + "시간 전";
        } else {
            return dateFormat.format(createdAt);
        }
    }
}
