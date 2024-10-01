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
let selectedTextBoxes = []; // 다중 선택된 텍스트 박스를 저장할 배열

// 텍스트 박스 포커스 관리
let isTextBoxFocused = false;

let isDragging = false;
let dragStartX, dragStartY;
let dragEndX, dragEndY;

// 무작위로 6자리 숫자를 Base64로 인코딩하여 파일명 생성
function generateRandomBase64FileName() {
    const randomNum = Math.floor(100000 + Math.random() * 900000); // 100000 ~ 999999 사이의 숫자
    const base64FileName = btoa(randomNum.toString());
    return base64FileName;
}

// 캔버스 이미지를 서버로 업로드
function uploadImage() {
    var canvas = document.getElementById('canvas');
    
    // 캔버스에 이미지와 텍스트가 이미 그려져 있어야 함
    drawCanvas();  // 텍스트 박스와 이미지가 그려진 캔버스를 준비
    
    // 무작위로 생성된 파일명 생성
    const fileName = generateRandomBase64FileName() + '.png';  // Base64 인코딩된 파일명에 .png 확장자 추가
    
    // Canvas를 Blob으로 변환 (이미지 데이터로 변환)
    canvas.toBlob(function(blob) {
        var formData = new FormData();
        formData.append('image', blob, fileName);  // Base64 파일명을 사용하여 이미지 추가
        
        // 이미지 서버로 업로드 (fetch 사용)
        fetch('http://192.168.0.237:8188/upload/image', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Upload failed');
            }
        })
        .then(data => {
            console.log("Image uploaded successfully:", data);

            // 업로드된 파일명을 백엔드로 전송
            sendFileNameToBackend(fileName);  // fileName 변수를 전송
        })
        .catch(error => {
            console.error("Error uploading image:", error);
            displayStatusMessage('Failed to upload image.', 'error');
        });
    }, 'image/png');  // PNG 포맷으로 변환
}

	
// 파일명을 백엔드로 전송하는 함수
function sendFileNameToBackend(fileName) {
    fetch('http://localhost:8080/team3webnovel/generate-font', {  // 백엔드 경로로 파일명 전송
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'  // JSON 형식으로 데이터 전송
        },
        body: JSON.stringify({ fileName: fileName })  // 파일명을 JSON으로 전송
    })
    .then(response => {
        if (response.ok) {
            console.log('File name sent successfully');
        } else {
            console.error('Failed to send file name');
        }
    })
    .catch(error => {
        console.error('Error sending file name:', error);
    });
}


// 성공 또는 실패 메시지를 표시하는 함수
function displayStatusMessage(message, status) {
    var statusDiv = document.getElementById('uploadStatus');
    statusDiv.innerText = message;
    
    // 상태에 따른 스타일 변경 (성공: 녹색, 실패: 빨간색)
    if (status === 'success') {
        statusDiv.style.color = 'green';
    } else if (status === 'error') {
        statusDiv.style.color = 'red';
    }
    
    // 일정 시간 후 메시지를 사라지게 할 수 있음 (선택 사항)
    setTimeout(() => {
        statusDiv.innerText = '';
    }, 5000);  // 5초 후 메시지 사라짐
}

// 세이브 버튼에 함수 연결
document.getElementById('saveBtn').addEventListener('click', uploadImage);

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


function drawCanvas() {
    // 캔버스 초기화
    ctx.clearRect(0, 0, canvas.width, canvas.height);

	// 이미지가 있는 경우 먼저 이미지 그리기
	if (img) {
	    ctx.drawImage(img, 0, 0, canvas.width, canvas.height); // 업로드된 이미지 그리기
	}
	if (img.src) {
		ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
	}
    // 모든 텍스트 박스를 그리기 위해 textBoxes 배열을 순회
    textBoxes.forEach((textBox, index) => {
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

        const textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 가장 긴 줄의 너비 계산
        const textHeight = textBox.fontSize * 1.2 * lines.length;  // 텍스트 박스의 전체 높이 계산

        // 포커스된 텍스트 박스에 점선 테두리 그리기 (클릭으로 포커스된 경우)
        if (index === focusedTextBoxIndex) {
            ctx.setLineDash([5, 5]);  // 점선 설정 (5px 선, 5px 간격)
            ctx.strokeStyle = '#000';  // 테두리 색상을 검은색으로 설정
            ctx.lineWidth = 1;  // 테두리 두께 설정
            ctx.strokeRect(textBox.x - textWidth / 2 - 5, textBox.y - textHeight / 2 - 5, textWidth + 10, textHeight + 10);
            ctx.setLineDash([]);  // 점선 해제 (다른 도형에 영향을 주지 않도록)
        }

        // 드래그된 범위 내 텍스트 박스에 점선 테두리 그리기
        if (selectedTextBoxes.includes(index)) {
            ctx.setLineDash([5, 5]);  // 점선 설정 (5px 선, 5px 간격)
            ctx.strokeStyle = '#000';  // 테두리 색상을 검은색으로 설정
            ctx.lineWidth = 1;  // 테두리 두께 설정
            ctx.strokeRect(textBox.x - textWidth / 2 - 5, textBox.y - textHeight / 2 - 5, textWidth + 10, textHeight + 10);
            ctx.setLineDash([]);  // 점선 해제 (다른 도형에 영향을 주지 않도록)
        }
    });
}





