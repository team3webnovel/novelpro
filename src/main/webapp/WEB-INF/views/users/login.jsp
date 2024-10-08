<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>




<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 페이지</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/login.css">
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <img src="<%= request.getContextPath() %>/static/images/logo.png" alt="logo" class="logo">
            <h2>당신의 이야기를 꾸며드릴게요!<br>자유 연재 플랫폼 아일란드</h2>
        </div>

        <!-- form 태그에서 action 속성에 /login URL을 명시하여 POST 요청을 전송 -->
        <form id="loginForm" action="<%= request.getContextPath() %>/login" method="post">
            <input type="text" id="username" name="username" placeholder="아이디" required><br>
            <input type="password" id="password" name="password" placeholder="비밀번호" required><br>
            <button type="submit" class="login-btn">로그인</button>
        </form>

        <div class="login-options">
            <a href="#">아이디 찾기</a> |
            <a href="#">비밀번호 재설정</a> |
            <a href="<%= request.getContextPath() %>/register">회원가입</a>
        </div>

        <!-- 로그인 실패 시 서버에서 전달된 메시지를 표시하기 위한 공간 -->
        <c:if test="${not empty message}">
            <div class="error-message" style="color: red;">${message}</div>
        </c:if>
    </div>
</body>
</html>
