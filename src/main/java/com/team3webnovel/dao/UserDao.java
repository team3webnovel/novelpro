package com.team3webnovel.dao;

import com.team3webnovel.vo.UserVo;

public interface UserDao {
    UserVo findUserByUsername(String username);
    void insertUser(UserVo user);
}
