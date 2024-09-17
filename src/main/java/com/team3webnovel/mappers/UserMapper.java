package com.team3webnovel.mappers;

import com.team3webnovel.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVo findUserByUsername(String username);

    void insertUser(UserVo user);
}
