package com.team3webnovel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import com.team3webnovel.services.PwServiceImpl;

@Configuration
public class MailConfig {

    @Autowired
    private PwServiceImpl pwService;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("ktw1398@gmail.com");

        // PwServiceImpl을 통해 DB에서 가져온 디코딩된 비밀번호 설정
        String password = pwService.getGoogleAppPassword();  // 파라미터로 "google_app_pw" 전달
        mailSender.setPassword(password);

        // 추가적인 프로퍼티 설정
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
