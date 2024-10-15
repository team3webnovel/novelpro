// 좋아요 상태를 토글하는 함수
function toggleLike(boardId) {
    const likeCountElement = document.getElementById(`like-count-${boardId}`);
    let likeCount = parseInt(likeCountElement.textContent, 10);

    fetch(`/team3webnovel/novel/like/${boardId}`, {
        method: 'POST', // 좋아요/좋아요 취소 요청
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
    .then(response => {
        if (response.status === 401) { // 인증되지 않은 사용자
            return response.json(); // JSON으로 리다이렉트 정보 받기
        }
        return response.json(); // 정상적인 응답 처리
    })
    .then(data => {
        if (data.redirect) {
            // 서버에서 리다이렉트 URL을 받은 경우
            window.location.href = data.redirect; // 로그인 페이지로 리다이렉트
        } else if (data.success) {
            // 좋아요 처리 성공
            if (data.liked) {
                likeCountElement.textContent = likeCount + 1; // 좋아요 수 증가
            } else {
                likeCountElement.textContent = likeCount - 1; // 좋아요 수 감소
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
