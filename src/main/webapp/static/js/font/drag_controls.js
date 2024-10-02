let isDragging = false;
let dragStartX, dragStartY;
let dragEndX, dragEndY;

// 마우스 드래그 시작
canvas.addEventListener('mousedown', function (e) {
    const rect = canvas.getBoundingClientRect();
    dragStartX = e.clientX - rect.left;
    dragStartY = e.clientY - rect.top;
    isDragging = true;

    drawCanvas(); // 기존 캔버스 초기화 및 그리기
});

// 마우스 드래그 중
canvas.addEventListener('mousemove', function (e) {
    if (!isDragging) return;

    const rect = canvas.getBoundingClientRect();
    dragEndX = e.clientX - rect.left;
    dragEndY = e.clientY - rect.top;

    // 드래그 범위 점선 그리기
    drawCanvas(); // 캔버스 초기화 및 기존 텍스트 박스 그리기
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
});

// 마우스 드래그 끝
canvas.addEventListener('mouseup', function (e) {
    isDragging = false;
    drawCanvas(); // 드래그 끝난 후 다시 그리기
});
