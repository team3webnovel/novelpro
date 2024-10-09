<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/users/register.css">
    <script src="<%= request.getContextPath() %>/static/js/password-strength.js" defer></script>
            <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</head>
<body>
    <div class="register-container">
        <div class="register-header">
            <h2>회원가입</h2>
            <p>자유 연재 플랫폼에 오신 것을 환영합니다.</p>
        </div>

        <form id="registerForm" action="<%= request.getContextPath() %>/register" method="post">
            <input type="text" id="username" name="username" placeholder="아이디" required><br>
            <input type="email" id="email" name="email" placeholder="이메일" required><br>
            
            <!-- 이메일 인증 버튼과 인증번호 입력 -->
            <button type="button" id="emailAuthBtn" onclick="sendAuthToken()">이메일 인증</button><br>
            <input type="text" id="emailToken" name="emailToken" placeholder="인증번호 입력" style="display:none;"><br>
            <button type="button" id="verifyTokenBtn" onclick="verifyAuthToken()" style="display:none;">인증번호 확인</button>

            <input type="password" id="password" name="password" placeholder="비밀번호" oninput="checkPasswordStrength()" required><br>
            
            <div id="strength-wrapper">
                <div id="strength-bar"></div>
                <span id="strength-text"></span>
            </div>

            <input type="password" id="confirm-password" name="confirmpassword" placeholder="비밀번호 확인" oninput="checkPasswordMatch()" required><br>
            
            <span id="password-match-message"></span>

            <button type="submit" class="register-btn">회원가입</button>
        </form>

        <div class="login-link">
            이미 계정이 있으신가요? <a href="<%= request.getContextPath() %>/login">로그인</a>
        </div>
    </div>

    <script>
        // 서버에서 전달된 메시지를 받아 알림으로 표시
        window.onload = function() {
            const message = '<%= request.getAttribute("message") %>';
            if (message && message !== 'null') {
                alert(message);  // 메시지를 알림으로 표시
            }
        };

        // 이메일 인증 요청
        function sendAuthToken() {
            const email = document.getElementById('email').value;
            fetch('<%= request.getContextPath() %>/send-email-token', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email: email })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('인증번호가 이메일로 전송되었습니다.');
                    document.getElementById('emailToken').style.display = 'block';
                    document.getElementById('verifyTokenBtn').style.display = 'block';
                    document.getElementById('emailAuthBtn').style.display = 'none';
                } else {
                    alert('이메일 전송에 실패했습니다. 다시 시도해주세요.');
                }
            });
        }

        // 인증번호 확인
        function verifyAuthToken() {
            const token = document.getElementById('emailToken').value;
            fetch('<%= request.getContextPath() %>/verify-email-token', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ token: token })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('이메일 인증이 완료되었습니다.');
                    document.getElementById('verifyTokenBtn').disabled = true;
                    document.getElementById('verifyTokenBtn').textContent = "인증 완료";  // 버튼 텍스트 변경
                    document.getElementById('verifyTokenBtn').style.backgroundColor = "#4CAF50";  // 성공 스타일
                } else {
                    alert('인증번호가 일치하지 않습니다. 다시 확인해주세요.');
                }
            });
        }

        // 비밀번호 일치 여부 확인
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
            <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
