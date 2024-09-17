function checkPasswordStrength() {
    var strengthBar = document.getElementById("strength-bar");
    var strengthText = document.getElementById("strength-text");
    var password = document.getElementById("password").value;
    var strength = 0;

    // 비밀번호 강도 계산
    if (password.length >= 8) strength++;
    if (password.match(/[a-z]+/)) strength++;
    if (password.match(/[A-Z]+/)) strength++;
    if (password.match(/[0-9]+/)) strength++;
    if (password.match(/[$@#&!]+/)) strength++;

    // 강도에 따른 스타일 변경
    switch (strength) {
        case 1:
            strengthBar.style.width = "20%";
            strengthBar.style.backgroundColor = "red";
            strengthText.textContent = "매우 약함";
            break;
        case 2:
            strengthBar.style.width = "40%";
            strengthBar.style.backgroundColor = "orange";
            strengthText.textContent = "약함";
            break;
        case 3:
            strengthBar.style.width = "60%";
            strengthBar.style.backgroundColor = "yellow";
            strengthText.textContent = "보통";
            break;
        case 4:
            strengthBar.style.width = "80%";
            strengthBar.style.backgroundColor = "blue";
            strengthText.textContent = "강함";
            break;
        case 5:
            strengthBar.style.width = "100%";
            strengthBar.style.backgroundColor = "green";
            strengthText.textContent = "매우 강함";
            break;
        default:
            strengthBar.style.width = "0";
            strengthText.textContent = "";
            break;
    }
}

function checkPasswordMatch() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm-password").value;
    var message = document.getElementById("password-match-message");

    if (confirmPassword === "") {
        message.textContent = "";
    } else if (password === confirmPassword) {
        message.textContent = "비밀번호가 일치합니다.";
        message.style.color = "green";
    } else {
        message.textContent = "비밀번호가 일치하지 않습니다.";
        message.style.color = "red";
    }
}
