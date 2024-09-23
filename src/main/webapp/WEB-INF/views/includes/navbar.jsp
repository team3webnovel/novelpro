<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav>
    <ul class="navbar">
        <li><a href="<%= request.getContextPath() %>/">홈</a></li>
        <li><a href="<%= request.getContextPath() %>/login">로그인</a></li>
        <li><a href="<%= request.getContextPath() %>/register">회원가입</a></li>
        <li><a href="<%= request.getContextPath() %>/about">소개</a></li>
    </ul>
</nav>
