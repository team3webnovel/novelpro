<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회차 쓰기</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- CKEditor CDN 추가 -->
    <script src="https://cdn.ckeditor.com/4.20.0/full/ckeditor.js"></script>
</head>
<body>
    <!-- 헤더 포함, 인라인 스타일로 간격 추가 -->
    <header style="margin-bottom: 100px;">
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    </header>

<div class="container mt-5">
    <h2>회차 쓰기</h2>
    
    <!-- 폼 시작 -->
    <form action="<%=request.getContextPath()%>/episode/update/${episode.novelId}/${episode.episodeNo}" method="POST">
        
        <!-- 이미지 선택 드롭다운 -->
        <div class="form-group">
            <label for="imageSelect">이미지를 선택하세요</label>
            <select id="imageSelect" class="form-control" name="illust" onchange="displaySelectedImage()">
                <option value="">이미지를 선택하세요</option>
                <c:forEach var="image" items="${imageList}">
                    <option value="${image.creationId}" data-image-url="${image.imageUrl}" 
                        <c:if test="${image.creationId == episode.imageId}">selected</c:if> >
                        ${image.filename != null ? image.filename : image.imageUrl}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- 선택한 이미지 미리보기 -->
        <div class="mt-3">
            <img id="selectedImagePreview" src="" alt="선택된 이미지가 여기에 표시됩니다" style="max-width: 100%; height: auto; display: none;" />
        </div>

        <!-- 선택한 이미지 파일명 표시 -->
        <p>선택한 파일명: <span id="selectedImageFileName">없음</span></p>

        <!-- BGM 선택 드롭다운 -->
        <div class="form-group">
            <label for="bgmSelect">BGM을 선택하세요</label>
            <select id="bgmSelect" class="form-control" name="bgm" onchange="displaySelectedBGM()">
                <option value="">BGM을 선택하세요</option>
                <c:forEach var="music" items="${musicList}">
                    <option value="${music.creationId}" data-bgm-url="https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4" 
                        <c:if test="${music.creationId == episode.bgmId}">selected</c:if> >
                        ${music.title}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- 선택한 BGM 파일명 표시 -->
        <p>선택한 BGM 파일명: <span id="selectedBgmFileName">없음</span></p>

        <!-- BGM 미리보기 -->
        <div id="bgmPlayerContainer" class="mt-3" style="display: none;">
            <audio id="bgmPlayer" controls>
                <source id="bgmSource" src="" type="audio/mp4">
                Your browser does not support the audio element.
            </audio>
        </div>
    
        <!-- 제목 -->
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="소설 제목을 입력하세요" 
                value="${episode.title}" required>
        </div>

        <!-- 몇화인지 -->
        <div class="form-group">
            <label for="episode">몇 화인지</label>
            <input type="number" class="form-control" id="episode" name="episode" placeholder="몇 화인지 입력하세요" value="${episode.episodeNo}" required>
        </div>

        <!-- 내용 (CKEditor 적용) -->
        <div class="form-group">
            <label for="content">내용</label>
            <textarea class="form-control" id="content" name="content" rows="10" placeholder="소설 내용을 입력하세요" required>${episode.contents}</textarea>
        </div>
        
                <!-- 이미지 삽입 버튼 -->
        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#imageModal">
            텍스트에 이미지 삽입
        </button>

        <!-- 이미지 선택 모달 -->
        <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="imageModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="imageModalLabel">이미지 선택</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <c:forEach var="image" items="${imageList}">
                                <div class="col-md-4 text-center mb-3">
                                    <img src="${image.imageUrl}" alt="${image.filename}" class="img-thumbnail" style="max-width: 100%; cursor: pointer;" onclick="insertImageToEditor('${image.imageUrl}')">
                                    <p>${image.filename != null ? image.filename : '이미지'}</p>
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

        <!-- 버튼들 -->
        <div class="d-flex justify-content-between">
            <!-- 이전 버튼: 클릭 시 cover 페이지로 이동 -->
            <button type="button" class="btn btn-secondary" onclick="goBack()">이전</button>

            <!-- 버튼 그룹: 삭제와 저장을 옆에 배치 -->
			<div class="btn-group">
			    <!-- 삭제 버튼 -->
			    <button id="deleteBtn" class="btn btn-danger" onclick="deleteEpisode(${novelCover.novelId}, ${episodeNo})">삭제</button>
			
			    <!-- 저장 버튼: form의 기본 submit 동작 -->
			    <button type="submit" class="btn btn-primary">저장</button>
			</div>
         </div>
    </form> <!-- 폼 끝 -->
