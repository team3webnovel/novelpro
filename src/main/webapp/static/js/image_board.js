<<<<<<< HEAD
function openModal(boardId, imageUrl, creationId) {
	document.getElementById('modalContent').textContent = "";
    // modal에 데이터를 세팅
    document.getElementById('modalImage').src = imageUrl;
	
	fetch('/team3webnovel/gije/image/board/detail/' + creationId, {
=======
let currentBoardId = null;
let currentCreationId = null;
let currentComment = null;

function openModal(boardId, imageUrl, creationId, comment) {
	document.getElementById('modal-content').textContent = "";
	document.getElementById('comment-content').innerHTML="";
	
	currentBoardId = boardId;
    currentCreationId = creationId;
    currentComment = comment;
	
    // modal에 데이터를 세팅
    document.getElementById('modalImage').src = imageUrl;
	
	refreshModal(boardId, creationId, comment);
    // modal을 보이게 설정
    $('#myModal').modal('show');
}

// modal을 닫는 함수
function closeModal() {
	document.getElementById('modalContent').textContent = "";
    $('#myModal').modal('hide');
}

function refreshModal(boardId, creationId, comment){
	fetch('/team3webnovel/images/board/detail/' + creationId, {
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645
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
<<<<<<< HEAD
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
=======
			// imageVo 객체와 comments 리스트를 사용하여 모달에 데이터를 세팅
	        const imageVo = data.imageVo; // imageVo 필드에 접근
	        const comments = data.comments; // comments 필드에 접근 (리스트)
			if(comments != null){
				let commentsHtml = comments.map(comment => {
		            return `<tr><td><strong>${comment.userName}</strong>: ${comment.content}
						<span class="comment-time">${comment.formattedCreatedAt}</td></tr></span>`;
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
			
			if (imageVo != null){
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
		})
		.catch(error => {
			console.error(error)
		})
}

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
    });
});
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645
