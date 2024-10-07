<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 재설정</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/users/resetPassword.css">
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</head>
<body>
    <div class="reset-password-container">
        <h2>비밀번호 재설정</h2>
        <form action="${pageContext.request.contextPath}/reset-password" method="post">
            <div class="form-group">
                <label for="email">이메일:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <button type="submit" class="btn btn-primary">임시 비밀번호 전송</button>
        </form>

        <c:if test="${not empty message}">
            <div class="alert alert-danger">${message}</div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
    </div>
</body>
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</html>
