package com.team3webnovel.mappers;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESTest {

    public static void main(String[] args) {
        try {
            // 테스트할 평문 텍스트
            String plainText = "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";

            // 32바이트 키와 16바이트 IV 설정
            String key = "01234567890123456789012345678901";  // 32바이트 AES 키
            String iv = "0123456789012345";  // 16바이트 IV

            // 암호화 테스트
            String encryptedText = encryptAES(plainText, key, iv);
            System.out.println("Encrypted Text: " + encryptedText);

            // 복호화 테스트
            String decryptedText = decryptAES(encryptedText, key, iv);
            System.out.println("Decrypted Text: " + decryptedText);

            // 복호화된 텍스트가 원본과 일치하는지 확인
            if (plainText.equals(decryptedText)) {
                System.out.println("Test passed: Decrypted text matches the original plain text.");
            } else {
                System.out.println("Test failed: Decrypted text does not match the original plain text.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // AES 암호화 메서드
    public static String encryptAES(String plainText, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // AES 복호화 메서드
    public static String decryptAES(String encryptedText, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, "UTF-8");
    }
}
