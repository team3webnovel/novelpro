// 텍스트 스타일 변경 시 포커스 유지
const maintainFocus = (elementId) => {
    const element = document.getElementById(elementId);
    element.addEventListener('mousedown', function (e) {
        e.stopPropagation();  // 이벤트 전파 중지
    });
};

// 그라데이션 필드 활성화/비활성화
document.getElementById('gradientInput').addEventListener('change', function (e) {
    e.stopPropagation();
    const gradientStartInput = document.getElementById('gradientStartInput');
    const gradientEndInput = document.getElementById('gradientEndInput');
    const gradientDirection = document.getElementById('gradientDirection');  // 그라데이션 방향 선택 필드

    if (textBox) {
        textBox.useGradient = this.checked;
        drawCanvas();
    }

    // 그라데이션 사용 여부에 따라 색상 및 방향 입력 필드 활성화/비활성화
    gradientStartInput.disabled = !this.checked;
    gradientEndInput.disabled = !this.checked;
    gradientDirection.disabled = !this.checked;
});
maintainFocus('gradientInput');

// 그라데이션 시작 색상 변경
document.getElementById('gradientStartInput').addEventListener('input', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.gradientStart = this.value;
        drawCanvas();
    }
});
maintainFocus('gradientStartInput');

// 그라데이션 끝 색상 변경
document.getElementById('gradientEndInput').addEventListener('input', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.gradientEnd = this.value;
        drawCanvas();
    }
});
maintainFocus('gradientEndInput');

// 그라데이션 방향 변경
document.getElementById('gradientDirection').addEventListener('change', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.gradientDirection = this.value;  // 선택한 방향 (가로 또는 세로)
        drawCanvas();
    }
});
maintainFocus('gradientDirection');

// 폰트, 색상, 크기 등의 변화를 적용하는 이벤트 리스너 추가
document.getElementById('fontSelect').addEventListener('change', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.font = this.value;
        drawCanvas();
    }
});
maintainFocus('fontSelect');

document.getElementById('fontSizeSlider').addEventListener('input', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.fontSize = parseInt(this.value, 10);
        drawCanvas();
    }
});
maintainFocus('fontSizeSlider');

document.getElementById('textColor').addEventListener('input', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.color = this.value;
        drawCanvas();
    }
});
maintainFocus('textColor');

document.getElementById('outlineColorInput').addEventListener('input', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.outlineColor = this.value;
        drawCanvas();
    }
});
maintainFocus('outlineColorInput');

document.getElementById('boldBtn').addEventListener('click', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.bold = !textBox.bold;
        drawCanvas();
    }
});
maintainFocus('boldBtn');

document.getElementById('italicBtn').addEventListener('click', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.italic = !textBox.italic;
        drawCanvas();
    }
});
maintainFocus('italicBtn');

document.getElementById('underlineBtn').addEventListener('click', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.underline = !textBox.underline;
        drawCanvas();
    }
});
maintainFocus('underlineBtn');

document.getElementById('strikethroughBtn').addEventListener('click', function (e) {
    e.stopPropagation();
    if (textBox) {
        textBox.strikethrough = !textBox.strikethrough;
        drawCanvas();
    }
});
maintainFocus('strikethroughBtn');
