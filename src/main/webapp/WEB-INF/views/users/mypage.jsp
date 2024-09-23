<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/index.css">
<!-- 헤더 포함 -->
<%@ include file="/WEB-INF/views/includes/header.jsp" %>

<div class="container mypage">
    <h2>마이페이지</h2>
    
    <div class="user-info">
        <p><strong>아이디:</strong> ${sessionScope.user.username}</p>
        <p><strong>이메일:</strong> ${sessionScope.user.email}</p>
        <p><strong>가입일:</strong> ${sessionScope.user.createdAt}</p>
    </div>

    <div class="mypage-actions">
        <a href="<%= request.getContextPath() %>/edit-profile" class="btn-primary">프로필 수정</a>
        <a href="<%= request.getContextPath() %>/logout" class="btn-secondary">로그아웃</a>
    </div>
</div>

<!-- 푸터 포함 -->
<%@ include file="/WEB-INF/views/includes/footer.jsp" %>