// 텍스트 박스 외부 클릭 시 포커스 처리 (다중 선택 포함)
canvas.addEventListener('click', function (e) {
	
	// 모달에서 이미지가 선택된 경우에는 포커스 해제 로직을 실행하지 않음
	if (modalImageSelected) {
	    // 플래그를 다시 초기화 (한 번만 체크되도록)
	    modalImageSelected = false;
	    return;
	}
	
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    let clickedOnTextBox = false;  // 클릭한 곳이 텍스트 박스인지 여부를 추적

    // 모든 텍스트 박스에 대해 클릭 여부 확인
    textBoxes.forEach((box, index) => {
        const lines = box.text.split('\n');
        const textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 각 텍스트 박스의 가장 긴 줄의 너비 계산
        const textHeight = box.fontSize * 1.2 * lines.length;  // 각 텍스트 박스의 높이 계산

        // 마우스가 텍스트 박스 안에 있는지 확인
        if (mouseX >= box.x - textWidth / 2 && mouseX <= box.x + textWidth / 2 &&
            mouseY >= box.y - textHeight / 2 && mouseY <= box.y + textHeight / 2) {
            
            clickedOnTextBox = true;  // 텍스트 박스를 클릭했음을 표시

            if (e.ctrlKey) {
                // Ctrl 키가 눌린 상태에서 클릭한 경우, 다중 선택 처리
                if (selectedTextBoxes.includes(index)) {
                    // 이미 선택된 텍스트 박스라면 선택 해제
                    selectedTextBoxes = selectedTextBoxes.filter(i => i !== index);
                } else {
                    // 선택되지 않은 텍스트 박스라면 추가
                    selectedTextBoxes.push(index);
                }
            } else {
                // Ctrl 키가 눌리지 않은 경우, 기존 선택 해제하고 새로운 박스 선택
                selectedTextBoxes = [index];
            }

            // 텍스트 박스가 클릭된 후 포커스 상태를 반영
            focusedTextBoxIndex = index;
            textBox = textBoxes[focusedTextBoxIndex];  // 포커스된 텍스트 박스를 전역 변수로 할당

            drawCanvas();  // 선택 상태 반영 후 캔버스 다시 그림
        }
    });

    // 텍스트 박스 외부 클릭 시 모든 선택 해제
    if (!clickedOnTextBox && !e.ctrlKey) {
        selectedTextBoxes = [];  // 선택 해제
        focusedTextBoxIndex = -1;  // 포커스 해제
        textBox = null;
        drawCanvas();  // 다시 그리기
    }
});




canvas.addEventListener('mousedown', function (e) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    let newFocusedIndex = -1;  // 클릭한 텍스트 박스를 찾기 위한 임시 인덱스
    let clickedOnTextBox = false;  // 텍스트 박스를 클릭했는지 여부

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
            dragging = true;  // 텍스트 박스 드래그 모드 활성화
            canvas.style.cursor = 'grabbing';  // 드래그 중 커서 변경
            clickedOnTextBox = true;  // 텍스트 박스를 클릭했음을 표시
        }
    });

    if (clickedOnTextBox) {
        // 텍스트 박스를 클릭한 경우에만 포커스 변경
        if (newFocusedIndex !== -1) {
            focusedTextBoxIndex = newFocusedIndex;
            textBox = textBoxes[focusedTextBoxIndex];  // 현재 포커스된 텍스트 박스를 전역 변수 textBox에 할당
            drawCanvas();  // 포커스 변경 후 캔버스를 다시 그림
        }
    } else {
        // 텍스트 박스를 클릭하지 않은 경우, 드래그 범위 시작
        const rect = canvas.getBoundingClientRect();
        dragStartX = e.clientX - rect.left;
        dragStartY = e.clientY - rect.top;
        isDragging = true;  // 드래그 모드 활성화

        // 초기 드래그 위치에서 네모난 점선이 그려지기 시작
        dragEndX = dragStartX;
        dragEndY = dragStartY;

        drawCanvas();  // 기존 캔버스 초기화 및 그리기
    }
});



