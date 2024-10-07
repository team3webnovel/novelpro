<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/users/changePassword.css">
    <script src="<%= request.getContextPath() %>/static/js/password-strength.js" defer></script>
    <title>비밀번호 변경</title>

    <!-- 자바스크립트를 이용한 알림 표시 -->
    <script>
        window.onload = function() {
            // JSP 변수를 자바스크립트로 전달
            const message = "<c:out value='${message}'/>";
            const successMessage = "<c:out value='${successMessage}'/>";

            // 경고 메시지 출력
            if (message && message.trim() !== "") {
                alert(message);  // 경고 알람 표시
            }

            // 성공 메시지 출력
            if (successMessage && successMessage.trim() !== "") {
                alert(successMessage);  // 성공 알람 표시
            }
        };
    </script>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</head>
<body>
    <div class="change-password-container">
        <h2>비밀번호 변경</h2>
        <form action="${pageContext.request.contextPath}/change-password" method="post">
            <div class="form-group">
                <label for="current-password">현재 비밀번호:</label>
                <input type="password" id="current-password" name="currentPassword" required>
            </div>

            <div class="form-group">
                <label for="new-password">새 비밀번호:</label>
                <input type="password" id="password" name="newPassword" oninput="checkPasswordStrength(); checkPasswordMatch();" required>
            </div>
            <div id="strength-wrapper">
                <div id="strength-bar"></div>
                <span id="strength-text"></span>
            </div>
            <div class="form-group">
                <label for="confirm-password">새 비밀번호 확인:</label>
                <input type="password" id="confirm-password" name="confirmPassword" oninput="checkPasswordMatch();" required>
            </div>

            <div id="password-match-message"></div> <!-- 비밀번호 일치 여부 메시지 -->
            
            <button type="submit" class="btn btn-primary">비밀번호 변경</button>
        </form>
    </div>
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
