<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소설 쓰기</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- CKEditor CDN -->
    <script src="https://cdn.ckeditor.com/4.20.0/full/ckeditor.js"></script>

    <style>
        /* 스타일은 그대로 유지 */
        .btn-custom {
            background-color: #007bff;
            color: #fff;
            border-radius: 50px;
            padding: 10px 20px;
        }
        .btn-custom:hover {
            background-color: #0056b3;
            box-shadow: 0 4px 10px rgba(0, 123, 255, 0.5);
        }
        .preview-container img:hover {
            transform: scale(1.05);
        }
        .bgm-card:hover {
            transform: scale(1.05);
        }
        .preview-container {
            margin-top: 20px;
            text-align: center;
        }
        .preview-container img {
            border-radius: 10px;
            max-width: 100%;
            max-height: 400px;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
        .bgm-card {
            max-width: 100%;
            max-height: 400px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
        .bgm-card img {
            max-height: 200px;
            object-fit: cover;
        }
        .bgm-card audio {
            margin-top: 10px;
            width: 100%;
        }
    </style>
</head>
<body>
    <!-- 헤더 포함 -->
    <header style="margin-bottom: 100px;">
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    </header>

    <div class="container mt-5">
        <h2 class="mb-4">소설 쓰기</h2>

        <form action="<%=request.getContextPath()%>/novel/episode/update/${episode.novelId}/${episode.episodeNo}" method="POST">
            <div class="row">
                <!-- 왼쪽: 표지 이미지 선택 -->
                <div class="col-md-6">
                    <h4>표지 이미지 선택</h4>
                    <button type="button" class="btn btn-custom" data-toggle="modal" data-target="#coverImageModal">
                        표지 이미지 선택
                    </button>

                    <!-- 선택된 이미지 미리보기 -->
                    <div class="preview-container">
                        <img id="selectedCoverImagePreview" src="" alt="선택된 표지 이미지" style="display: none;" />
                        <p>선택한 파일명: <span id="selectedCoverImageFileName">없음</span></p>
                    </div>
                    <input type="hidden" id="selectedCoverImageId" name="illust" value="" />

                    <!-- 표지 이미지 선택 모달 -->
                    <div class="modal fade" id="coverImageModal" tabindex="-1" role="dialog" aria-labelledby="coverImageModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="coverImageModalLabel">표지 이미지 선택</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <c:forEach var="image" items="${imageList}">
                                            <div class="col-md-3 text-center mb-3">
                                                <img src="${image.imageUrl}" alt="${image.filename}" class="img-thumbnail" style="cursor: pointer;" onclick="selectCoverImage('${image.creationId}', '${image.imageUrl}', '${image.filename}')">
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

                <!-- 오른쪽: BGM 선택 -->
                <div class="col-md-6">
                    <h4>BGM 선택</h4>
                    <button type="button" class="btn btn-custom" data-toggle="modal" data-target="#bgmModal">
                        BGM 선택
                    </button>

                    <!-- 선택한 BGM 카드 형태로 미리보기 -->
                    <div class="card text-center mt-3 mx-auto" id="bgmCard" style="display: none; max-width: 330px; max-height: 440px;">
                        <img id="selectedBgmImage" class="card-img-top" src="" alt="선택된 BGM 이미지" style="max-height: 320px; object-fit: cover; width: 100%;" />
                        <div class="card-body" style="padding: 10px;">
                            <h5 class="card-title text-dark">
                                <span id="selectedBgmFileName">선택한 BGM 파일명</span>
                            </h5>
                            <audio id="bgmPlayer" controls class="w-100 mt-2" style="height: 40px;">
                                <source id="bgmSource" src="" type="audio/mp4">
                                Your browser does not support the audio element.
                            </audio>
                        </div>
                    </div>
                    <input type="hidden" id="selectedBgmId" name="bgm" value="" />
                                        <!-- BGM 선택 모달 -->
                    <div class="modal fade" id="bgmModal" tabindex="-1" role="dialog" aria-labelledby="bgmModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="bgmModalLabel">BGM 선택</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <c:forEach var="music" items="${musicList}">
                                            <div class="col-md-3 text-center mb-3">
												<button type="button" class="btn btn-outline-primary" 
												    onclick="selectBGM('${music.creationId}', 'https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4', '${music.title}', '${music.imageUrl}')">
												    ${music.title}
												</button>

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
            </div>

            <!-- 제목, 에피소드, 내용 입력 -->
            <div class="form-group mt-5">
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

            <!-- 버튼들 -->
            <div class="d-flex justify-content-between mt-3">
                <button type="button" class="btn btn-secondary" onclick="goBack()">이전</button>
                <div class="btn-group">
                    <button type="button" class="btn btn-secondary" onclick="saveTemporary()">임시저장</button>
                    <button type="submit" class="btn btn-primary">저장</button>
                </div>
            </div>
        </form>
    </div>

    <script>
        // 페이지 로드 시 BGM 및 표지 이미지가 있는 경우 미리보기에 표시
        <c:if test="${not empty episode.imageId}">
            <c:forEach var="image" items="${imageList}">
                <c:if test="${image.creationId == episode.imageId}">
                    document.getElementById('selectedCoverImagePreview').src = '${image.imageUrl}';
                    document.getElementById('selectedCoverImagePreview').style.display = 'block';
                    document.getElementById('selectedCoverImageFileName').innerText = '${image.filename}';
                    document.getElementById('selectedCoverImageId').value = '${image.creationId}';
                </c:if>
            </c:forEach>
        </c:if>

        <c:if test="${not empty episode.bgmId}">
            <c:forEach var="music" items="${musicList}">
                <c:if test="${music.creationId == episode.bgmId}">
                    document.getElementById('bgmCard').style.display = 'block';
                    document.getElementById('selectedBgmImage').src = '${music.imageUrl}';
                    document.getElementById('selectedBgmFileName').innerText = '${music.title}';
                    document.getElementById('bgmSource').src = 'https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4';
                    document.getElementById('bgmPlayer').load();
                    document.getElementById('selectedBgmId').value = '${music.creationId}';
                </c:if>
            </c:forEach>
        </c:if>

        // 표지 이미지 선택
        function selectCoverImage(creationId, imageUrl, fileName) {
            document.getElementById('selectedCoverImagePreview').src = imageUrl;
            document.getElementById('selectedCoverImagePreview').style.display = 'block';
            document.getElementById('selectedCoverImageFileName').innerText = fileName;
            document.getElementById('selectedCoverImageId').value = creationId;
            $('#coverImageModal').modal('hide');
        }

        // BGM 선택
        function selectBGM(creationId, bgmUrl, fileName, imageUrl) {
            document.getElementById('bgmCard').style.display = 'block';
            document.getElementById('selectedBgmImage').src = imageUrl;
            document.getElementById('selectedBgmFileName').innerText = fileName;
            document.getElementById('bgmSource').src = bgmUrl;
            document.getElementById('bgmPlayer').load();
            document.getElementById('selectedBgmId').value = creationId;
            $('#bgmModal').modal('hide');
        }

        // 기타 기능
        function saveTemporary() {
            alert("임시 저장 기능이 구현될 예정입니다.");
        }

    	function goBack() {
    	    window.history.back();
    	}

        // CKEditor 설정
        CKEDITOR.replace('content', {
            height: 400
        });
    </script>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
