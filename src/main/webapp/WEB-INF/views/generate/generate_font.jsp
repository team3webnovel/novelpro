<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Font Customizer</title>
    <link rel="stylesheet" href="static/css/font_customizer.css">
    
</head>
<body>
<!-- 직접 올리기 섹션 -->
<div class="toolbar">
    <h2>직접 올리기</h2>
    <div>
        <input type="file" id="imageUpload" accept="image/*">
    </div>
</div>
<!-- 모달 버튼 -->
<button id="openModalBtn">이미지 선택하기</button>

<!-- 모달 -->
<div id="imageModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>내 그림 선택</h2>
        <div>
            <c:forEach var="image" items="${imageList}">
                <input type="image" class="modal-image-item" src="${image.imageUrl}" style="width: 100px; height: auto;">
            </c:forEach>
        </div>
    </div>
</div>

<!-- 툴바 UI 구성 -->
<div class="toolbar">
    <!-- 폰트 선택 -->
    <select id="fontSelect">
        <option value="Arial">Arial</option>
        <option value="'SF_HambakSnow', sans-serif">SF_HambakSnow</option>
        <option value="'Danjo-bold-Regular', sans-serif">Danjo-bold-Regular</option>
        <option value="'Galmuri9', sans-serif">Galmuri</option>
        <option value="'국립박물관문화재단클래식B', sans-serif">국립박물관문화재단클래식B</option>
        <option value="'MaplestoryOTFBold', sans-serif">MaplestoryOTFBold</option>
        <option value="'Cafe24ClassicType-Regular', sans-serif">Cafe24ClassicType-Regular</option>
        <option value="'HSGyoulnoonkot', sans-serif">HSGyoulnoonkot</option>
        <option value="'HakgyoansimByeolbichhaneulTTF-B', sans-serif">HakgyoansimByeolbichhaneulTTF-B</option>
        <option value="'OKCHAN', sans-serif">OKCHAN</option>
    </select>

	<!-- 폰트 크기 슬라이더 -->
	<label for="fontSizeSlider">Font Size (pt):</label>
	<input type="range" id="fontSizeSlider" min="10" max="70" value="10" step="1">

    
    <!-- 텍스트 박스 추가 버튼 -->
    <button id="addTextBoxBtn" class="toolbar-btn">TXT</button>

    <!-- 텍스트 스타일 버튼들 -->
    <button id="boldBtn" class="toolbar-btn"><b>B</b></button>
    <button id="italicBtn" class="toolbar-btn"><i>I</i></button>
    <button id="underlineBtn" class="toolbar-btn"><u>U</u></button>
    <button id="strikethroughBtn" class="toolbar-btn"><s>S</s></button>

    <!-- 텍스트 색상과 외곽선 색상 선택 -->
    <input type="color" id="textColor" title="글자색 선택">
    <input type="color" id="outlineColorInput" value="#FFFFFF" title="외곽선 색상 선택">

    <!-- 그라데이션 적용 여부 -->
    <label for="gradientInput">그라데이션 사용:</label>
    <input type="checkbox" id="gradientInput">

    <!-- 그라데이션 색상 설정 -->
    <input type="color" id="gradientStartInput" value="#ff0000" title="그라데이션 시작 색상" disabled>
    <input type="color" id="gradientEndInput" value="#0000ff" title="그라데이션 끝 색상" disabled>

</div>

<canvas id="canvas"></canvas>
<button id="saveBtn">이미지 저장</button>
<div id="uploadStatus"></div> <!-- 성공/실패 메시지가 표시될 영역 -->

<script src="static/js/font/text_style.js"></script>
<script src="static/js/font/canvas_draw.js"></script>
<script src="static/js/font/keyboard_controls.js"></script>
<script src="static/js/font/drag_controls.js"></script>
<script src="static/js/font/modal.js"></script>
<script>
// 이미지가 클릭되었을 때 canvas에 이미지를 그리는 함수
function selectImageAndDraw(imageUrl) {
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext('2d');
    var img = new Image();

    img.onload = function () {
        // 이미지가 로드되면 캔버스 크기를 이미지 크기에 맞게 조정
        canvas.width = img.width;
        canvas.height = img.height;
        // 이미지 그리기
        ctx.drawImage(img, 0, 0);
        drawCanvas();  // 필요하다면 추가 작업 (예: 텍스트 추가 등)
    };

    img.src = imageUrl; // 이미지 URL 설정
}

// 클릭 이벤트 리스너 추가
document.querySelectorAll('.image-item').forEach(function (imgElement) {
    imgElement.addEventListener('click', function () {
        // 이미지 URL 가져오기
        var imageUrl = imgElement.getAttribute('src');
        // 선택한 이미지를 캔버스에 그리기
        selectImageAndDraw(imageUrl);
    });
});

</script>

</body>
</html>
