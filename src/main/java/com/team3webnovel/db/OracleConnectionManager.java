package com.team3webnovel.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnectionManager {

    // JDBC URL 및 사용자 정보는 상수로 선언하여 재사용
    private static final String WALLET_PATH = "src/main/resources/wallet";  // 오라클 지갑 경로
    private static final String SERVICE_NAME = "g699a7358c7003f_obdehifxdix5mjdt_high.adb.oraclecloud.com";  // 서비스 이름
    private static final String JDBC_URL = "jdbc:oracle:thin:@tcps://adb.ap-chuncheon-1.oraclecloud.com:1522/" + SERVICE_NAME + "?TNS_ADMIN=" + WALLET_PATH;
    private static final String USERNAME = "Team3_project";
    private static final String PASSWORD = "T3Proj#Secure";

    // 데이터베이스 연결을 반환하는 메서드
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // 오라클 JDBC 드라이버 로드
        Class.forName("oracle.jdbc.OracleDriver");

        // 데이터베이스 연결 반환
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    // 연결 테스트 메서드
    public static void main(String[] args) {
        Connection conn = null;

        try {
            // 데이터베이스 연결
            conn = getConnection();
            System.out.println("DB 연결 성공!");

            // 필요한 로직 추가 (쿼리 실행 등)
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 리소스 정리
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
