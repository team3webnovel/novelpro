let currentBoardId = null;
let currentCreationId = null;
let currentComment = null;

function openModal(boardId, imageUrl, creationId, comment, userId) {
	document.getElementById('modal-content').textContent = "";
	document.getElementById('comment-content').innerHTML="";
	document.getElementById('deleteBoard').innerHTML = "";

	currentBoardId = boardId;
    currentCreationId = creationId;
    currentComment = comment;
	
    // modal에 데이터를 세팅
    document.getElementById('modalImage').src = imageUrl;
	
	refreshModal(boardId, creationId, comment, userId);
    // modal을 보이게 설정
    $('#myModal').modal('show');
}

// modal을 닫는 함수
function closeModal() {
	document.getElementById('modalContent').textContent = "";
    $('#myModal').modal('hide');
}

function refreshModal(boardId, creationId, comment, boardUserId){
	let userId = document.getElementById('userId').value;
	
	fetch('/team3webnovel/images/board/detail/' + creationId, {
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
			// imageVo 객체와 comments 리스트를 사용하여 모달에 데이터를 세팅
	        const imageVo = data.imageVo; // imageVo 필드에 접근
	        const comments = data.comments; // comments 필드에 접근 (리스트)
			if(comments != null){
				let commentsHtml = comments.map(comment => {
		            return `<tr><td><strong>${comment.userName}</strong>: ${comment.content}
						<span class="comment-time">${comment.formattedCreatedAt}</span>
						${userId == comment.userId ? 
	                    	`<button type="button" class="comment-delete btn btn-danger btn-sm" data-comment-id="${comment.commentId}" style="float: right;">삭제</button>` 
	                    : ''}
						</td></tr>
						`;
		        }).join('');
				document.getElementById('comment-content').innerHTML=`
					<table class="table">
						<thead>
							<tr>
								<th>
									<div class="comment-container d-flex align-items-center">
		            					<textarea id="comment" name="comment" class="form-control me-2" placeholder="댓글을 입력하세요" style="flex: 1;"></textarea>
										<input type="hidden" id="boardId" name="boardId" value="${boardId}" />
		            					<button type="submit" id="comment-submit" class="btn btn-primary">작성</button>
		        					</div>
								</th>
							</tr>
						</thead>
						<tbody>
							` + commentsHtml
							+ `
						</tbody>
					</table>
				`;	
			}
			
			if (imageVo != null && imageVo.prompt != null){
				document.getElementById('modal-content').innerHTML = `
		            <table class="table">
		                <thead>
		                    <tr>
		                        <th scope="col">속성</th>
		                        <th scope="col">값</th>
		                    </tr>
		                </thead>
		                <tbody>
		                    <tr>
		                        <td>프롬포트</td>
		                        <td>
									<span id="shortContent"></span>
									<span id="fullContent" style="display:none;">${imageVo.prompt}</span>
									<button id="toggleButton" class="btn btn-link p-0" onclick="toggleContent()">자세히 보기</button>
								</td>
		                    </tr>
		                    <tr>
		                        <td>부정 프롬포트</td>
		                        <td>${imageVo.nPrompt}</td>
		                    </tr>
		                    <tr>
		                        <td>모델</td>
		                        <td>${imageVo.modelCheck}</td>
		                    </tr>
							<tr>
								<td>seed</td>
								<td>${imageVo.seed}</td>
							</tr>
							<tr>
								<td>코멘트</td>
								<td>${comment}</td>
							</tr>
		                </tbody>
		            </table>
		        `;
			} else{
				document.getElementById('modal-content').innerHTML = `
					<table class="table">
						<thead>
							<tr>
								<th scope="col">속성</th>
								<th scope="col">값</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>코멘트</td>
								<td>${comment}</td>
							</tr>
						</tbody>
					</table>
				`;
			}
			console.log(boardUserId, userId);
			if (boardUserId == userId) {
		        document.getElementById('deleteBoard').innerHTML = `
					<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
		            <button type="button" id="deletePostButton" class="btn btn-danger" onclick="deletePost(currentBoardId)">게시글 삭제</button>
		        `;
		    } else {
				document.getElementById('deleteBoard').innerHTML = `
					<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
				`;
			}
		})
		.catch(error => {
			console.error(error)
		})
}

function deletePost(boardId) {
    if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
        fetch(`/team3webnovel/images/board/delete/${boardId}`, {
            method: 'DELETE'
        })
		.then(response => response.json()) // JSON 응답을 받음
        .then(data => {
            if (data.success) {
                alert(data.message); // 서버에서 전달한 성공 메시지 출력
                window.location.href = '/team3webnovel/images/board';
            } else {
                alert(data.message); // 서버에서 전달한 실패 메시지 출력
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('게시글 삭제 중 오류가 발생했습니다.');
        });
    }
};

function toggleContent() {
    const shortContent = document.getElementById('shortContent');
    const fullContent = document.getElementById('fullContent');
    const toggleButton = document.getElementById('toggleButton');

    if (fullContent.style.display === "none") {
        fullContent.style.display = "inline"; // 전체 내용 보이기
        shortContent.style.display = "none";  // 짧은 내용 숨기기
        toggleButton.textContent = "숨기기"; // 버튼 텍스트 변경
    } else {
        fullContent.style.display = "none"; // 전체 내용 숨기기
        shortContent.style.display = "inline"; // 짧은 내용 보이기
        toggleButton.textContent = "자세히 보기"; // 버튼 텍스트 변경
    }
}

document.addEventListener("DOMContentLoaded", function() {
    // 부모 요소에 이벤트 위임을 적용
    document.getElementById('comment-content').addEventListener('click', function(event) {
        // 클릭한 요소가 'comment-submit' 버튼인지 확인
        if (event.target && event.target.id === 'comment-submit') {
            event.preventDefault();
            let comment = document.getElementById('comment').value;
            let boardId = document.getElementById('boardId').value;

            if (comment.trim() === "") {
                alert("댓글을 입력하세요.");
                return;
            }

            fetch('/team3webnovel/images/board/comment/write', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    'comment': comment, // 댓글 내용
                    'boardId': boardId  // 게시글 ID
                })
            })
            .then(response => response.json()) // 서버로부터 JSON 응답을 받음
            .then(data => {
                if (data.success) {
                    refreshModal(currentBoardId, currentCreationId, currentComment);
                } else {
                    alert('댓글 작성에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 작성 중 오류가 발생했습니다.');
            });
        }
		
		// 댓글 삭제 버튼 클릭 확인
        if (event.target && event.target.classList.contains('comment-delete')) {
            event.preventDefault();
            let commentId = event.target.getAttribute('data-comment-id'); // 삭제할 댓글의 ID

            if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
                fetch(`/team3webnovel/images/board/comment/delete?commentId=${commentId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                .then(response => response.json()) // 서버로부터 JSON 응답을 받음
                .then(data => {
                    if (data.success) {
                        alert('댓글이 삭제되었습니다.');
                        refreshModal(currentBoardId, currentCreationId, currentComment); // 댓글 목록 갱신
                    } else {
                        alert('댓글 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('댓글 삭제 중 오류가 발생했습니다.');
                });
            }
        }
    });
});


// 좋아요 상태를 토글하는 함수
function toggleLike(boardId) {
    const likeCountElement = document.getElementById(`like-count-${boardId}`);
    let likeCount = parseInt(likeCountElement.textContent, 10);
	
	let userId = document.getElementById('userId').value;

    fetch(`/team3webnovel/images/board/like/${boardId}`, {
        method: 'POST', // 좋아요/좋아요 취소 요청
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            userId: userId // 실제 사용자 ID로 교체
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