// 마우스 이동 시 드래그 또는 호버 처리
document.addEventListener('mousemove', function (e) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    // 드래그 중일 때는 텍스트 박스 이동 또는 드래그 영역 선택
    if (isDragging) {
        // 캔버스 밖으로 나가도 드래그를 계속함
        dragEndX = Math.max(0, Math.min(mouseX, canvas.width));
        dragEndY = Math.max(0, Math.min(mouseY, canvas.height));
        
        drawCanvas(); // 기존 텍스트 박스를 그리면서 드래그 범위 점선 표시
        ctx.strokeStyle = '#000'; // 드래그 영역 테두리 색상 (검정색)
        ctx.lineWidth = 1;
        ctx.setLineDash([5, 5]); // 점선 스타일 설정
        ctx.strokeRect(
            Math.min(dragStartX, dragEndX),
            Math.min(dragStartY, dragEndY),
            Math.abs(dragStartX - dragEndX),
            Math.abs(dragStartY - dragEndY)
        );
        ctx.setLineDash([]); // 점선 해제
        return;
    }

    // 텍스트 박스 드래그 중이면 텍스트 박스 이동
    if (dragging && focusedTextBoxIndex !== -1) {
        const textBox = textBoxes[focusedTextBoxIndex];  // 포커스된 텍스트 박스 가져오기
        textBox.x = mouseX - offsetX;
        textBox.y = mouseY - offsetY;
        drawCanvas();
        return;
    }
});

// 마우스 버튼을 떼면 드래그 종료 (document에서 감지)
document.addEventListener('mouseup', function () {
    if (dragging) {
        dragging = false;  // 텍스트 박스 드래그 상태를 종료
        canvas.style.cursor = 'default';  // 드래그가 끝나면 기본 커서로 변경
    }

    if (isDragging) {
        isDragging = false;  // 드래그 범위 선택 상태 종료

        // 드래그된 범위 안에 있는 텍스트 박스들 포커스
        selectedTextBoxes = [];  // 선택된 텍스트 박스들을 저장할 배열

        const dragLeft = Math.min(dragStartX, dragEndX);
        const dragRight = Math.max(dragStartX, dragEndX);
        const dragTop = Math.min(dragStartY, dragEndY);
        const dragBottom = Math.max(dragStartY, dragEndY);

        textBoxes.forEach((box, index) => {
            const lines = box.text.split('\n');
            const textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 텍스트 박스의 너비 계산
            const textHeight = box.fontSize * 1.2 * lines.length;  // 텍스트 박스의 높이 계산

            const boxLeft = box.x - textWidth / 2;
            const boxRight = box.x + textWidth / 2;
            const boxTop = box.y - textHeight / 2;
            const boxBottom = box.y + textHeight / 2;

            // 텍스트 박스가 드래그 범위 안에 있는지 확인 (텍스트 박스의 일부라도 범위 안에 있으면 선택)
            if (
                boxRight > dragLeft && 
                boxLeft < dragRight && 
                boxBottom > dragTop && 
                boxTop < dragBottom
            ) {
                selectedTextBoxes.push(index);  // 선택된 텍스트 박스의 인덱스를 저장
            }
        });

        // 선택된 텍스트 박스들 중 첫 번째 텍스트 박스에 포커스 처리
        if (selectedTextBoxes.length > 0) {
            focusedTextBoxIndex = selectedTextBoxes[0];  // 첫 번째 선택된 텍스트 박스에 포커스
            textBox = textBoxes[focusedTextBoxIndex];  // 포커스된 텍스트 박스 설정
        }

        drawCanvas();  // 드래그 후 텍스트 박스에 포커스를 표시하기 위해 다시 그리기
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