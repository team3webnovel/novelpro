// 좋아요 상태를 토글하는 함수
function toggleLike(boardId) {
    const likeCountElement = document.getElementById(`like-count-${boardId}`);
    let likeCount = parseInt(likeCountElement.textContent, 10);
	

    fetch(`/team3webnovel/novel/like/${boardId}`, {
        method: 'POST', // 좋아요/좋아요 취소 요청
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // 서버 응답에 따라 UI 업데이트 (좋아요 추가/취소)
            if (data.liked) {
                likeCountElement.textContent = likeCount + 1;
            } else {
                likeCountElement.textContent = likeCount - 1;
            }
        } else {
            alert('좋아요 처리에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('서버와의 통신 중 문제가 발생했습니다.');
    });
}