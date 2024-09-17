package com.team3webnovel.dao;

import com.team3webnovel.mappers.UserMapper;
import com.team3webnovel.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVo findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public void insertUser(UserVo user) {
        userMapper.insertUser(user);
    }
}
