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
            // WebSocket 서버에 clientId를 포함하여 연결
            socket = new WebSocket('ws://192.168.0.237:8188/ws?clientId=' + clientId);  

            // 서버로부터 메시지 수신 처리
            socket.onmessage = function(event) {
                var data = JSON.parse(event.data);
                console.log("Received data: ", data); // 모든 수신 메시지 로그

                // 이미지 생성 완료 메시지 수신 시 알림 및 이미지 표시
                if (data.type === 'executed' && data.data.output && data.data.output.images) {
                    console.log("Executed message received: ", data);  // 실행 완료 메시지 로그
                    var imageUrl = "http://192.168.0.237:8188/view?filename=" 
                                    + data.data.output.images[0].filename 
                                    + "&subfolder=" + data.data.output.images[0].subfolder 
                                    + "&type=output&nocache=" + new Date().getTime();
                    alert("Image generated successfully! URL: " + imageUrl);

                    // 이미지 생성 결과를 UI에 업데이트
                    document.getElementById("imageResult").innerHTML = '<img src="' + imageUrl + '" alt="Generated Image" style="max-width: 100%;"/>';
                    document.getElementById("filename").innerHTML = "Filename: " + data.data.output.images[0].filename;
                }

                // 상태 메시지 수신 처리
                if (data.type === 'status') {
                    console.log("Status update: ", data.data.status.exec_info);
                    var queueRemaining = data.data.status.exec_info.queue_remaining;
                    console.log("Queue remaining: " + queueRemaining);
                }

                // 이미지 생성 진행 중인 메시지 처리
                if (data.type === 'executing') {
                    console.log("Image generation in progress. Prompt ID: " + data.data.prompt_id);
                }
            };

            // WebSocket 연결이 열렸을 때 처리
            socket.onopen = function() {
                console.log("WebSocket 연결 성공!");
                document.getElementById("connectionStatus").innerHTML = "WebSocket 상태: 연결 성공";
            };

            // WebSocket 연결이 닫혔을 때 처리
            socket.onclose = function() {
                console.log("WebSocket 연결 종료");
                document.getElementById("connectionStatus").innerHTML = "WebSocket 상태: 연결 종료";
            };

            // WebSocket 에러 발생 시 처리
            socket.onerror = function(error) {
                console.log("WebSocket 에러: " + error.message);
                document.getElementById("connectionStatus").innerHTML = "WebSocket 상태: 에러 발생";
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
    <div id="imageResult">이미지 생성 상태가 여기에 표시됩니다.</div>
    <div id="filename"></div>
    <div id="connectionStatus">WebSocket 상태: 초기화되지 않음</div>
</body>
</html>
