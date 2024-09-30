package com.team3webnovel.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3webnovel.mappers.UserMapper;
import com.team3webnovel.vo.UserVo;

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

    @Override
    public List<UserVo> getUserName() {
        return userMapper.getUserName();
    }
}
