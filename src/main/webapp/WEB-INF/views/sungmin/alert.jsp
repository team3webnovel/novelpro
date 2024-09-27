<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Image Generation Status</title>
    <script type="text/javascript">
        var socket;

        // WebSocket 연결 설정
        function connectWebSocket() {
            socket = new WebSocket('ws://192.168.0.237:8188/ws?clientId=yourClientId');  // WebSocket 서버 주소

            // 서버로부터 메시지 수신 처리
            socket.onmessage = function(event) {
                var data = JSON.parse(event.data);

                if (data.type === 'notification') {
                    alert("Image generated successfully! URL: " + data.message);
                    // 필요 시 DOM 업데이트 가능
                    document.getElementById("imageResult").innerHTML = '<img src="' + data.message + '" alt="Generated Image"/>';
                }
            };

            // WebSocket 연결이 열렸을 때 처리
            socket.onopen = function() {
                console.log("WebSocket 연결 성공!");
            };

            // WebSocket 연결이 닫혔을 때 처리
            socket.onclose = function() {
                console.log("WebSocket 연결 종료");
            };

            // WebSocket 에러 발생 시 처리
            socket.onerror = function(error) {
                console.log("WebSocket 에러: " + error.message);
            };
        }

        // 페이지가 로드될 때 WebSocket 연결 시작
        window.onload = function() {
            connectWebSocket();  // 페이지 로딩 시 WebSocket 연결 시작
        };
    </script>
</head>
<body>
    <h1>Image Generation</h1>
    <div id="imageResult"></div>
</body>
</html>
