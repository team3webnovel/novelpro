 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디 찾기</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/users/findId.css">
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</head>
<body>
    <div class="find-id-container">
        <h2>아이디 찾기</h2>
        <form action="<%= request.getContextPath() %>/find-id" method="post">
            <input type="email" name="email" placeholder="이메일을 입력하세요" required><br>
            <button type="submit" class="find-id-btn">아이디 찾기</button>
        </form>

        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <a href="<%= request.getContextPath() %>/login">로그인 페이지로 돌아가기</a>
    </div>
        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
