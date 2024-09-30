<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Image Generation Status</title>
<%--     <script src="<%= request.getContextPath()%>/static/js/connectWebSocket.js"></script> <!-- 외부 스크립트 파일 로드 --> --%>
	<script>
	function checkForImageGeneration() {
	    // 서버로 작업 상태 확인 요청
	    fetch('/team3webnovel/images/checkStatus')
	        .then(response => response.json())
	        .then(data => {
	            if (data.status === 'completed') {
	                // 작업이 완료되었을 경우, alert 띄우기
	                alert('Image generation completed! URL: ' + data.imageUrl);

	                // 이미지 URL로 페이지 이동 또는 추가 처리 가능
	                // window.location.href = '/viewImage?url=' + data.imageUrl;
	            }
	        })
	        .catch(error => console.error('Error checking status:', error));
	}

	// 5초마다 상태 확인 요청을 서버로 보냄
	setInterval(checkForImageGeneration, 5000);  // 5000ms = 5초

	</script>
</head>
<body>
    <h1>Image Generation</h1>
    <div id="imageResult">이미지 생성 상태가 여기에 표시됩니다.</div>
    <div id="filename"></div>
    <div id="connectionStatus">WebSocket 상태: 초기화되지 않음</div>
</body>
</html>
