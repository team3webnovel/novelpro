package com.team3webnovel.test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageTextExample {
    public static void main(String[] args) {
        try {
            // 이미지 로드
            BufferedImage image = ImageIO.read(new File("your_image.jpg"));

            // 그래픽 객체 생성
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            g2d.setColor(Color.WHITE);

            // 텍스트 삽입
            g2d.drawString("Hello, World!", 50, 50);

            // 수정된 이미지 저장
            ImageIO.write(image, "jpg", new File("output_image.jpg"));

            g2d.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
