<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Video Generation Status</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/modal.css">
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</head>
<body>
    <h1>Video Generation</h1>
    <div id="videoResult">비디오 생성 상태가 여기에 표시됩니다.</div>
    <div id="filename"></div>
    <div id="connectionStatus">WebSocket 상태: 초기화되지 않음</div>

    <!-- 모달 HTML -->
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>Video generation completed!</h2>
            <video id="videoResultPlayer" controls style="max-width:100%;">
                <source id="videoSource" src="" type="video/mp4">
                Your browser does not support the video tag.
            </video>
        </div>
    </div>

    <!-- 외부 JavaScript 파일 -->
    <script src="<%= request.getContextPath()%>/static/js/videoStatus.js"></script>
</body>
</html>
