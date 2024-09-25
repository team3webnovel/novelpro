package com.team3webnovel.services;

import com.team3webnovel.mappers.UserMapper;
import com.team3webnovel.vo.UserVo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.validator.routines.EmailValidator;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private PwService pwService;  // 비밀번호를 가져오는 서비스

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserVo findUserByUsername(String username) {
        logger.debug("findUserByUsername called with username: {}", username);
        return userMapper.findUserByUsername(username);
    }

    @Override
    public UserVo findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
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

    // 이메일 인증 토큰 전송
    @Override
    public void sendEmailToken(String email, String token) throws MessagingException {
        logger.debug("sendEmailToken called for email: {}", email);

        // DB에서 구글 앱 비밀번호를 가져와 JavaMailSender의 패스워드를 동적으로 설정
        String googleAppPassword = pwService.getGoogleAppPassword();
        ((JavaMailSenderImpl) mailSender).setPassword(googleAppPassword);

        // 이메일 전송을 위한 MimeMessage 생성
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("이메일 인증 코드");
        helper.setText("인증번호: " + token, true);

        // 이메일 검증 후 처리
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("이메일 주소가 올바르지 않습니다.");
        }

        try {
            mailSender.send(message);
            logger.info("Email sent successfully to {}", email);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", email, e.getMessage());
            throw new MessagingException("이메일 전송에 실패했습니다.", e);
        }
    }
}
