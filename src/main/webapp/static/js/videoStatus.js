// 비디오 상태 업데이트 함수 (웹소켓 또는 주기적 확인)
function updateVideoStatus(videoUrl, filename) {
    document.getElementById("videoResult").innerHTML = "비디오 생성이 완료되었습니다.";
    document.getElementById("filename").innerHTML = "파일명: " + filename;
    document.getElementById("videoSource").src = videoUrl;
    openModal();
}

// 모달 열기 함수
function openModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
}

// 모달 닫기 함수
function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

// 웹소켓 연결 상태 표시 함수
function updateConnectionStatus(status) {
    document.getElementById("connectionStatus").innerHTML = "WebSocket 상태: " + status;
}

// WebSocket 연결 초기화 함수
function initWebSocketConnection() {
    const socket = new WebSocket("ws://yourserveraddress/videoStatus");

    socket.onopen = function() {
        updateConnectionStatus("연결됨");
    };

    socket.onmessage = function(event) {
        const data = JSON.parse(event.data);
        if (data.status === "completed") {
            updateVideoStatus(data.videoUrl, data.filename);
        }
    };

    socket.onclose = function() {
        updateConnectionStatus("연결 종료됨");
    };

    socket.onerror = function(error) {
        updateConnectionStatus("에러 발생: " + error.message);
    };
}

// 비디오 생성 폼 제출 함수
function submitVideoForm() {
    const form = document.getElementById('videoGenerateForm');
    const formData = new FormData(form);

    // Ajax 요청을 통해 서버에 비디오 생성 요청을 전송
    fetch('/videos/generate', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        console.log("Response Status:", response.status);  // 응답 상태 로그
        return response.json();
    })
    .then(data => {
        console.log("Server Response:", data);  // 서버에서 반환된 데이터 로그

        if (data.status === "success") {
            document.getElementById('generationResult').innerHTML = 
                `<p>Video generation started successfully! Check the status in a few moments.</p>`;
            initWebSocketConnection();  // WebSocket 연결 초기화
        } else {
            document.getElementById('generationResult').innerHTML = 
                `<p>Error: ${data.message}</p>`;
        }
    })
    .catch(error => {
        console.error("Error occurred:", error);  // 에러 발생 시 콘솔에 출력
        document.getElementById('generationResult').innerHTML = 
            `<p>Error: ${error.message}</p>`;
    });

    return false; // 폼의 기본 제출 동작 방지
}
