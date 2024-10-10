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

let gif;  // 전역으로 gif 객체를 선언하여 renderGIF 함수에서도 접근 가능하게 함
let gifCanvas;  // 전역으로 gifCanvas를 선언하여 renderGIF에서 사용

// 텍스트 박스 포커스 관리
let isTextBoxFocused = false;

let isDragging = false;
let dragStartX, dragStartY;
let dragEndX, dragEndY;

const fontSelect = document.getElementById('fontSelect');



// 선택한 폰트를 select 요소에 직접 적용하는 함수
fontSelect.addEventListener('change', function() {
    const selectedFont = fontSelect.value;
    fontSelect.style.fontFamily = selectedFont;  // select 박스에도 선택한 폰트를 적용
});

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
                throw new Error('이미지 저장에 실패헀습니다.');
            }
        })
        .then(data => {
				console.log("Image uploaded successfully:", data);

				// 업로드된 파일명을 백엔드로 전송
				sendFileNameToBackend(fileName);  // fileName 변수를 전송
			    // 성공 상태 메시지 출력
			    displayStatusMessage('이미지가 성공적으로 저장되었습니다.', 'success');

			    // 업로드 후 이미지 미리보기 추가
			    const imagePreview = document.createElement('img');
			    imagePreview.src = canvas.toDataURL();  // 업로드된 캔버스를 미리보기로 표시
			    document.getElementById('uploadStatus').appendChild(imagePreview);
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


// 성공 또는 실패 메시지를 표시하는 함수 (커스텀 알람 사용)
function displayStatusMessage(message, status) {
    var statusDiv = document.getElementById('uploadStatus');
    statusDiv.innerText = message;

    // 상태에 따른 클래스 추가
    if (status === 'success') {
        statusDiv.className = 'custom-alert success show';
    } else if (status === 'error') {
        statusDiv.className = 'custom-alert error show';
    }

    // 알람을 표시하고, 일정 시간 후 사라지게 설정
    statusDiv.style.display = 'block';  // 알람 표시
    setTimeout(() => {
        statusDiv.classList.remove('show');  // 알람 사라지기 시작
        setTimeout(() => {
            statusDiv.style.display = 'none';  // 완전히 숨김
        }, 300);  // 사라지는 애니메이션 대기 시간
    }, 3000);  // 3초 후 알람이 사라짐
}



// 세이브 버튼에 함수 연결
document.getElementById('saveBtn').addEventListener('click', uploadImage);

document.getElementById('imageUpload').addEventListener('change', function () {
    const file = this.files[0];
    const fileType = file.type;

    if (!file) {
        console.error("No file selected.");
        return;
    }

    if (fileType === "image/gif") {
        const reader = new FileReader();
        reader.onload = function(event) {
            const imgElement = document.getElementById('canvas');
			
            if (!imgElement) {
                console.error("Canvas element not found!");
                return;
            }

            // GIF 파일 URL 설정
            imgElement.src = event.target.result;

            // SuperGif 사용하여 GIF 처리
            gif = new SuperGif({ gif: imgElement });
            gif.load(function() {
                console.log('GIF loaded successfully!');

                // gifCanvas가 유효한지 확인 (안전하게 확인)
                gifCanvas = gif.get_canvas();
                if (!gifCanvas) {
                    console.error("gifCanvas is null");
                    return;
                }

                // 캔버스 크기 변경
                canvas.width = gifCanvas.width;
                canvas.height = gifCanvas.height;

                // GIF를 캔버스에 그리기
                renderGIF(ctx);  // GIF 렌더링 함수 호출
            });
        };
        reader.readAsDataURL(file);  // 파일을 Data URL로 읽어들이기
    } else {
        // PNG, JPEG 등의 일반 이미지 파일 처리
        const reader = new FileReader();
        reader.onload = function (event) {
            img.onload = function () {
                canvas.width = img.width;
                canvas.height = img.height;
                drawCanvas();  // 텍스트 박스 및 추가 요소 그리기
            };
            img.src = event.target.result;
        };
        reader.readAsDataURL(file);  // 파일을 Data URL로 읽어들이기
    }
});

function renderGIF(ctx) {
    // 다음 GIF 프레임으로 이동
    gif.move_relative(1);

    // 캔버스를 초기화하고 GIF 프레임을 그리기
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.drawImage(gif.get_canvas(), 0, 0, canvas.width, canvas.height);

    // 텍스트 박스를 GIF 위에 그리기
    drawTextBoxes(ctx);

    // GIF의 프레임 딜레이를 가져오기
    const frameInfo = gif.get_current_frame(); // 현재 프레임 정보를 가져옴
    const frameDelay = frameInfo.delay || 100; // delay 값이 없으면 기본값 100ms

    // 프레임 속도를 기반으로 GIF 재생
    setTimeout(() => renderGIF(ctx), frameDelay);
}

function drawTextBoxes(ctx) {
    textBoxes.forEach((textBox) => {
        // 폰트 설정
        ctx.font = `${textBox.bold ? 'bold ' : ''}${textBox.italic ? 'italic ' : ''}${textBox.fontSize}px ${textBox.font}`;
        ctx.fillStyle = textBox.color;
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        // 여러 줄 텍스트 처리 (줄바꿈 처리)
        const lines = textBox.text.split('\n');
        const lineHeight = textBox.fontSize * 1.2;  // 텍스트 줄 간격 설정

        lines.forEach((line, i) => {
            const yPosition = textBox.y + i * lineHeight - (lines.length - 1) * lineHeight / 2;
            ctx.fillText(line, textBox.x, yPosition);  // 각 줄을 캔버스에 그리기
        });

        // 선택된 텍스트 박스에 테두리 표시
        if (focusedTextBoxIndex === textBoxes.indexOf(textBox)) {
            ctx.setLineDash([5, 5]);  // 점선 테두리 설정
            ctx.strokeStyle = '#000';  // 테두리 색상
            ctx.strokeRect(
                textBox.x - 50,
                textBox.y - 10 - (lines.length - 1) * lineHeight / 2,  // 박스 높이를 줄 수에 맞춰 설정
                100,
                20 + (lines.length - 1) * lineHeight  // 줄 수에 따른 높이 조정
            );
            ctx.setLineDash([]);  // 점선 해제
        }
    });
}


// 텍스트 박스 생성 버튼 클릭 시 새로운 텍스트 박스 추가
document.getElementById('addTextBoxBtn').addEventListener('click', function () {
    const newTextBox = {
		text: '여기에 텍스트를 입력하세요',
		x: 150,
		y: 150,
		fontSize: 30,
		font: 'Arial',
		color: '#000000',
		orientation: 'horizontal',  // 가로 텍스트
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
// 텍스트 박스 생성 버튼 클릭 시 새로운 텍스트 박스 추가
document.getElementById('addVerticalTextBoxBtn').addEventListener('click', function () {
    const newTextBox = {
		text: '여기에 텍스트를 입력하세요',
		x: 150,
		y: 150,
		fontSize: 30,
		font: 'Arial',
		color: '#000000',
		orientation: 'vertical',  // 세로 텍스트
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
    if (img && img.src) {
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
    }

    // 모든 텍스트 박스를 순회하면서 그리기
    textBoxes.forEach((textBox, index) => {
        // 폰트 설정
        ctx.font = `${textBox.bold ? 'bold ' : ''}${textBox.italic ? 'italic ' : ''}${textBox.fontSize}px ${textBox.font}`;
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        let textWidth, textHeight;

        if (textBox.orientation === 'horizontal' || !textBox.orientation) {
            // 가로 텍스트 크기 계산
            const lines = textBox.text.split('\n');
            const lineHeight = textBox.fontSize * 1.2;
            textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));
            textHeight = lineHeight * lines.length;
        } else if (textBox.orientation === 'vertical') {
            // 세로 텍스트 크기 계산
            const characters = textBox.text.split('');
            textWidth = textBox.fontSize; // 세로 텍스트의 너비는 글자 크기로 설정
            textHeight = characters.length * textBox.fontSize; // 총 높이
        }

        // 그라데이션 적용 여부 확인
        if (textBox.useGradient) {
            let gradient;
            if (textBox.gradientDirection === 'vertical') {
                // 세로 방향 그라데이션
                gradient = ctx.createLinearGradient(
                    0,
                    textBox.y - textHeight / 2,
                    0,
                    textBox.y + textHeight / 2
                );
            } else {
                // 가로 방향 그라데이션
                gradient = ctx.createLinearGradient(
                    textBox.x - textWidth / 2,
                    0,
                    textBox.x + textWidth / 2,
                    0
                );
            }
            gradient.addColorStop(0, textBox.gradientStart);
            gradient.addColorStop(1, textBox.gradientEnd);
            ctx.fillStyle = gradient;
        } else {
            ctx.fillStyle = textBox.color;
        }

        if (textBox.orientation === 'horizontal' || !textBox.orientation) {
            // 가로 텍스트 처리
            const lines = textBox.text.split('\n');
            const lineHeight = textBox.fontSize * 1.2;

            lines.forEach((line, i) => {
                const y = textBox.y + i * lineHeight - (lines.length - 1) * lineHeight / 2;

                // 글자 외곽선 그리기
                if (textBox.outlineColor) {
                    ctx.strokeStyle = textBox.outlineColor;
                    ctx.lineWidth = 2;
                    ctx.strokeText(line, textBox.x, y);
                }

                // 텍스트 그리기
                ctx.fillText(line, textBox.x, y);
            });
        } else if (textBox.orientation === 'vertical') {
            // 세로 텍스트 처리
            const characters = textBox.text.split('');
            let y = textBox.y - (characters.length - 1) * textBox.fontSize / 2;

            characters.forEach((char) => {
                // 글자 외곽선 그리기
                if (textBox.outlineColor) {
                    ctx.strokeStyle = textBox.outlineColor;
                    ctx.lineWidth = 2;
                    ctx.strokeText(char, textBox.x, y);
                }

                // 텍스트 그리기
                ctx.fillText(char, textBox.x, y);
                y += textBox.fontSize;
            });
        }

        // 포커스된 텍스트 박스에 점선 테두리 그리기
        if (index === focusedTextBoxIndex) {
            ctx.setLineDash([5, 5]);
            ctx.strokeStyle = '#000';
            ctx.lineWidth = 1;
            ctx.strokeRect(
                textBox.x - textWidth / 2 - 5,
                textBox.y - textHeight / 2 - 5,
                textWidth + 10,
                textHeight + 10
            );
            ctx.setLineDash([]);
        }

        // 선택된 텍스트 박스들에 점선 테두리 그리기
        if (selectedTextBoxes.includes(index)) {
            ctx.setLineDash([5, 5]);
            ctx.strokeStyle = '#000';
            ctx.lineWidth = 1;
            ctx.strokeRect(
                textBox.x - textWidth / 2 - 5,
                textBox.y - textHeight / 2 - 5,
                textWidth + 10,
                textHeight + 10
            );
            ctx.setLineDash([]);
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
        let textWidth, textHeight;

        if (box.orientation === 'vertical') {
            // 세로 텍스트 박스의 크기 계산
            const characters = box.text.split('');
            textWidth = box.fontSize;  // 세로 텍스트 박스의 너비는 글자 크기로 설정
            textHeight = characters.length * box.fontSize;  // 텍스트 박스의 높이는 글자의 수 * 글자 크기
        } else {
            // 가로 텍스트 박스의 크기 계산
            const lines = box.text.split('\n');
            textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 각 텍스트 박스의 가장 긴 줄의 너비 계산
            textHeight = box.fontSize * 1.2 * lines.length;  // 각 텍스트 박스의 높이 계산
        }

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


canvas.addEventListener('dblclick', function (e) {
    if (!textBox) return;  // 텍스트 박스가 없는 경우 아무것도 하지 않음
    
    const rect = canvas.getBoundingClientRect();
    const mouseX = e.clientX - rect.left;
    const mouseY = e.clientY - rect.top;

    let textWidth, textHeight;

    if (textBox.orientation === 'vertical') {
        // 세로 텍스트 박스의 크기 계산
        const characters = textBox.text.split('');
        textWidth = textBox.fontSize;  // 세로 텍스트 박스의 너비는 글자 크기
        textHeight = characters.length * textBox.fontSize;  // 텍스트 박스의 높이는 글자의 수 * 글자 크기

        // 더블클릭한 위치가 텍스트 박스 안쪽인지 확인
        if (mouseX >= textBox.x - textWidth / 2 && mouseX <= textBox.x + textWidth / 2 &&
            mouseY >= textBox.y - textHeight / 2 && mouseY <= textBox.y + textHeight / 2) {

            // 기존 텍스트 박스를 숨기고 DOM 요소로 텍스트 편집 가능하게 만듦
            const textarea = document.createElement('textarea');
            textarea.value = textBox.text;
            textarea.style.position = 'absolute';

            // 세로 텍스트인 경우 입력 상자를 세로 배치에 맞춰 설정
            textarea.style.left = `${rect.left + textBox.x - textWidth / 2}px`;
            textarea.style.top = `${rect.top + textBox.y - textHeight / 2}px`;  // 텍스트 위치로 이동
            textarea.style.width = `${textWidth + 20}px`;  // 너비는 글자 크기
            textarea.style.height = `${textHeight + 20}px`;  // 높이는 글자 수에 비례
            textarea.style.fontSize = `${textBox.fontSize}px`;
            textarea.style.fontFamily = textBox.font;
            textarea.style.border = '1px solid #000';
            textarea.style.padding = '5px';
            textarea.style.zIndex = 1000;
            textarea.style.lineHeight = '1.2';  // 줄 간격 설정

            document.body.appendChild(textarea);
            textarea.focus();  // 자동으로 포커스를 입력 상자에 설정

            // 포커스를 잃으면 텍스트 업데이트
            textarea.addEventListener('blur', function () {
                updateTextBox(textarea.value);  // 입력된 값을 텍스트 박스로 반영
                document.body.removeChild(textarea);  // 입력 상자 제거
            });
        }

    } else {
        // 가로 텍스트 박스의 크기 계산
        const lines = textBox.text.split('\n');
        textWidth = Math.max(...lines.map(line => ctx.measureText(line).width));  // 가장 긴 줄의 너비 계산
        textHeight = textBox.fontSize * 1.2 * lines.length;  // 전체 텍스트 박스의 높이 계산

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
    }
});



// 텍스트 박스 업데이트 함수
function updateTextBox(newText) {
    if (textBox) {
        textBox.text = newText.replace(/\r?\n/g, '\n');  // 줄바꿈 적용
        drawCanvas();  // 캔버스 다시 그리기
    }
}