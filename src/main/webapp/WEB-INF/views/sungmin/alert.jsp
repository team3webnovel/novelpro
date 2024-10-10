<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Image Generation Status</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/modal.css">
    <script src="<%= request.getContextPath()%>/static/js/imageStatus.js"></script>
</head>
<body>
    <h1>Image Generation</h1>
    <div id="imageResult">이미지 생성 상태가 여기에 표시됩니다.</div>
    <div id="filename"></div>
    <div id="connectionStatus">WebSocket 상태: 초기화되지 않음</div>

    <!-- 모달 HTML -->
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>Image generation completed!</h2>
            <img id="imageResultImg" src="" alt="Generated Image" style="max-width:100%;">
        </div>
    </div>
</body>
</html>
