<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소설 쓰기</title>
    <!-- CSS 파일 경로 수정 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/new_novel.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- contextPath를 JavaScript에서 사용할 수 있도록 변수로 설정 -->
    <script type="text/javascript">
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <!-- 조건부로 novel_tutorial.js 로드 -->
    <c:if test="${not empty AImessage}">
        <script src="${pageContext.request.contextPath}/static/js/ai_studio/novel_tutorial.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/ai_studio/novel_tutorial.css">
    </c:if> 
</head>
<body>
    <!-- 헤더 포함, 인라인 스타일로 간격 추가 -->
    <header style="margin-bottom: 100px;">
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    </header>
   <div class="container mt-5">
    <div class="row">
        <!-- 왼쪽: 소설 쓰기 폼 -->
        <div class="col-md-6">
            <h2>소설 쓰기</h2>
            <form method="POST" action="${pageContext.request.contextPath}/novel/new-novel">
                <!-- 제목 입력 -->
                <div class="form-group">
                    <label for="title">소설 제목</label>
                    <input type="text" class="form-control" id="title" name="title" placeholder="소설 제목을 입력하세요" required>
                </div>

                <!-- 장르 선택 -->
                <div class="form-group">
                    <label for="genre">장르</label>
                    <select class="form-control" id="genre" name="genre" required>
                        <option value="">장르 선택</option>
                        <option value="로판">로맨스판타지</option>
                        <option value="현판">현대판타지</option>
                        <option value="판타지">판타지</option>
                        <option value="무협">무협</option>
                        <option value="로맨스">로맨스</option>
                        <option value="일반">일반</option>
                    </select>
                </div>

               <!-- 표지 이미지 선택 -->
                    <label for="title">커버 선택</label>
                    <div class="col-md-6">
                        <button type="button" class="btn btn-custom" data-toggle="modal" data-target="#coverImageModal">커버선택하기</button>
                        <!-- 선택된 미리보기 -->
                        <div class="preview-container mt-3">
                            <img id="selectedCoverImagePreview" src="" alt="선택된 표지 이미지 또는 비디오 썸네일" style="max-width: 150px; height: auto; display: none;" />
                            <p>선택한 파일명: <span id="selectedCoverImageFileName">없음</span></p>
                        </div>
                        <!-- 선택한 이미지/비디오 ID를 숨긴 필드로 전송 -->
                        <input type="hidden" id="selectedCoverImageId" name="illust" value="" />
                        <!-- 이미지 및 비디오 선택 모달 -->
                        <div class="modal fade" id="coverImageModal" tabindex="-1" role="dialog" aria-labelledby="coverImageModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="coverImageModalLabel">표지 이미지 또는 비디오 선택</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <h6>이미지 선택</h6>
                                        <div class="row">
                                            <!-- 이미지 리스트 -->
                                            <c:forEach var="image" items="${imageList}">
                                                <div class="col-md-3 text-center mb-3">
                                                    <img src="${image.imageUrl}" alt="${image.filename}" class="img-thumbnail" style="cursor: pointer;" onclick="selectCoverImage('${image.creationId}', '${image.imageUrl}', '${image.filename}')">
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <h6>비디오 선택</h6>
                                        <div class="row">
                                            <!-- 비디오 리스트 (썸네일 사용) -->
                                            <c:forEach var="video" items="${videoList}">
                                                <div class="col-md-3 text-center mb-3">
                                                    <img src="${video.videoUrl}" alt="${video.videoFilename}" class="img-thumbnail" style="cursor: pointer;" onclick="selectCoverImage('${video.creationId}', '${video.videoUrl}', '${video.videoFilename}')">
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                <!-- 줄거리 입력 -->
                <div class="form-group mt-4">
                    <label for="intro">소설 줄거리</label>
                    <textarea class="form-control" id="intro" name="intro" rows="6" placeholder="소설 줄거리를 입력하세요" required></textarea>
                </div>

                <!-- 저장 버튼 -->
                <div class="d-flex justify-content-between mt-4">
                    <button type="submit" class="btn btn-save">저장</button>
                </div>
            </form>
        </div>
       			<!-- AImessage가 있을 때 숨겨진 필드로 추가 -->
		<c:if test="${not empty AImessage}">
			<input type="hidden" id="AImessage" name="AImessage"
				value="${AImessage}">
		</c:if>

        <!-- 오른쪽: AI Story Maker -->
        <div class="col-md-6">
            <h2>AI Story Maker</h2>
                <div id="chat-container" class="chat-window-container">
                    <div id="chat-log" class="chat-window border p-3"></div>
                    <!-- 사용자 입력 -->
                    <div id="input-area" class="mt-3 d-flex input-area-sticky">
                        <input type="text" id="user-input" name="user-input" placeholder="메시지를 입력하세요" class="form-control">
                        <button id="submit-btn" type="submit" class="btn btn-input ml-2">입력</button>
                    </div>
                </div>
        </div>
    </div>
</div>

                

    <!-- 이미지 선택 스크립트 -->
    <script>
    function selectCoverImage(creationId, imageUrl, fileName) {
        var imgPreview = document.getElementById('selectedCoverImagePreview');
        imgPreview.src = imageUrl;
        imgPreview.style.display = 'block'; // 이미지 표시
        document.getElementById('selectedCoverImageFileName').innerText = fileName;
        document.getElementById('selectedCoverImageId').value = creationId;
        $('#coverImageModal').modal('hide'); // 모달 닫기
    }

    </script>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('form');
    const AImessage = document.getElementById('AImessage');

    form.addEventListener('submit', function(event) {
        // 만약 AImessage가 존재한다면 이를 숨겨진 필드로 폼에 추가
        if (AImessage) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'AImessage';
            input.value = AImessage.value; // AImessage 값을 폼에 포함
            form.appendChild(input);
        }
    });
});
</script>

<!-- JavaScript 파일 링크 -->
<script src="${pageContext.request.contextPath}/static/js/chatbot.js"></script>
<!-- jQuery의 slim 버전이 아닌 일반 버전 사용 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
        window.addEventListener('beforeunload', function () {
            fetch('/resetChatHistory', {
                method: 'POST'
            }).then(response => {
                // 성공적으로 chatHistory 리셋됨
            }).catch(error => {
                console.error('Error resetting chat history:', error);
            });
        });
    </script>
</body>
</html>