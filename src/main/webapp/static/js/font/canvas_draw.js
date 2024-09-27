// * canvas_draw.js

// 캔버스와 컨텍스트 설정
const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d');
let img = new Image();  // 이미지를 전역으로 사용
let textBoxes = []; // 모든 텍스트 박스를 관리하는 배열
let textBox = null;  // 현재 포커스된 텍스트 박스만 관리
let focusedTextBoxIndex = -1; // 포커스된 텍스트 박스의 인덱스
let dragging = false;  // 드래그 상태 추적
let offsetX, offsetY;  // 드래그 위치 오프셋
let isOnTextBox = false;  // 텍스트 박스 내부에 마우스가 있는지 여부
// 텍스트 박스 포커스 관리
let isTextBoxFocused = false;

// 이미지 업로드 핸들러
document.getElementById('imageUpload').addEventListener('change', function () {
    const reader = new FileReader();
    reader.onload = function (event) {
        img.onload = function () {
            canvas.width = img.width;
            canvas.height = img.height;
            drawCanvas();
        };
        img.src = event.target.result;
    };
    reader.readAsDataURL(this.files[0]);
});

// 텍스트 박스 생성 버튼 클릭 시 새로운 텍스트 박스 추가
document.getElementById('addTextBoxBtn').addEventListener('click', function () {
    const newTextBox = {
        text: '여기에 텍스트를 입력하세요',
        x: 150,
        y: 150,
        fontSize: 30,
        font: 'Arial',
        color: '#000000',
        outlineColor: '#FFFFFF',
        bold: false,
        italic: false,
        underline: false,
        strikethrough: false,
        useGradient: false,
        gradientStart: '#ff0000',
        gradientEnd: '#0000ff',
    };
    textBoxes.push(newTextBox);
    focusedTextBoxIndex = textBoxes.length - 1;  // 새로 생성된 텍스트 박스를 포커스
    textBox = textBoxes[focusedTextBoxIndex];  // 전역 변수에 포커스된 텍스트 박스 할당
    drawCanvas();  // 캔버스 업데이트
});


// 텍스트 박스를 캔버스에 그리기
function drawCanvas() {
    // 캔버스 초기화
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    if (img) {
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height); // 업로드된 이미지 그리기
    }

    // 모든 텍스트 박스를 그리기 위해 textBoxes 배열을 순회
    textBoxes.forEach((textBox) => {
        // 폰트 설정
        ctx.font = `${textBox.bold ? 'bold ' : ''}${textBox.italic ? 'italic ' : ''}${textBox.fontSize}px ${textBox.font}`;
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        // 텍스트를 여러 줄로 나누기
        const lines = textBox.text.split('\n');
        const lineHeight = textBox.fontSize * 1.2;

        // 각 줄마다 그리기
        lines.forEach((line, i) => {
            const y = textBox.y + i * lineHeight - (lines.length - 1) * lineHeight / 2;

            // 그라데이션 적용 여부 확인
            if (textBox.useGradient) {
                const gradient = ctx.createLinearGradient(0, 0, canvas.width, 0); // 가로 방향 그라데이션
                gradient.addColorStop(0, textBox.gradientStart);  // 그라데이션 시작 색상
                gradient.addColorStop(1, textBox.gradientEnd);    // 그라데이션 끝 색상
                ctx.fillStyle = gradient;  // 그라데이션을 fillStyle로 설정
            } else {
                ctx.fillStyle = textBox.color;  // 그라데이션이 없을 경우 일반 색상 적용
            }

            // 글자 외곽선 그리기 (외곽선이 있는 경우)
            if (textBox.outlineColor) {
                ctx.strokeStyle = textBox.outlineColor;  // 외곽선 색상 설정
                ctx.lineWidth = 2;  // 외곽선 두께 설정
                ctx.strokeText(line, textBox.x, y);  // 외곽선 그리기
            }

            // 텍스트 그리기 (채우기)
            ctx.fillText(line, textBox.x, y);

            // 밑줄 추가 (밑줄은 항상 검은색으로 설정)
            if (textBox.underline) {
                ctx.strokeStyle = '#000';  // 밑줄은 검은색으로 고정
                ctx.lineWidth = 1;
                ctx.beginPath();
                ctx.moveTo(textBox.x - ctx.measureText(line).width / 2, y + textBox.fontSize / 4);
                ctx.lineTo(textBox.x + ctx.measureText(line).width / 2, y + textBox.fontSize / 4);
                ctx.stroke();
            }

            // 취소선 추가 (항상 검은색)
            if (textBox.strikethrough) {
                ctx.strokeStyle = '#000';  // 취소선은 검은색으로 고정
                ctx.lineWidth = 1;
                ctx.beginPath();
                ctx.moveTo(textBox.x - ctx.measureText(line).width / 2, y);
                ctx.lineTo(textBox.x + ctx.measureText(line).width / 2, y);
                ctx.stroke();
            }
        });
    });
}







