// 캔버스와 컨텍스트 설정
const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d');
let img = new Image();  // 이미지를 전역으로 사용
let textBoxes = [];  // 생성된 텍스트 박스 목록
let selectedTextBox = null;  // 선택된 텍스트 박스
let dragging = false;  // 드래그 상태 추적
let offsetX, offsetY;  // 드래그 위치 오프셋

// 이미지 업로드 핸들러
document.getElementById('imageUpload').addEventListener('change', function (e) {
    const reader = new FileReader();
    reader.onload = function (event) {
        img = new Image();
        img.onload = function () {
            // 캔버스 크기를 이미지에 맞춤
            canvas.width = img.width;
            canvas.height = img.height;
            drawCanvas(); // 이미지 및 텍스트 박스 다시 그리기
        };
        img.src = event.target.result;
    };
    reader.readAsDataURL(e.target.files[0]);
});

// 텍스트 박스 생성 버튼 클릭 시
document.getElementById('addTextBoxBtn').addEventListener('click', function () {
    // 새로운 텍스트 박스 추가
    const newTextBox = {
        text: '여기에 텍스트를 입력하세요',
        x: 150,  // 기본 좌표
        y: 150,
        fontSize: 30,
        font: 'Arial',
        color: '#000000',
        outlineColor: '#FFFFFF',
        bold: false,
        italic: false,
        underline: false,
        strikethrough: false,
        isDragging: false,
    };

    textBoxes.push(newTextBox);
    drawCanvas();  // 캔버스 다시 그리기
});

// 캔버스에 이미지 및 텍스트 박스 그리기
function drawCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);  // 캔버스를 초기화
    if (img) {
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);  // 이미지 다시 그리기
    }

    // 텍스트 박스들 다시 그리기
    textBoxes.forEach(textBox => {
        ctx.font = `${textBox.bold ? 'bold ' : ''}${textBox.italic ? 'italic ' : ''}${textBox.fontSize}px ${textBox.font}`;
        ctx.fillStyle = textBox.color;
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        // 외곽선 그리기
        ctx.strokeStyle = textBox.outlineColor;
        ctx.lineWidth = 2;
        ctx.strokeText(textBox.text, textBox.x, textBox.y);

        // 텍스트 그리기
        ctx.fillText(textBox.text, textBox.x, textBox.y);

        // 추가 스타일 (밑줄, 취소선)
        if (textBox.underline) {
            ctx.beginPath();
            ctx.moveTo(textBox.x - ctx.measureText(textBox.text).width / 2, textBox.y + textBox.fontSize / 4);
            ctx.lineTo(textBox.x + ctx.measureText(textBox.text).width / 2, textBox.y + textBox.fontSize / 4);
            ctx.stroke();
        }
        if (textBox.strikethrough) {
            ctx.beginPath();
            ctx.moveTo(textBox.x - ctx.measureText(textBox.text).width / 2, textBox.y);
            ctx.lineTo(textBox.x + ctx.measureText(textBox.text).width / 2, textBox.y);
            ctx.stroke();
        }
    });
}

// 마우스 드래그로 텍스트 박스 이동
canvas.addEventListener('mousedown', function (e) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    // 텍스트 박스 클릭했는지 확인
    textBoxes.forEach(textBox => {
        const textWidth = ctx.measureText(textBox.text).width;
        const textHeight = textBox.fontSize;

        if (
            mouseX >= textBox.x - textWidth / 2 &&
            mouseX <= textBox.x + textWidth / 2 &&
            mouseY >= textBox.y - textHeight / 2 &&
            mouseY <= textBox.y + textHeight / 2
        ) {
            selectedTextBox = textBox;
            offsetX = mouseX - textBox.x;
            offsetY = mouseY - textBox.y;
            dragging = true;
        }
    });
});

canvas.addEventListener('mousemove', function (e) {
    if (dragging && selectedTextBox) {
        const rect = canvas.getBoundingClientRect();
        selectedTextBox.x = e.clientX - rect.left - offsetX;
        selectedTextBox.y = e.clientY - rect.top - offsetY;
        drawCanvas();  // 드래그 중 텍스트 박스 위치 업데이트
    }
});

canvas.addEventListener('mouseup', function () {
    dragging = false;
});

// 텍스트 박스를 더블클릭하여 텍스트 수정
canvas.addEventListener('dblclick', function (e) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    textBoxes.forEach(textBox => {
        const textWidth = ctx.measureText(textBox.text).width;
        const textHeight = textBox.fontSize;

        if (
            mouseX >= textBox.x - textWidth / 2 &&
            mouseX <= textBox.x + textWidth / 2 &&
            mouseY >= textBox.y - textHeight / 2 &&
            mouseY <= textBox.y + textHeight / 2
        ) {
            // 텍스트 수정
            let input = document.createElement('input');
            input.type = 'text';
            input.value = textBox.text;
            input.style.position = 'absolute';
            input.style.left = `${textBox.x - textWidth / 2}px`;
            input.style.top = `${textBox.y - textHeight}px`;

            document.body.appendChild(input);
            input.focus();

            input.addEventListener('blur', function () {
                textBox.text = input.value;
                document.body.removeChild(input);
                drawCanvas();
            });

            input.addEventListener('keydown', function (e) {
                if (e.key === 'Enter') {
                    input.blur();
                }
            });
        }
    });
});

// Delete 키로 텍스트 박스 삭제
document.addEventListener('keydown', function (e) {
    if (e.key === 'Delete' && selectedTextBox) {
        const index = textBoxes.indexOf(selectedTextBox);
        if (index !== -1) {
            textBoxes.splice(index, 1);
            selectedTextBox = null;
            drawCanvas();
        }
    }
});
