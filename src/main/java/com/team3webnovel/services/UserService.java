package com.team3webnovel.services;

import com.team3webnovel.vo.UserVo;

public interface UserService {
    // 회원가입
    void registerUser(UserVo user) throws Exception;

    // 사용자 이름으로 사용자 찾기
    UserVo findUserByUsername(String username);
    
    // 구글 사용자 - 이메일로 기존 등록 여부 확인
    public UserVo findUserByEmail(String email);

    // 비밀번호 비교 (암호화된 비밀번호 비교)
    boolean isPasswordMatch(String rawPassword, String hashedPassword);

    // 이메일 인증 토큰 전송
    void sendEmailToken(String email, String token) throws Exception;

	boolean resetPassword(String email, String newPassword);

	void updateUserPassword(UserVo user);

	void sendTemporaryPasswordEmail(String email, String temporaryPassword);
	
}
