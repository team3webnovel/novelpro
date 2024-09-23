package com.team3webnovel.services;

import com.team3webnovel.mappers.UserMapper;
import com.team3webnovel.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserVo findUserByUsername(String username) {
        logger.debug("findUserByUsername called with username: {}", username);
        return userMapper.findUserByUsername(username);
    }

    @Override
    @Transactional  // 트랜잭션 활성화
    public void registerUser(UserVo user) {
        logger.info("registerUser started for username: {}", user.getUsername());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        logger.info("Password encoding completed for username: {}", user.getUsername());

        user.setPassword(encodedPassword);

        try {
            logger.debug("Inserting user into the database: {}", user);
            userMapper.insertUser(user);  // 문제 발생 시 이 부분에서 로그를 확인
            logger.info("User successfully inserted into the database for username: {}", user.getUsername());
        } catch (Exception e) {
            logger.error("Error occurred while inserting user: ", e);
            throw e;  // 예외를 다시 던져서 트랜잭션이 롤백되도록 처리
        }
    }
    // 비밀번호 비교
    @Override
    public boolean isPasswordMatch(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
