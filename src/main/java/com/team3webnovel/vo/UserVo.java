package com.team3webnovel.vo;

import java.time.LocalDateTime;

public class UserVo {
    private int userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;  // String 대신 LocalDateTime 사용
    
	// Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

	@Override
	public String toString() {
		return "UserVo [userId=" + userId + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", createdAt=" + createdAt + "]";
	}
    
    
}