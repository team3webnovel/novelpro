function openModal(boardId, imageUrl, creationId) {
	document.getElementById('modalContent').textContent = "";
    // modal에 데이터를 세팅
    document.getElementById('modalImage').src = imageUrl;
	
	fetch('/team3webnovel/gije/image/board/detail/' + creationId, {
		method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
		body: new URLSearchParams({
            'boardId': boardId
        })
	})
		.then(response => response.json())
		.then(data => {
			document.getElementById('modalContent').textContent = data.prompt;
		})
		.catch(error => console.error('실패'))
	
    // modal을 보이게 설정
    $('#myModal').modal('show');
}

// modal을 닫는 함수
function closeModal() {
	document.getElementById('modalContent').textContent = "";
    $('#myModal').modal('hide');
}