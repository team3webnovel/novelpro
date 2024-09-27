<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Font Customizer</title>
    <link rel="stylesheet" href="static/css/font_customizer.css">
</head>
<body>

<h2>이미지에 폰트 꾸미기</h2>

<!-- 이미지 업로드 폼 -->
<div>
    <input type="file" id="imageUpload" accept="image/*"><br><br>
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

<!-- 텍스트 수정 영역 (툴바와 캔버스 사이에 위치) -->
<textarea id="textEditBox" style="
    position: relative;
    width: 20%; 
    height: 30px; 
    font-size: 20px; 
    top: 50%; 
    left: 50%; 
    transform: translate(-50%, -50%); 
    z-index: 1000;">
</textarea>

<!-- 캔버스 (이미지와 텍스트를 표시할 영역) -->
<canvas id="canvas"></canvas>

<script src="static/js/font/text_style.js"></script>
<script src="static/js/font/canvas_draw.js"></script>
<script src="static/js/font/keyboard_controls.js"></script>
<script src="static/js/font/drag_controls.js"></script>

</body>
</html>
