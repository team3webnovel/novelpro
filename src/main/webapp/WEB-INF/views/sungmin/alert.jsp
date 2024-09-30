<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Image Generation Status</title>
    <script type="text/javascript">
	    var clientId = '<%=request.getAttribute("clientId")%>';  // 서버에서 전달된 clientId를 변수에 저장
	    var socket;
	
	    // WebSocket 연결 설정
	    function connectWebSocket() {
	        socket = new WebSocket('ws://192.168.0.237:8188/ws?clientId=' + clientId);  
	
	        socket.onmessage = function(event) {
	            var data = JSON.parse(event.data);
	            console.log("Received data: ", data);
	
	            // 이미지 생성 완료 메시지 수신 시 알림
	            if (data.type === 'executed' && data.data.output && data.data.output.images) {
	                var imageUrl = "http://192.168.0.237:8188/view?filename=" + data.data.output.images[0].filename + "&type=output";
	                alert('Image generated successfully! URL: ' + imageUrl);
	            }
	        };
	
	        socket.onopen = function() {
	            console.log("WebSocket 연결 성공!");
	        };
	
	        socket.onclose = function() {
	            console.log("WebSocket 연결 종료");
	        };
	
	        socket.onerror = function(error) {
	            console.log("WebSocket 에러: " + error.message);
	        };
	    }
	
	    // 페이지 로드 시 WebSocket 연결
	    window.onload = function() {
	        connectWebSocket();  // WebSocket 연결
	    };
	</script>

</head>
<body>
    <h1>Image Generation</h1>
    <div id="imageResult">이미지 생성 상태가 여기에 표시됩니다.</div>
    <div id="filename"></div>
    <div id="connectionStatus">WebSocket 상태: 초기화되지 않음</div>
</body>
</html>
