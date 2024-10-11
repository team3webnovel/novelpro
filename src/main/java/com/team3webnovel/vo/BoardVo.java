package com.team3webnovel.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BoardVo {
	int boardId;
	int userId;
	String userName;
	String title;
	String content;
	Date createdAt;
	Date updatedAt;
	int viewCount;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm");
	
	public BoardVo(int boardId, int userId, String userName, String title, String content, Date createdAt, Date updatedAt,
			int viewCount) {
		super();
		this.boardId = boardId;
		this.userId = userId;
		this.userName = userName;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.viewCount = viewCount;
	}
	
	public BoardVo() {
		
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