// 텍스트 박스 외부 클릭 시 포커스 해제
canvas.addEventListener('click', function (e) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    let clickedOnTextBox = false;  // 클릭한 곳이 텍스트 박스인지 여부를 추적

    // 텍스트 박스를 클릭한 경우, 현재 포커스된 텍스트 박스가 있으면 그 내용을 저장
    if (focusedTextBoxIndex !== -1 && textBox) {
        // 수정된 텍스트 박스 내용을 배열에 반영
        textBoxes[focusedTextBoxIndex] = textBox;
    }

    // 모든 텍스트 박스에 대해 클릭 여부 확인
    textBoxes.forEach((box, index) => {
        const lines = box.text.split('\n');
        const textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 각 텍스트 박스의 가장 긴 줄의 너비 계산
        const textHeight = box.fontSize * 1.2 * lines.length;  // 각 텍스트 박스의 높이 계산

        // 마우스가 텍스트 박스 안에 있는지 확인
        if (mouseX >= box.x - textWidth / 2 && mouseX <= box.x + textWidth / 2 &&
            mouseY >= box.y - textHeight / 2 && mouseY <= box.y + textHeight / 2) {
            // 텍스트 박스를 클릭한 경우
            focusedTextBoxIndex = index;  // 해당 텍스트 박스로 포커스 변경
            textBox = textBoxes[focusedTextBoxIndex];  // 전역 변수에 현재 포커스된 텍스트 박스 설정
            clickedOnTextBox = true;
            drawCanvas();  // 포커스된 상태로 캔버스를 다시 그림
        }
    });

    // 마우스가 캔버스 내부에 있는지 확인 (캔버스 외부 클릭 시 포커스 해제하지 않음)
    if (mouseX >= 0 && mouseX <= canvas.width && mouseY >= 0 && mouseY <= canvas.height) {
        // 텍스트 박스 외부를 클릭한 경우 포커스 해제
        if (!clickedOnTextBox) {
            if (focusedTextBoxIndex !== -1 && textBox) {
                // 포커스된 텍스트 박스의 내용을 저장
                textBoxes[focusedTextBoxIndex] = textBox;
            }

            focusedTextBoxIndex = -1;  // 포커스 해제
            textBox = null;  // 전역 변수를 null로 설정하여 포커스 해제 상태 반영
            drawCanvas();  // 포커스 해제된 상태로 캔버스를 다시 그림
        }
    }
});



// 마우스 이벤트로 드래그
canvas.addEventListener('mousedown', function (e) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    let newFocusedIndex = -1;  // 클릭한 텍스트 박스를 찾기 위한 임시 인덱스

    // 모든 텍스트 박스에 대해 클릭 여부 확인
    textBoxes.forEach((box, index) => {
        const lines = box.text.split('\n');
        const textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 각 텍스트 박스의 가장 긴 줄의 너비 계산
        const textHeight = box.fontSize * 1.2 * lines.length;  // 각 텍스트 박스의 높이 계산

        // 마우스가 텍스트 박스 안에 있는지 확인
        if (mouseX >= box.x - textWidth / 2 && mouseX <= box.x + textWidth / 2 &&
            mouseY >= box.y - textHeight / 2 && mouseY <= box.y + textHeight / 2) {
            newFocusedIndex = index;  // 클릭한 텍스트 박스 인덱스 설정
            offsetX = mouseX - box.x;
            offsetY = mouseY - box.y;
            dragging = true;
            canvas.style.cursor = 'grabbing';  // 드래그 중 커서 변경
        }
    });

    // 클릭된 텍스트 박스가 있으면 포커스 변경
    if (newFocusedIndex !== -1) {
        focusedTextBoxIndex = newFocusedIndex;
        textBox = textBoxes[focusedTextBoxIndex];  // 현재 포커스된 텍스트 박스를 전역 변수 textBox에 할당
        drawCanvas();  // 포커스 변경 후 캔버스를 다시 그림
    }
});


