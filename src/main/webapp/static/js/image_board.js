function openModal(imageUrl, creationId) {
    // modal에 데이터를 세팅
    document.getElementById('modalImage').src = imageUrl;
	
	fetch('/team3webnovel/gije/image/board/detail/' + creationId)
		.then(response => response.json())
		.then(data => {
			document.getElementById('modalContent').textContent = data.prompt;
		})
		.catch(error => console.error('실패'))
	
    // modal을 보이게 설정
    document.getElementById('myModal').style.display = 'block';
}

// modal을 닫는 함수
function closeModal() {
    document.getElementById('myModal').style.display = 'none';
}