</div>

<script>
    // 이전 버튼 클릭 시 cover.jsp로 이동
    function goBack() {
        var contextPath = "<%= request.getContextPath() %>";
        window.location.href = contextPath + "/cover";
    }

    // 페이지 로드 시 미리보기 설정
    window.onload = function() {
        displaySelectedImage();
        displaySelectedBGM();
    };

    // 이미지 미리보기
    function displaySelectedImage() {
        var select = document.getElementById('imageSelect');
        var selectedOption = select.options[select.selectedIndex];
        var imageUrl = selectedOption.getAttribute('data-image-url');
        var fileName = selectedOption.text;

        // 이미지 미리보기 설정
        var imgPreview = document.getElementById('selectedImagePreview');
        if (imageUrl && imageUrl.trim() !== "") {
            imgPreview.src = imageUrl;
            imgPreview.style.display = 'block';  // 이미지 표시
        } else {
            imgPreview.style.display = 'none';  // 이미지 숨김
        }

        // 선택한 파일명 표시
        document.getElementById('selectedImageFileName').innerText = fileName;
    }

    // BGM 미리보기
    function displaySelectedBGM() {
        var select = document.getElementById('bgmSelect');
        var selectedOption = select.options[select.selectedIndex];
        var bgmUrl = selectedOption.getAttribute('data-bgm-url');
        var fileName = selectedOption.text;

        // 선택한 파일명 표시
        document.getElementById('selectedBgmFileName').innerText = fileName;

        // BGM 미리보기 설정
        var bgmPlayerContainer = document.getElementById('bgmPlayerContainer');
        var bgmPlayer = document.getElementById('bgmPlayer');
        var bgmSource = document.getElementById('bgmSource');
        
        if (bgmUrl && bgmUrl.trim() !== "") {
            bgmSource.src = bgmUrl; // 올바른 오디오 파일 경로가 들어가는지 확인
            bgmPlayerContainer.style.display = 'block';
            bgmPlayer.load(); // 새로운 소스를 로드하여 재생 가능하도록 설정
        } else {
            bgmPlayerContainer.style.display = 'none';
        }
    }

    // CKEditor 적용
    CKEDITOR.replace('content', {
        height: 1000 // 여기서 높이를 픽셀 단위로 설정할 수 있습니다.
    });
    // CKEditor에 이미지 삽입
    function insertImageToEditor(imageUrl) {
        var editor = CKEDITOR.instances.content;
        editor.insertHtml('<img src="' + imageUrl + '" alt="이미지" style="max-width:100%;">');
        $('#imageModal').modal('hide');
    }
    
 // 에피소드 삭제
    function deleteEpisode(novelId, episodeNo) {
        if (confirm('정말 삭제하시겠습니까?')) {
            var contextPath = "<%= request.getContextPath() %>";
            fetch(`${contextPath}/novel/delete-episode/${novelId}/${episodeNo}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    alert('에피소드가 성공적으로 삭제되었습니다.');
                    window.location.href = contextPath + '/storage';  // 삭제 후 목록 페이지로 리다이렉트
                } else {
                    alert('에피소드 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('삭제 중 오류가 발생했습니다.');
            });
        }
    }


</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
