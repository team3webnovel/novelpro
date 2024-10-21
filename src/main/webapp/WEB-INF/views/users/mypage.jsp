<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/users/mypage.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <!-- 헤더 포함 -->
    <header>
        <%@ include file="/WEB-INF/views/includes/header.jsp" %>
    </header>

    <!-- 본문 내용 -->
	<div class="container mypage">
	    <h2 style="color: #003366;">마이페이지</h2>
	
	    <div class="user-info">
	        <p><strong style="color: #003366;">아이디:</strong> ${sessionScope.user.username}</p>
	        <p><strong style="color: #003366;">이메일:</strong> ${sessionScope.user.email}</p>
	        <p><strong style="color: #003366;">가입일:</strong> ${sessionScope.user.createdAt}</p>
	    </div>
	
	    <div class="mypage-actions">
	        <a href="<%= request.getContextPath() %>/change-password" 
	           class="btn btn-primary" 
	           style="background-color: #003366; border-color: #003366; color: white;">
	            비밀번호 변경
	        </a>
	            <a href="<%= request.getContextPath() %>/logout" class="btn btn-secondary">로그아웃</a>
        </div>
    </div>

</body>
</html>
