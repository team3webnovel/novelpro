function checkForImageGeneration() {
    // 서버로 작업 상태 확인 요청
    fetch('/team3webnovel/images/checkStatus')
	        .then(response => response.json())
	        .then(data => {
	            const storageIcon = document.querySelector('.storage-icon');
	            const notificationBadge = document.querySelector('.notification-badge');

	            // 기존 알림을 삭제한 후 재설정
	            if (notificationBadge) {
	                notificationBadge.remove();
	            }

	            if (data.status === 'completed') {
	                // 작업 완료 상태일 때 알림 표시
	                storageIcon.innerHTML += `
	                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger notification-badge">
	                        !
	                    </span>`;
	            }
	        })
	        .catch(error => console.error('Error:', error));
}

// 5초마다 상태 확인 요청을 서버로 보냄
setInterval(checkForImageGeneration, 5000);  // 5000ms = 5초

