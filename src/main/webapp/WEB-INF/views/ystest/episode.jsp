<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소설 쓰기</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- CKEditor CDN 추가 -->
    <script src="https://cdn.ckeditor.com/4.20.0/full/ckeditor.js"></script>
</head>
<body>

<div class="container mt-5">
    <h2>소설 쓰기</h2>
    
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

        <!-- 회차 표시 -->
        <div class="form-group">
            <label for="episode">몇 화인지</label>
            <p id="episodeDisplay" class="form-control-plaintext">${episode.episodeNo}화</p>
        </div>

        <!-- 내용 (CKEditor 적용) -->
        <div class="form-group">
            <label for="content">내용</label>
            <textarea class="form-control" id="content" name="content" rows="10" placeholder="소설 내용을 입력하세요" required>${episode.contents}</textarea>
        </div>

        <!-- 버튼들 -->
        <div class="d-flex justify-content-between">
            <!-- 이전 버튼: 클릭 시 cover 페이지로 이동 -->
            <button type="button" class="btn btn-secondary" onclick="goBack()">이전</button>

            <!-- 버튼 그룹: 임시저장과 저장을 옆에 배치 -->
            <div class="btn-group">
                <button type="button" class="btn btn-secondary" onclick="saveTemporary()">임시저장</button>
                <button type="submit" class="btn btn-primary">저장</button>
            </div>
         </div>
    </form> <!-- 폼 끝 -->
</div>

<script>
    function saveTemporary() {
        alert("임시 저장 기능이 구현될 예정입니다.");
        // 이 부분에 실제 임시 저장 로직을 추가할 수 있습니다.
    }

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
    CKEDITOR.replace('content'); // textarea 요소에 CKEditor 적용
</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
