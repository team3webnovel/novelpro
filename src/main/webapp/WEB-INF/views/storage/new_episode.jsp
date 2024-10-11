<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소설 쓰기</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- CKEditor CDN -->
    <script src="https://cdn.ckeditor.com/4.20.0/full/ckeditor.js"></script>

    <!-- 추가 스타일 -->
	<style>
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
		    transform: scale(1.05); /* 이미지 hover 시 약간 확대 */
		}
		
		.bgm-card:hover {
		    transform: scale(1.05); /* BGM 카드 hover 시 약간 확대 */
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
	        margin-right: auto; /* 이미지 좌측 중앙 배치 */
	    }
	
	    .preview-container p {
	        margin-top: 10px;
	        font-weight: bold;
	        color: #555;
	        text-align: center;
	    }
	
	    /* BGM 카드 스타일 */
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
	
	    /* BGM 플레이어 중앙 배치 */
	    .bgm-preview-container {
	        margin-top: 20px;
	        text-align: center;
	    }
	
	    .bgm-preview-container audio {
	        display: block;
	        margin-left: auto;
	        margin-right: auto; /* 음원 플레이어 우측 중앙 배치 */
	    }
	
	    /* 모달 이미지 선택 시 시각적으로 강조 */
	    .img-thumbnail:hover {
	        border: 2px solid #007bff;
	        box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
	    }
	</style>

</head>
<body>
    <!-- 헤더 포함, 인라인 스타일로 간격 추가 -->
    <header style="margin-bottom: 100px;">
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    </header>

    <div class="container mt-5">
        <h2 class="mb-4">소설 쓰기</h2>
    
        <!-- 폼 시작 -->
        <form action="<%=request.getContextPath()%>/novel/write/${novelId}" method="POST">
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

                    <!-- 선택한 이미지 ID를 폼에 숨긴 필드로 전송 -->
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
                                        <!-- 이미지를 선택할 수 있는 모달 -->
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
				    <div class="form-group">
				        <button type="button" class="btn btn-custom" data-toggle="modal" data-target="#bgmModal">
				            BGM 선택
				        </button>
				    </div>
				
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


<<<<<<< HEAD

				
				    <!-- 선택한 BGM ID를 폼에 숨긴 필드로 전송 -->
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
                                                <button class="btn btn-outline-primary" onclick="selectBGM('${music.creationId}', 'https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4', '${music.title}', '${music.imageUrl}')">
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

            <!-- 제목 입력 -->
            <div class="form-group mt-5">
                <label for="title">제목</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="소설 제목을 입력하세요" required>
            </div>

            <!-- 몇 화인지 입력 -->
            <div class="form-group">
                <label for="episode">몇 화인지</label>
                <input type="number" class="form-control" id="episode" name="episode" placeholder="몇 화인지 입력하세요" required>
            </div>

            <!-- 내용 입력 (CKEditor 적용) -->
            <div class="form-group">
                <label for="content">내용</label>
                <textarea class="form-control" id="content" name="content" rows="10" placeholder="소설 내용을 입력하세요" required></textarea>
            </div>

            <!-- 이미지 삽입 버튼 -->
            <button type="button" class="btn btn-info mb-3" data-toggle="modal" data-target="#imageModal">
                텍스트에 이미지 삽입
            </button>

            <!-- 이미지 선택 모달 -->
            <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="imageModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
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
                                    <div class="col-md-2 text-center mb-3">
                                        <img src="${image.imageUrl}" alt="${image.filename}" class="img-thumbnail" style="cursor: pointer;" onclick="insertImageToEditor('${image.imageUrl}')">
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
            <div class="d-flex justify-content-between mt-3">
                <!-- 이전 버튼: 클릭 시 cover 페이지로 이동 -->
                <button type="button" class="btn btn-secondary" onclick="goBack()">이전</button>

                <!-- 임시저장과 저장 버튼 -->
                <div class="btn-group">
                    <button type="button" class="btn btn-secondary" onclick="saveTemporary()">임시저장</button>
                    <button type="submit" class="btn btn-primary">저장</button>
                </div>
            </div>
        </form> <!-- 폼 끝 -->
    </div>

<script>
    // 표지 이미지 선택 시 호출되는 함수
    function selectCoverImage(creationId, imageUrl, fileName) {
        var imgPreview = document.getElementById('selectedCoverImagePreview');
        imgPreview.src = imageUrl;
        imgPreview.style.display = 'block';  // 이미지 표시

        document.getElementById('selectedCoverImageFileName').innerText = fileName;
        document.getElementById('selectedCoverImageId').value = creationId;
        $('#coverImageModal').modal('hide');
    }

 // BGM 선택 시 호출되는 함수
    function selectBGM(creationId, bgmUrl, fileName, imageUrl) {
        // BGM 카드 형태로 미리보기 표시
        var bgmCard = document.getElementById('bgmCard');
        var bgmImage = document.getElementById('selectedBgmImage');
        var bgmPlayerContainer = document.getElementById('bgmPlayerContainer');
        var bgmSource = document.getElementById('bgmSource');

        // 이미지 설정
        bgmImage.src = imageUrl;
        bgmCard.style.display = 'block';

        // BGM 파일명 표시
        document.getElementById('selectedBgmFileName').innerText = fileName;

        // 음원 미리보기 설정
        bgmSource.src = bgmUrl;
        document.getElementById('bgmPlayer').load();

        // BGM ID 설정
        document.getElementById('selectedBgmId').value = creationId;

        // 모달 닫기
        $('#bgmModal').modal('hide');
    }



    // CKEditor 적용
    CKEDITOR.replace('content', {
        height: 400
    });

    // CKEditor에 이미지 삽입
    function insertImageToEditor(imageUrl) {
        var editor = CKEDITOR.instances.content;
        editor.insertHtml('<img src="' + imageUrl + '" alt="이미지" style="max-width:100%;">');
        $('#imageModal').modal('hide');
    }

    // 임시 저장 버튼 기능
    function saveTemporary() {
        alert("임시 저장 기능이 구현될 예정입니다.");
    }
=======
        <!-- 버튼들 -->
        <div class="d-flex justify-content-between mt-3">
            <!-- 이전 버튼: 클릭 시 cover 페이지로 이동 -->
            <button type="button" class="btn btn-secondary" onclick="goBack()">이전</button>
            <!-- 버튼 -->
            <div class="btn-group">
                <button type="submit" class="btn btn-primary">저장</button>
            </div>
            </div>
    	</form> <!-- 폼 끝 -->
</div>

<script>
>>>>>>> ad35e6932a891afff47269c0781107bc78955957

    // 이전 버튼 클릭 시 cover.jsp로 이동
    function goBack() {
        var contextPath = "<%= request.getContextPath() %>";
        window.location.href = contextPath + "/cover";
    }
</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>