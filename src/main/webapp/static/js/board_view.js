document.addEventListener("DOMContentLoaded", function() {
    // 댓글 작성 버튼 이벤트
    document.getElementById('comment-submit').addEventListener('click', function(event) {
        event.preventDefault();
        let comment = document.getElementById('comment').value;
        let boardId = document.getElementById('boardId').value;

        if (comment.trim() === "") {
            alert("댓글을 입력하세요.");
            return;
        }

        fetch('/team3webnovel/board/comment/write', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'comment': comment,  // 댓글 내용
                'boardId': boardId   // 게시글 ID
            })
        }).then(response => response.json())  // 서버로부터 JSON 응답을 받음
        .then(data => {
            if (data.success) {
                location.reload();  // 성공 시 페이지 새로고침
            } else {
                alert('댓글 작성에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('댓글 작성 중 오류가 발생했습니다.');
        });
    });

    // 댓글 삭제 버튼 이벤트
    document.querySelectorAll('.btn-danger').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            let commentId = this.getAttribute('data-comment-id');  // 버튼의 data-comment-id 속성에서 ID를 가져옴

            if (!commentId) {
                return;  // 게시글 삭제 버튼일 경우 이벤트 무시
            }

            if (confirm("정말 댓글을 삭제하시겠습니까?")) {
                fetch(`/team3webnovel/board/comment/delete?commentId=${commentId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                .then(response => response.json())  // 서버로부터 JSON 응답을 받음
                .then(data => {
                    if (data.success) {
                        location.reload();  // 성공 시 페이지 새로고침
                    } else {
                        alert('댓글 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('댓글 삭제 중 오류가 발생했습니다.');
                });
            }
        });
    });

    // 게시글 삭제 버튼 이벤트
    document.querySelector('form#deleteForm button.btn-danger').addEventListener('click', function(event) {
        event.preventDefault();
        let boardId = document.getElementById('boardId').value;

        if (confirm("정말 게시글을 삭제하시겠습니까?")) {
            fetch(`/team3webnovel/board/delete/${boardId}`, {
                method: 'DELETE'
            })
            .then(response => response.json())  // JSON 응답을 받음
            .then(data => {
                if (data.success) {
                    alert(data.message);  // 서버에서 전달한 성공 메시지 출력
                    window.location.href = '/team3webnovel/board';
                } else {
                    alert(data.message);  // 서버에서 전달한 실패 메시지 출력
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('게시글 삭제 중 오류가 발생했습니다.');
            });
        }
    });
});
