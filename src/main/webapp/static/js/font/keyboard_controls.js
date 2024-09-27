// 복사, 붙여넣기, 되돌리기 등을 위한 변수들
let clipboardTextBoxes = []; // 다중 복사 클립보드
let undoStack = []; // 되돌리기 기능을 위한 스택

document.addEventListener('keydown', function (e) {
    // Ctrl + C: 복사
    if (e.ctrlKey && e.key === 'c') {
        if (selectedTextBoxes.length > 0) {
            // 다중 선택된 텍스트 박스를 복사
            clipboardTextBoxes = selectedTextBoxes.map(index => ({ ...textBoxes[index] }));
        } else if (focusedTextBoxIndex !== -1) {
            // 단일 선택된 텍스트 박스를 복사
            clipboardTextBoxes = [{ ...textBoxes[focusedTextBoxIndex] }];
        }
        e.preventDefault();
    } 
    // Ctrl + X: 잘라내기
    else if (e.ctrlKey && e.key === 'x') {
        if (selectedTextBoxes.length > 0) {
            clipboardTextBoxes = selectedTextBoxes.map(index => ({ ...textBoxes[index] }));
            undoStack.push([...textBoxes]); 
            selectedTextBoxes.sort((a, b) => b - a).forEach(index => textBoxes.splice(index, 1));
            selectedTextBoxes = [];
        } else if (focusedTextBoxIndex !== -1) {
            clipboardTextBoxes = [{ ...textBoxes[focusedTextBoxIndex] }];
            undoStack.push([...textBoxes]); 
            textBoxes.splice(focusedTextBoxIndex, 1);
            focusedTextBoxIndex = -1;
        }
        drawCanvas(); 
        e.preventDefault();
    } 
    // Ctrl + V: 붙여넣기
    else if (e.ctrlKey && e.key === 'v') {
        if (clipboardTextBoxes.length > 0) {
            undoStack.push([...textBoxes]); 
            clipboardTextBoxes.forEach(textBox => {
                const newTextBox = { ...textBox, x: textBox.x + 20, y: textBox.y + 20 };
                textBoxes.push(newTextBox); 
            });
            selectedTextBoxes = clipboardTextBoxes.map((_, i) => textBoxes.length - clipboardTextBoxes.length + i);
            drawCanvas();
        }
        e.preventDefault();
    } 
    // Ctrl + Z: 되돌리기
    else if (e.ctrlKey && e.key === 'z') {
        if (undoStack.length > 0) {
            textBoxes = undoStack.pop(); 
            selectedTextBoxes = [];
            focusedTextBoxIndex = -1;
            drawCanvas(); 
        }
        e.preventDefault();
    }
});

// 키보드 이벤트를 통해 텍스트 박스 이동 및 삭제
document.addEventListener('keydown', function (e) {
    // 텍스트 박스 수정 중이면 삭제를 막음 (textarea에 포커스가 있을 때)
    if (document.activeElement.tagName.toLowerCase() === 'textarea') return;

    // 다중 포커스가 있을 경우 이동 및 삭제 처리
    if (selectedTextBoxes.length > 0) {
        handleMultiTextBoxMoveDelete(e);
    } 
    // 단일 포커스가 있을 경우 처리
    else if (focusedTextBoxIndex !== -1) {
        handleSingleTextBoxMoveDelete(e);
    }
});

function handleMultiTextBoxMoveDelete(e) {
    // 화살표 키를 눌렀을 때 기본 동작(스크롤)을 방지
    if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight'].includes(e.key)) {
        e.preventDefault();
    }

    switch (e.key) {
        case 'ArrowUp':
            selectedTextBoxes.forEach(index => textBoxes[index].y -= 5);
            drawCanvas();
            break;
        case 'ArrowDown':
            selectedTextBoxes.forEach(index => textBoxes[index].y += 5);
            drawCanvas();
            break;
        case 'ArrowLeft':
            selectedTextBoxes.forEach(index => textBoxes[index].x -= 5);
            drawCanvas();
            break;
        case 'ArrowRight':
            selectedTextBoxes.forEach(index => textBoxes[index].x += 5);
            drawCanvas();
            break;
        case 'Delete':
        case 'Backspace':
            deleteSelectedTextBoxes();
            break;
    }
}

function handleSingleTextBoxMoveDelete(e) {
    if (!textBox) return;

    // 화살표 키를 눌렀을 때 기본 동작(스크롤)을 방지
    if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight'].includes(e.key)) {
        e.preventDefault();
    }

    switch (e.key) {
        case 'ArrowUp':
            textBox.y -= 5;
            drawCanvas();
            break;
        case 'ArrowDown':
            textBox.y += 5;
            drawCanvas();
            break;
        case 'ArrowLeft':
            textBox.x -= 5;
            drawCanvas();
            break;
        case 'ArrowRight':
            textBox.x += 5;
            drawCanvas();
            break;
        case 'Delete':
        case 'Backspace':
            deleteFocusedTextBox();
            break;
    }
}

// 다중 선택된 텍스트 박스 삭제 함수
function deleteSelectedTextBoxes() {
    if (selectedTextBoxes.length > 0) {
        selectedTextBoxes.sort((a, b) => b - a).forEach(index => textBoxes.splice(index, 1));
        selectedTextBoxes = [];
        drawCanvas();
    }
}

// 단일 포커스된 텍스트 박스 삭제 함수
function deleteFocusedTextBox() {
    if (focusedTextBoxIndex !== -1) {
        textBoxes.splice(focusedTextBoxIndex, 1);
        focusedTextBoxIndex = -1;
        textBox = null;
        drawCanvas();
    }
}
