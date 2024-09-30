window.onload = function() {
    fetch('/team3webnovel/images/getClientId')  // 서버에서 clientId를 반환하는 API 엔드포인트
        .then(response => response.json())
        .then(data => {
            var clientId = data.clientId;
            if (clientId) {
                connectWebSocket(clientId);  // WebSocket 연결
            } else {
                console.error("clientId가 null입니다.");
            }
        })
        .catch(error => console.error('Error fetching clientId:', error));
};

function connectWebSocket(clientId) {
    var socket = new WebSocket('ws://192.168.0.237:8188/ws?clientId=' + clientId);

    socket.onmessage = function(event) {
        var data = JSON.parse(event.data);
		console.log("Received data: ", data);

        // 이미지 생성 완료 메시지 수신 시 알림
        if (data.type === 'executed' && data.data.output && data.data.output.images) {
            var imageUrl = "http://192.168.0.237:8188/view?filename=" + data.data.output.images[0].filename + "&type=output";
            alert("이미지 생성 완료");
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
