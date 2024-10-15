<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Font Customizer</title>
    <link rel="stylesheet" href="static/css/generate/generate_font.css">
    <link rel="stylesheet" href="static/css/generate/font_collection.css">
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    
<c:if test="${not empty AImessage}">
	<script src="static/js/ai_studio/font_tutorial.js"></script>
    <link rel="stylesheet" href="static/css/ai_studio/font_tutorial.css">
</c:if>

</head>
<body>
<div class="main-contents">
<h2>폰트 작업을 할 이미지를 골라주세요.</h2>
<div class="button-container">
    <input type="file" id="imageUpload" accept="image/*">
    <button id="openModalBtn">내 이미지 중 선택</button>
</div>

<!-- 모달 -->
<div id="imageModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>내 이미지 중 선택</h2>
        <div>
            <c:forEach var="image" items="${imageList}">
                <input type="image" class="modal-image-item" src="${image.imageUrl}" style="width: 100px; height: auto;">
            </c:forEach>
        </div>
    </div>
</div>

<c:if test="${not empty AImessage}">
	<input type="hidden" id = "AImessage">
</c:if>

<!-- 툴바 UI 구성 -->
<div class="toolbar">
    <!-- 폰트 선택 -->
<select id="fontSelect">
    <option value="Arial" style="font-family: Arial;">Arial</option>
    <option value="'SF_HambakSnow', sans-serif" style="font-family: 'SF_HambakSnow', sans-serif;">SF_HambakSnow</option>
    <option value="'Danjo-bold-Regular', sans-serif" style="font-family: 'Danjo-bold-Regular', sans-serif;">Danjo-bold-Regular</option>
    <option value="'Galmuri9', sans-serif" style="font-family: 'Galmuri9', sans-serif;">Galmuri</option>
    <option value="'국립박물관문화재단클래식B', sans-serif" style="font-family: '국립박물관문화재단클래식B', sans-serif;">국립박물관문화재단클래식B</option>
    <option value="'MaplestoryOTFBold', sans-serif" style="font-family: 'MaplestoryOTFBold', sans-serif;">MaplestoryOTFBold</option>
    <option value="'Cafe24ClassicType-Regular', sans-serif" style="font-family: 'Cafe24ClassicType-Regular', sans-serif;">Cafe24ClassicType-Regular</option>
    <option value="'HSGyoulnoonkot', sans-serif" style="font-family: 'HSGyoulnoonkot', sans-serif;">HSGyoulnoonkot</option>
    <option value="'HakgyoansimByeolbichhaneulTTF-B', sans-serif" style="font-family: 'HakgyoansimByeolbichhaneulTTF-B', sans-serif;">HakgyoansimByeolbichhaneulTTF-B</option>
    <option value="'OKCHAN', sans-serif" style="font-family: 'OKCHAN', sans-serif;">OKCHAN</option>
</select>


	<!-- 폰트 크기 슬라이더 -->
	<label for="fontSizeSlider">Font Size (pt):</label>
	<input type="range" id="fontSizeSlider" min="10" max="150" value="10" step="1">

    
    <!-- 텍스트 박스 추가 버튼 -->
    <button id="addTextBoxBtn" class="toolbar-btn">가로TXT</button>
    <button id="addVerticalTextBoxBtn" class="toolbar-btn">세로TXT</button>

    <!-- 텍스트 스타일 버튼들 -->
    <button id="boldBtn" class="toolbar-btn"><b>B</b></button>
    <button id="italicBtn" class="toolbar-btn"><i>I</i></button>
    <button id="underlineBtn" class="toolbar-btn"><u>U</u></button>
    <button id="strikethroughBtn" class="toolbar-btn"><s>S</s></button>

    <!-- 텍스트 색상과 외곽선 색상 선택 -->
    <input type="color" id="textColor" title="글자색 선택">
    <input type="color" id="outlineColorInput" value="#FFFFFF" title="외곽선 색상 선택">

</div>
<div class="toolbar">
<!-- 그라데이션 적용 여부 -->
<label for="gradientInput">그라데이션 사용:</label>
<input type="checkbox" id="gradientInput">

<!-- 그라데이션 방향 선택 -->
<label for="gradientDirection">그라데이션 방향:</label>
<select id="gradientDirection" disabled>
    <option value="horizontal">가로</option>
    <option value="vertical">세로</option>
</select>

    <!-- 그라데이션 색상 설정 -->
    <input type="color" id="gradientStartInput" value="#ff0000" title="그라데이션 시작 색상" disabled>
    <input type="color" id="gradientEndInput" value="#0000ff" title="그라데이션 끝 색상" disabled>

</div>

<canvas id="canvas"></canvas>
<button id="saveBtn">이대로 저장</button>
<div id="uploadStatus" style="display:none;" class="custom-alert"></div>

<script src="static/js/font/libgif.js"></script>
<script src="static/js/font/text_style.js"></script>
<script src="static/js/font/canvas_draw.js"></script>
<script src="static/js/font/keyboard_controls.js"></script>
<script src="static/js/font/modal.js"></script>

</div>
</body>
</html>
