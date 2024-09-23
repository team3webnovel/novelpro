<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/register.css">
    <script src="<%= request.getContextPath() %>/static/js/password-strength.js" defer></script>
</head>
<body>
    <div class="register-container">
        <div class="register-header">
            <h2>회원가입</h2>
            <p>자유 연재 플랫폼에 오신 것을 환영합니다.</p>
        </div>

        <!-- 폼에서 데이터를 서버로 전송할 때 기본 POST 사용 -->
        <form id="registerForm" action="<%= request.getContextPath() %>/register" method="post">
            <input type="text" id="username" name="username" placeholder="아이디" required><br>
            <input type="email" id="email" name="email" placeholder="이메일" required><br>
            <input type="password" id="password" name="password" placeholder="비밀번호" oninput="checkPasswordStrength()" required><br>

            <!-- 비밀번호 강도 표시 영역 -->
            <div id="strength-wrapper">
                <div id="strength-bar"></div>
                <span id="strength-text"></span>
            </div>

            <input type="password" id="confirm-password" name="confirmpassword" placeholder="비밀번호 확인" oninput="checkPasswordMatch()" required><br>

            <!-- 비밀번호 일치 여부 표시 -->
            <span id="password-match-message"></span>

            <button type="submit" class="register-btn">회원가입</button>
        </form>

        <div class="login-link">
            이미 계정이 있으신가요? <a href="<%= request.getContextPath() %>/login">로그인</a>
        </div>
    </div>

    <script>
        // 비밀번호 일치 여부 확인 (이 부분은 유지)
        function checkPasswordMatch() {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirm-password').value;
            const message = document.getElementById('password-match-message');

            if (password !== confirmPassword) {
                message.textContent = "비밀번호가 일치하지 않습니다.";
                message.style.color = "red";
            } else {
                message.textContent = "비밀번호가 일치합니다.";
                message.style.color = "green";
            }
        }
    </script>
</body>
</html>
