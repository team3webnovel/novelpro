package com.team3webnovel.dao;

import java.util.List;

import com.team3webnovel.vo.UserVo;

public interface UserDao {
    UserVo findUserByUsername(String username);
    void insertUser(UserVo user);
    
    public List <UserVo> getUserName();
}
