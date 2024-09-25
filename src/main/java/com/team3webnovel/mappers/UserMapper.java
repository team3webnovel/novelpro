package com.team3webnovel.mappers;

import com.team3webnovel.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserVo findUserByUsername(String username);

    UserVo findUserByEmail(String email);  // 이메일로 사용자 조회 메서드 추가

    void insertUser(UserVo user);

    void updateUserPassword(UserVo user);
    
    void updatePassword(UserVo user);
    
}
