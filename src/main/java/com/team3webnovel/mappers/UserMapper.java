package com.team3webnovel.mappers;

import com.team3webnovel.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVo findUserByUsername(String username);

    UserVo findUserByEmail(String email);  // 이메일로 사용자 조회 메서드 추가

    void insertUser(UserVo user);
}
