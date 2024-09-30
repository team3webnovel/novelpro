function goToPage(boardId, currentPage) {
    const url = `/team3webnovel/gije/view/${boardId}?page=${currentPage}`;
    window.location.href = url;
}

document.addEventListener("DOMContentLoaded", function() {
	document.getElementById('comment-submit').addEventListener('click', function(event){
		event.preventDefault();
		let comment = document.getElementById('comment').value;
		let boardId = document.getElementById('boardId').value;
		
		if (comment.trim() === "") {
            alert("댓글을 입력하세요.");
            return;
        }
		
		fetch('/team3webnovel/gije/comment/write', {
			method: 'post',
			headers:{
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: new URLSearchParams({
                'comment': comment, // 댓글 내용
                'boardId': boardId  // 게시글 ID
            })
		}).then(response => response.json()) // 서버로부터 JSON 응답을 받음
        .then(data => {
            if (data.success) {
                location.reload(); // 성공 시 페이지 새로고침
            } else {
                alert('댓글 작성에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('댓글 작성 중 오류가 발생했습니다.');
        });
	})
	
	document.querySelectorAll('.comment-delete').forEach(button => {
	    button.addEventListener('click', function(event) {
	        event.preventDefault();
	        let commentId = this.getAttribute('data-comment-id'); // 버튼의 data-comment-id 속성에서 ID를 가져옵니다.

	        fetch('/team3webnovel/gije/comment/delete', {
	            method: 'post',
	            headers: {
	                'Content-Type': 'application/x-www-form-urlencoded'
	            },
	            body: new URLSearchParams({
	                'commentId': commentId, // 댓글 ID
	            })
	        })
	        .then(response => response.json()) // 서버로부터 JSON 응답을 받음
	        .then(data => {
	            if (data.success) {
	                location.reload(); // 성공 시 페이지 새로고침
	            } else {
	                alert('댓글 삭제에 실패했습니다.');
	            }
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            alert('댓글 삭제 중 오류가 발생했습니다.');
	        });
	    });
	});
});