// 마우스 이동 시 드래그 또는 호버 처리
canvas.addEventListener('mousemove', function (e) {
    if (focusedTextBoxIndex === -1) return;  // 포커스된 텍스트 박스가 없으면 리턴

    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    const textBox = textBoxes[focusedTextBoxIndex];  // 포커스된 텍스트 박스 가져오기
    const lines = textBox.text.split('\n');
    const textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 가장 긴 줄의 너비 계산
    const textHeight = textBox.fontSize * 1.2 * lines.length;  // 전체 텍스트 박스의 높이 계산 (줄 수 반영)

    // 드래그 중이면 텍스트 박스 이동
    if (dragging) {
        textBox.x = mouseX - offsetX;
        textBox.y = mouseY - offsetY;
        drawCanvas();
    }

    // 텍스트 박스 위에 있을 때 커서 변경
    let cursorChanged = false;
    textBoxes.forEach((box, index) => {
        const boxLines = box.text.split('\n');
        const boxTextWidth = Math.max(...boxLines.map(line => ctx.measureText(line).width));  // 각 텍스트 박스의 가장 긴 줄의 너비 계산
        const boxTextHeight = box.fontSize * 1.2 * boxLines.length;  // 각 텍스트 박스의 높이 계산

        if (mouseX >= box.x - boxTextWidth / 2 && mouseX <= box.x + boxTextWidth / 2 &&
            mouseY >= box.y - boxTextHeight / 2 && mouseY <= box.y + boxTextHeight / 2) {
            canvas.style.cursor = 'pointer';  // 텍스트 박스 위일 때 포인터 커서
            isOnTextBox = true;
            cursorChanged = true;
        }
    });

    if (!cursorChanged) {
        canvas.style.cursor = 'default';  // 기본 커서
        isOnTextBox = false;
    }
});


// 마우스 버튼을 떼면 드래그 종료
canvas.addEventListener('mouseup', function () {
    if (dragging) {
        dragging = false;  // 드래그 상태를 종료
        canvas.style.cursor = 'default';  // 드래그가 끝나면 기본 커서로 변경
    }
});

// 캔버스를 벗어나면 드래그 중지
canvas.addEventListener('mouseleave', function () {
    if (dragging) {
        dragging = false;  // 드래그 상태를 종료
        canvas.style.cursor = 'default';  // 커서를 기본 상태로 변경
    }
});


// 텍스트 수정용 DOM 요소 추가
canvas.addEventListener('dblclick', function (e) {
    if (!textBox) return;  // 텍스트 박스가 없는 경우 아무것도 하지 않음
    
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    const lines = textBox.text.split('\n');
    const textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 가장 긴 줄의 너비 계산
    const textHeight = textBox.fontSize * 1.2 * lines.length;  // 전체 텍스트 박스의 높이 계산 (줄 수 반영)

    // 더블 클릭한 위치가 텍스트 박스 안쪽일 때
    if (mouseX >= textBox.x - textWidth / 2 && mouseX <= textBox.x + textWidth / 2 &&
        mouseY >= textBox.y - textHeight / 2 && mouseY <= textBox.y + textHeight / 2) {
        
        // 기존 텍스트 박스를 숨기고, DOM 요소로 텍스트 편집 가능하게 만듦
        const textarea = document.createElement('textarea');
        textarea.value = textBox.text;
        textarea.style.position = 'absolute';
        textarea.style.left = `${rect.left + textBox.x - textWidth / 2}px`;  // 입력 상자를 텍스트 위치로 이동
        textarea.style.top = `${rect.top + textBox.y - textHeight / 2}px`;
        textarea.style.width = `${textWidth + 10}px`;  // 너비는 텍스트 박스의 너비에 맞춤
        textarea.style.height = `${textHeight + 10}px`;  // 높이는 텍스트 박스의 높이에 맞춤
        textarea.style.fontSize = `${textBox.fontSize}px`;
        textarea.style.fontFamily = textBox.font;
        textarea.style.border = '1px solid #000';  // 테두리 추가
        textarea.style.padding = '5px';
        textarea.style.zIndex = 1000;  // 캔버스 위로 오도록 설정
        textarea.style.lineHeight = '1.2';  // 줄 간격 설정
        
        document.body.appendChild(textarea);
        textarea.focus();  // 자동으로 포커스를 입력 상자에 설정

        // 포커스를 잃으면 텍스트 업데이트
        textarea.addEventListener('blur', function () {
            updateTextBox(textarea.value);  // 입력된 값을 텍스트 박스로 반영
            document.body.removeChild(textarea);  // 입력 상자 제거
        });
    }
});

// 텍스트 박스 업데이트 함수
function updateTextBox(newText) {
    if (textBox) {
        textBox.text = newText.replace(/\r?\n/g, '\n');  // 줄바꿈 적용
        drawCanvas();  // 캔버스 다시 그리기
    }
}
