package com.team3webnovel.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team3webnovel.dao.UserDao;
import com.team3webnovel.vo.UserVo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

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
        return userDao.findUserByUsername(username);
    }

    @Override
    public UserVo findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    @Transactional  // 트랜잭션 활성화
    public void registerUser(UserVo user) {
    	
//        logger.info("registerUser started for username: {}", user.getUsername());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        logger.info("Password encoding completed for username: {}", user.getUsername());

        user.setPassword(encodedPassword);

        try {
//            logger.debug("Inserting user into the database: {}", user);
            userDao.insertUser(user);  // 문제 발생 시 이 부분에서 로그를 확인
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
    @Override
    @Transactional
    public boolean resetPassword(String email, String newPassword) {
        UserVo user = userDao.findUserByEmail(email);

        if (user == null) {
            return false;
        }

        // 비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        // DB에 업데이트
        userDao.updatePassword(user);
        return true;
    }
    
    // 비밀번호 업데이트
    @Override
    public void updateUserPassword(UserVo user) {
        try {
            logger.debug("DB에 사용자 비밀번호 업데이트: {}", user.getEmail());
            userDao.updatePassword(user);
        } catch (Exception e) {
            logger.error("비밀번호 업데이트 중 오류 발생", e);
            throw new RuntimeException("비밀번호 업데이트 중 오류 발생", e);
        }
    }

    // 임시 비밀번호 이메일 전송
    @Override
    public void sendTemporaryPasswordEmail(String email, String temporaryPassword) {
        try {
            // 이메일 전송을 위한 메시지 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            
            helper.setTo(email);
            helper.setSubject("임시 비밀번호 안내");
            // 이메일 본문 설정 (HTML 포함)
            String htmlContent = "<p>임시 비밀번호: <strong>" + temporaryPassword + "</strong></p>"
                               + "<p>로그인 후 비밀번호를 변경해주세요.</p>";
            helper.setText(htmlContent, true);  // true는 HTML 형식임을 나타냅니다.

            // 이메일 전송
            mailSender.send(message);
            logger.info("임시 비밀번호 이메일 전송 완료: {}", email);
        } catch (MessagingException e) {
            logger.error("임시 비밀번호 이메일 전송 실패: {}", email, e);
            throw new RuntimeException("임시 비밀번호 이메일 전송에 실패했습니다.", e);
        }
    }
    
    
}
