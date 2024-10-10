function checkForImageGeneration() {
    // 서버로 작업 상태 확인 요청
    fetch('/team3webnovel/images/checkStatus')
        .then(response => response.json())
        .then(data => {
            if (data.status === 'completed') {
                // 작업이 완료되었을 경우, 모달 띄우기
                document.getElementById("imageResultImg").src = data.imageUrl;
                document.getElementById("myModal").style.display = "block";
            }
        })
        .catch(error => console.error('Error checking status:', error));
}

// 5초마다 상태 확인 요청을 서버로 보냄
setInterval(checkForImageGeneration, 5000);  // 5000ms = 5초

// 모달 닫기 기능
function closeModal() {
    document.getElementById("myModal").style.display = "none";
}
