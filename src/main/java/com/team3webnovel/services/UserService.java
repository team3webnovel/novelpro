package com.team3webnovel.services;

import com.team3webnovel.vo.UserVo;

public interface UserService {
    // 회원가입
    void registerUser(UserVo user) throws Exception;

    // 사용자 이름으로 사용자 찾기
    UserVo findUserByUsername(String username);

    // 비밀번호 비교 (암호화된 비밀번호 비교)
    boolean isPasswordMatch(String rawPassword, String hashedPassword);
}
