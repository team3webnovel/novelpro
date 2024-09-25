package com.team3webnovel.services;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.team3webnovel.mappers.PwMapper;
import com.team3webnovel.vo.PwVo;

import java.util.Base64;

@Service  // PwServiceImpl을 스프링 빈으로 등록
public class PwServiceImpl implements PwService {

    @Autowired
    private PwMapper pwMapper;

    private static final Logger logger = LoggerFactory.getLogger(PwServiceImpl.class);

    private static final String key = "0123456789abcdef";
    private static final String initVector = "0000000000000000";

    public String decryptPassword(String encryptedPassword) {
        try {
            logger.info("암호화된 비밀번호: {}", encryptedPassword);

            byte[] decodedPassword = Base64.getDecoder().decode(encryptedPassword);
            logger.info("Base64 디코딩된 비밀번호: {}", new String(decodedPassword, "UTF-8"));

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(decodedPassword);
            String decryptedPassword = new String(original, "UTF-8");
            logger.info("복호화된 비밀번호: {}", decryptedPassword);

            return decryptedPassword;
        } catch (IllegalArgumentException ex) {
            logger.error("잘못된 Base64 형식의 암호화된 비밀번호입니다.", ex);
            throw new RuntimeException("Base64 디코딩 오류", ex);
        } catch (Exception ex) {
            logger.error("비밀번호 복호화 중 오류 발생", ex);
            throw new RuntimeException("AES 복호화 오류 발생", ex);
        }
    }

    @Override
    public String getGoogleAppPassword() {
        PwVo pwVo = pwMapper.getPasswordByName("google_app_pw");

        logger.info("DB에서 가져온 암호화된 비밀번호: {}", pwVo.getPwPw());
        return decryptPassword(pwVo.getPwPw());
    }
}
