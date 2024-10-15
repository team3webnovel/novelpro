<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내 보관함</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="static/css/my_storage.css">
<!-- jQuery 전체 버전 로드 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="static/js/my_storage.js"></script>
<jsp:include page="/WEB-INF/views/includes/header.jsp" />

<link rel="stylesheet" href="https://cdn.plyr.io/3.6.8/plyr.css" />
<script src="https://cdn.plyr.io/3.6.8/plyr.polyfilled.js"></script>


</head>

<body>
	<div class="main-content">
		<div class="container mt-5">
			<div class="d-flex justify-content-between align-items-center">
				<h2>내 보관함</h2>
				<div class="btn-group">
					<a class="btn btn-studio" id="aiStudioButton">AI 창작 스튜디오</a>
					<div id="overlay" class="overlay" style="display: none;">
						<div id="messageBox" class="message-box">
							<p>당신의 창작을 도와드리겠습니다.</p>
							<button id="confirmButton">확인</button>
						</div>
					</div>

					<a href="<%=request.getContextPath()%>/generate-music"
						class="btn btn-primary">BGM 만들기</a> <a
						href="<%=request.getContextPath()%>/images"
						class="btn btn-primary">표지 제작</a> <a
						href="<%=request.getContextPath()%>/generate-font"
						class="btn btn-primary">표지 폰트</a> <a
						href="<%=request.getContextPath()%>/novel/new-novel"
						class="btn btn-primary">글쓰기</a> <a
						href="<%=request.getContextPath()%>/videos/video"
						class="btn btn-primary">비디오 제작(임시)</a>

				</div>
			</div>

			<!-- 탭 메뉴 -->
			<ul class="nav nav-tabs">
				<li class="nav-item"><a class="nav-link active" href="#mynovel"
					data-toggle="tab">내 소설</a></li>
				<li class="nav-item"><a class="nav-link" href="#images"
					data-toggle="tab">내 이미지</a></li>
				<li class="nav-item"><a class="nav-link" href="#music"
					data-toggle="tab">내 음악</a></li>
				<li class="nav-item"><a class="nav-link" href="#video"
					data-toggle="tab">내 비디오</a></li>
			</ul>

			<!-- 탭 콘텐츠 -->
			<div class="tab-content">
				<!-- 내 소설 탭 -->
				<div class="tab-pane fade show active" id="mynovel">
					<div class="row">
						<c:forEach var="novel" items="${novelList}">
							<div class="col-md-4 mb-4">
								<a
									href="<%=request.getContextPath()%>/novel/novel-detail/${novel.novelId}"
									class="card-link">
									<div class="card h-100">
						                <c:choose>
						                    <c:when test="${empty novel.imageUrl}">
						                        <img src="<%= request.getContextPath() %>/static/images/logo.png" alt="logo" class="card-img-top" style="max-height: 200px; object-fit: contain;">
						                    </c:when>
						                    <c:otherwise>
						                        <img src="${novel.imageUrl}" class="card-img-top" style="max-height: 200px; object-fit: contain;">
						                    </c:otherwise>
						                </c:choose>
										<div class="card-body">
											<h5 class="card-title">제목: ${novel.title}</h5>
											<p class="card-text">장르: ${novel.genre}</p>
											<p class="card-text">
											    내용: 
											    <c:choose>
											        <c:when test="${fn:length(novel.intro) > 15}">
											            ${fn:substring(novel.intro, 0, 15)}...
											        </c:when>
											        <c:otherwise>
											            ${novel.intro}
											        </c:otherwise>
											    </c:choose>
											</p>
											<p class="card-text">작성일: ${novel.createdAt}</p>
										</div>
									</div>
								</a>
							</div>
						</c:forEach>
					</div>
				</div>

				<!-- 내 이미지 탭 -->
				<div class="tab-pane fade" id="images">
					<div class="row">
						<c:forEach var="image" items="${imageList}">
							<div class="col-md-3 col-sm-6 mb-2">
								<div class="card" 
								     data-creation-id="${image.creationId}" 
								     data-image-url="${fn:escapeXml(image.imageUrl)}" 
								     data-created-at="${fn:escapeXml(image.createdAt)}" 
								     data-sampler="${fn:escapeXml(image.sampler)}"
								     data-prompt="${fn:escapeXml(image.prompt)}" 
								     data-model-check="${fn:escapeXml(image.modelCheck)}" 
								     data-title="${fn:escapeXml(image.title)}"
								     onclick="handleCardClick(this)">
								    <img src="${image.imageUrl}" class="card-img-top">
								</div>
							</div>
						</c:forEach>
					</div>
				</div>

				<!-- 내 음악 탭 -->
				<div class="tab-pane fade" id="music">
					<div class="row">
						<c:forEach var="music" items="${musicList}">
							<div class="col-md-4 mb-4">
								<div class="card h-100 text-center">
									<img src="${music.imageUrl}" alt="Cover Image"
										class="card-img-top"
										style="max-height: 200px; object-fit: cover;"
										onclick="playMusic('${music.creationId}', '${music.audioUrl}')">
									<div class="card-body">
										<h5 class="card-title text-dark">
											<a
												href="${pageContext.request.contextPath}/music_detail/${music.creationId}"
												class="text-dark">${music.title}</a>
										</h5>
										<audio id="audioPlayer${music.creationId}" controls
											class="plyr">
											<source id="audioSource${music.creationId}"
												src="https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4"
												type="audio/mp4">
											Your browser does not support the audio element.
										</audio>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>

				<!-- 내 비디오 탭 -->
				<div class="tab-pane fade" id="video">
					<!-- 비디오 리스트를 반복해서 표시 -->
					<div class="row">
						<c:forEach var="video" items="${videoList}">
							<div class="col-md-4 mb-4">
								<div class="card"
									onclick="openVideoModal(${video.creationId}, '${video.videoUrl}', '${video.createdAt}', '${video.sampler}', '${video.modelCheck}', '${video.title}')">
									<!-- 비디오 썸네일 출력 -->
									<img src="${video.videoUrl}" class="card-img-top"
										style="max-height: 200px; object-fit: cover;">
									<div class="card-body">
										<h5 class="card-title">${video.title}</h5>
										<p class="card-text">생성일: ${video.createdAt}</p>
										<p class="card-text">샘플러: ${video.sampler}</p>
										<p class="card-text">모델 체크: ${video.modelCheck}</p>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>

			</div>
		</div>

		<!-- 이미지 정보 모달 -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">이미지 정보</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<!-- 이미지 출력 -->
						<img id="modalImage" src="" alt="이미지" class="img-fluid" />

						<!-- 숨겨진 creationId 필드 -->
						<input type="hidden" id="creationIdField" value="" />

						<!-- 제목 표시/수정 -->
						<div class="d-flex justify-content-between align-items-center">
							<p id="modalTitle" class="mb-2"></p>
							<button type="button" class="btn btn-secondary btn-sm"
								id="editButton" onclick="enableEditTitle()">이름 바꾸기</button>

							<!-- 확인과 취소 버튼 (처음에는 숨김 처리) -->
							<div id="editControls" style="display: none;">
								<button type="button" class="btn btn-primary btn-sm"
									onclick="saveTitle()">확인</button>
								<button type="button" class="btn btn-secondary btn-sm"
									onclick="cancelEditTitle()">취소</button>
							</div>
						</div>

						<!-- 프롬프트 내용 표시 (기본적으로 일부만 표시) -->
						<p id="modalPromptShort"
							style="margin-bottom: 10px; display: inline;"></p>
						<span id="togglePromptButtonContainer" style="display: inline;">
							<button type="button" id="togglePromptButton"
								onclick="togglePrompt()"
								style="font-size: 0.875rem; padding: 0; border: none; background: none; color: #007bff;">자세히
								보기</button>
						</span>

						<!-- 전체 프롬프트 내용 -->
						<p id="modalPromptFull"
							style="display: none; margin-bottom: 10px; display: inline;"></p>
						<span id="togglePromptButtonContainerFull"
							style="display: none; display: inline;">
							<button type="button" id="togglePromptButtonFull"
								onclick="togglePrompt()"
								style="font-size: 0.875rem; padding: 0; border: none; background: none; color: #007bff;">간단히
								보기</button>
						</span>

						<!-- 생성일 표시 -->
						<p id="modalCreatedAt" style="margin-top: 10px;"></p>
						<p id="modalSampler"></p>
						<p id="modalModelCheck"></p>

						<!-- 공개 여부 선택 및 코멘트 입력 (처음에는 숨김 처리) -->
						<div id="submitSection" style="display: none;">
							<div class="form-group">
								<label for="publicOption">생성 정보 공개 여부:</label> <select
									id="publicOption" class="form-control">
									<option value="public">공개</option>
									<option value="private">비공개</option>
								</select>
							</div>

							<!-- 코멘트 입력 -->
							<div class="form-group">
								<label for="comment">작성자 코멘트:</label>
								<textarea id="comment" class="form-control" rows="4"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<!-- 게시판 올리기 버튼 -->
						<button type="button" class="btn btn-primary" id="postButton"
							onclick="enablePost()">게시판 올리기</button>

						<!-- 이미지 삭제 버튼 -->
						<button type="button" class="btn btn-danger" id="deleteButton"
							onclick="deleteImage()">삭제</button>

						<!-- 확인과 취소 버튼 (처음에는 숨김 처리) -->
						<div id="postControls" style="display: none;">
							<button type="button" class="btn btn-primary"
								onclick="submitData()">확인</button>
							<button type="button" class="btn btn-secondary"
								onclick="cancelPost()">취소</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 비디오 정보 모달 -->
		<div class="modal fade" id="videoModal" tabindex="-1" role="dialog"
			aria-labelledby="videoModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="videoModalLabel">비디오 정보</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<!-- 비디오 썸네일 이미지 출력 -->
						<img id="modalVideo" class="img-fluid" alt="비디오 썸네일">

						<!-- 숨겨진 creationId 필드 -->
						<input type="hidden" id="videoCreationIdField" />

						<!-- 제목 표시/수정 -->
						<div
							class="d-flex justify-content-between align-items-center mt-3">
							<p id="videoModalTitle" class="mb-2"></p>
							<input type="hidden" class="btn btn-secondary btn-sm"
								id="editVideoButton" onclick="enableEditVideoTitle()">
							<div id="editVideoControls" style="display: none;">
								<input type="button" class="btn btn-primary btn-sm"
									onclick="saveVideoTitle()">
								<input type="button" class="btn btn-secondary btn-sm"
									onclick="cancelEditVideoTitle()">
							</div>
						</div>

						<!-- 생성일 표시 -->
						<p id="videoModalCreatedAt" style="margin-top: 10px;"></p>
						<p id="videoModalSampler"></p>
						<p id="videoModalModelCheck"></p>

						<!-- 공개 여부 선택 및 코멘트 입력 -->
						<div id="videoSubmitSection" style="display: none;">
							<div class="form-group">
								<label for="videoPublicOption">생성 정보 공개 여부:</label> <select
									id="videoPublicOption" class="form-control">
									<option value="public">공개</option>
									<option value="private">비공개</option>
								</select>
							</div>

							<!-- 코멘트 입력 -->
							<div class="form-group">
								<label for="videoComment">작성자 코멘트:</label>
								<textarea id="videoComment" class="form-control" rows="4"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<!-- 게시판 올리기 버튼 -->
						<input type="hidden" class="btn btn-primary" id="postVideoButton"
							onclick="enableVideoPost()">

						<!-- 비디오 삭제 버튼 -->
						<button type="button" class="btn btn-danger"
							id="deleteVideoButton" onclick="deleteVideo()">삭제</button>

						<!-- 확인과 취소 버튼 -->
						<div id="postVideoControls" style="display: none;">
							<button type="button" class="btn btn-primary"
								onclick="submitVideoData()">확인</button>
							<button type="button" class="btn btn-secondary"
								onclick="cancelVideoPost()">취소</button>
						</div>
					</div>
				</div>
			</div>
		</div>


		<script type="text/javascript">
    var contextPath = "<%=request.getContextPath()%>";
    var originalTitle = '';  // 제목 저장 변수
 // Plyr 초기화 코드
    document.addEventListener('DOMContentLoaded', () => {
        const players = Plyr.setup('.plyr'); // .plyr 클래스를 가진 모든 오디오 및 비디오 태그에 적용
    });
</script>
		<script type="text/javascript">
		    var contextPath = "<%=request.getContextPath()%>";
		    var originalTitle = '';  // 제목 저장 변수

		    function handleCardClick(cardElement) {
		        const creationId = cardElement.getAttribute('data-creation-id');
		        const imageUrl = cardElement.getAttribute('data-image-url');
		        const createdAt = cardElement.getAttribute('data-created-at');
		        const sampler = cardElement.getAttribute('data-sampler');
		        const prompt = cardElement.getAttribute('data-prompt');
		        const modelCheck = cardElement.getAttribute('data-model-check');
		        const title = cardElement.getAttribute('data-title');

		        openImageModal(creationId, imageUrl, createdAt, sampler, prompt, modelCheck, title);
		    }

		 // 모달이 열릴 때 제목과 creationId 값을 설정하는 부분
			function openImageModal(creationId, imageUrl, createdAt, sampler, prompt, modelCheck, title) {
			    console.log('모달 열기 호출:', creationId, imageUrl, createdAt, sampler, prompt, modelCheck, title);
			
			    // 모달을 열 때 프롬프트 초기화
			    document.getElementById('modalPromptShort').style.display = 'inline'; // 요약된 프롬프트만 표시
			    document.getElementById('togglePromptButtonContainer').style.display = 'inline'; // "자세히 보기" 버튼 표시
			    document.getElementById('modalPromptFull').style.display = 'none'; // 전체 프롬프트는 숨김
			    document.getElementById('togglePromptButtonContainerFull').style.display = 'none'; // "간단히 보기" 버튼 숨김
			
			    // 이미지 및 정보 설정
			    document.getElementById('modalImage').src = imageUrl;
			    document.getElementById('modalCreatedAt').innerText = '생성일: ' + createdAt;
			    document.getElementById('modalSampler').innerText = '샘플러: ' + sampler;
			
			    // 프롬프트 내용 설정
			    const shortPrompt = prompt.length > 50 ? '프롬프트: ' + prompt.substring(0, 50) + '...' : '프롬프트: ' + prompt;
			    document.getElementById('modalPromptShort').innerText = shortPrompt;
			    document.getElementById('modalPromptFull').innerText = '프롬프트: ' + prompt;
			
			    // 제목 설정
			    document.getElementById('modalTitle').innerText = '제목: ' + title;
			
			    // originalTitle에 원래 제목 저장
			    originalTitle = title;
			
			    // creationId 숨겨진 필드에 저장
			    document.getElementById('creationIdField').value = creationId;
			  
			    // 모달 띄우기
			    $('#myModal').modal('show');
			}


		    // 제목 수정 가능하게 변경
		    function enableEditTitle() {
		        var titleElement = document.getElementById('modalTitle');
		        var currentTitle = titleElement.innerText.replace('제목: ', '');

		        // 제목을 인풋 필드로 변경
		        titleElement.innerHTML = '<input type="text" id="editableTitle" class="form-control" value="' + currentTitle + '" />';

		        // 수정 버튼 숨기고 확인, 취소 버튼 표시
		        document.getElementById('editButton').style.display = 'none';
		        document.getElementById('editControls').style.display = 'inline-block';
		    }

		    // 제목 수정 저장
		    function saveTitle() {
		        var editableTitle = document.getElementById('editableTitle');
		        var newTitle = editableTitle.value;
		        var creationId = document.getElementById('creationIdField').value;

		        // 서버로 수정된 제목을 전송 (AJAX)
		        $.ajax({
		            url: contextPath + '/novel/update-image-title',
		            method: 'POST',
		            contentType: 'application/json',
		            data: JSON.stringify({
		                creationId: parseInt(creationId),  // creationId는 숫자로 변환
		                title: newTitle                    // title 필드로 전송
		            }),
		            success: function(response) {
		                if (response.success) {
		                    // 성공 시 리다이렉트
		                    window.location.href = contextPath + '/storage';
		                } else {
		                    alert('제목 수정에 실패했습니다. 다시 시도해주세요.');
		                }
		            },
		            error: function() {
		                alert('오류가 발생했습니다. 나중에 다시 시도해주세요.');
		            }
		        });
		    }

		    // 제목 수정 취소
		    function cancelEditTitle() {
		        // 제목을 원래 상태로 복구
		        document.getElementById('modalTitle').innerText = '제목: ' + originalTitle;

		        // 확인, 취소 버튼 숨기고 수정 버튼 다시 표시
		        document.getElementById('editButton').style.display = 'inline-block';
		        document.getElementById('editControls').style.display = 'none';
		    }

		    // 모달 닫힐 때 초기화
		    $('#myModal').on('hide.bs.modal', function () {
		        // 제목을 원래 상태로 초기화
		        document.getElementById('modalTitle').innerText = '제목: ' + originalTitle;

		        // 수정 버튼 표시, 확인과 취소 버튼 숨김
		        document.getElementById('editButton').style.display = 'inline-block';
		        document.getElementById('editControls').style.display = 'none';

		        // 공개 여부 선택과 코멘트 입력 필드 숨기기
		        document.getElementById('submitSection').style.display = 'none';
		        document.getElementById('postButton').style.display = 'inline-block';
		        document.getElementById('postControls').style.display = 'none';

		        // 공개 여부 기본값 설정 및 코멘트 입력 초기화
		        document.getElementById('publicOption').value = 'public';
		        document.getElementById('comment').value = '';
		    });


		    function writeBoard(creationId, imageUrl) {
		        console.log("게시판에 이미지 올리기: " + creationId + ", " + imageUrl);
		    }
		    
		    function deleteImage() {
		        var creationId = document.getElementById('creationIdField').value;

		        if (confirm('정말 이 이미지를 삭제하시겠습니까?')) {
		            // 서버로 삭제 요청을 전송 (AJAX)
		            $.ajax({
		                url: contextPath + '/novel/delete-image',
		                method: 'POST',
		                contentType: 'application/json',
		                data: JSON.stringify({
		                    creationId: parseInt(creationId) // 삭제할 이미지의 creationId 전송
		                }),
		                success: function(response) {
		                    if (response.success) {
		                        // 성공 시 리다이렉트
		                        alert('이미지가 성공적으로 삭제되었습니다.');
		                        window.location.href = contextPath + '/storage';
		                    } else {
		                        alert('이미지 삭제에 실패했습니다. 다시 시도해주세요.');
		                    }
		                },
		                error: function() {
		                    alert('오류가 발생했습니다. 나중에 다시 시도해주세요.');
		                }
		            });
		        }
		    }

		    function togglePrompt() {
		        const shortPrompt = document.getElementById("modalPromptShort");
		        const fullPrompt = document.getElementById("modalPromptFull");
		        const toggleButtonContainer = document.getElementById("togglePromptButtonContainer");
		        const toggleButtonContainerFull = document.getElementById("togglePromptButtonContainerFull");

		        // 토글 로직: 전체 내용과 짧은 내용을 번갈아 보여줌
		        if (fullPrompt.style.display === "none") {
		            fullPrompt.style.display = "inline";  // 전체 프롬프트 표시
		            shortPrompt.style.display = "none";   // 요약 프롬프트 숨김
		            toggleButtonContainer.style.display = "none"; // "자세히 보기" 숨김
		            toggleButtonContainerFull.style.display = "inline"; // "간단히 보기" 버튼 표시
		        } else {
		            fullPrompt.style.display = "none";    // 전체 프롬프트 숨김
		            shortPrompt.style.display = "inline"; // 요약 프롬프트 표시
		            toggleButtonContainer.style.display = "inline"; // "자세히 보기" 버튼 표시
		            toggleButtonContainerFull.style.display = "none";  // "간단히 보기" 숨김
		        }
		    }

		    var contextPath = "<%=request.getContextPath()%>";
		    var originalVideoTitle = '';  // 비디오 제목 저장 변수

		 // 비디오 모달을 열고 creationId 값을 설정
		   function openVideoModal(creationId, videoUrl, createdAt, sampler, modelCheck, title) {
    			// 비디오 썸네일을 이미지로 설정
    			var videoElement = document.getElementById('modalVideo');  // img 태그
    			videoElement.src = videoUrl;  // 비디오 썸네일 이미지로 설정
    
    			// 비디오 정보 설정
   		 		document.getElementById('videoModalCreatedAt').innerText = '생성일: ' + createdAt;
    			document.getElementById('videoModalSampler').innerText = '샘플러: ' + sampler;
    			document.getElementById('videoModalModelCheck').innerText = '모델 체크: ' + modelCheck;
    			document.getElementById('videoModalTitle').innerText = '제목: ' + title;

    			// 로그 추가
    		    console.log('videoCreationIdField 존재 여부:', document.getElementById('videoCreationIdField'));
    			
    			// 숨겨진 creationId 필드에 저장
    			document.getElementById('videoCreationIdField').value = creationId;

    			// 모달 띄우기
    			$('#videoModal').modal('show');
			}

		    
		    // 비디오 제목 수정 가능하게 변경
		    function enableEditVideoTitle() {
		        var titleElement = document.getElementById('videoModalTitle');
		        var currentTitle = titleElement.innerText.replace('제목: ', '');

		        // 제목을 인풋 필드로 변경
		        titleElement.innerHTML = '<input type="text" id="editableVideoTitle" class="form-control" value="' + currentTitle + '" />';

		        // 수정 버튼 숨기고 확인, 취소 버튼 표시
		        document.getElementById('editVideoButton').style.display = 'none';
		        document.getElementById('editVideoControls').style.display = 'inline-block';
		    }

		    // 비디오 제목 저장
		    function saveVideoTitle() {
		        var editableTitle = document.getElementById('editableVideoTitle');
		        var newTitle = editableTitle.value;
		        var creationId = document.getElementById('videoCreationIdField').value;

		        // 서버로 수정된 제목 전송 (AJAX)
		        $.ajax({
		            url: contextPath + '/videos/update-video-title',
		            method: 'POST',
		            contentType: 'application/json',
		            data: JSON.stringify({
		                creationId: parseInt(creationId),
		                title: newTitle
		            }),
		            success: function(response) {
		                if (response.success) {
		                    window.location.href = contextPath + '/storage';
		                } else {
		                    alert('제목 수정에 실패했습니다. 다시 시도해주세요.');
		                }
		            },
		            error: function() {
		                alert('오류가 발생했습니다. 나중에 다시 시도해주세요.');
		            }
		        });
		    }

		    // 비디오 제목 수정 취소
		    function cancelEditVideoTitle() {
		        // 제목을 원래 상태로 복구
		        document.getElementById('videoModalTitle').innerText = '제목: ' + originalVideoTitle;

		        // 수정 버튼 숨기고 확인, 취소 버튼 표시
		        document.getElementById('editVideoButton').style.display = 'inline-block';
		        document.getElementById('editVideoControls').style.display = 'none';
		    }

		    // 게시판 올리기 버튼 눌렀을 때 공개 여부 및 코멘트 입력 필드 보이기
		    function enableVideoPost() {
		        document.getElementById('videoSubmitSection').style.display = 'block';

		        // 게시판 올리기 버튼 숨기고 확인, 취소 버튼 표시
		        document.getElementById('postVideoButton').style.display = 'none';
		        document.getElementById('postVideoControls').style.display = 'inline-block';
		    }

		    // 게시판 올리기 취소
		    function cancelVideoPost() {
		        // 공개 여부와 코멘트 입력 필드를 숨기고 게시판 올리기 버튼을 다시 표시
		        document.getElementById('videoSubmitSection').style.display = 'none';
		        document.getElementById('postVideoButton').style.display = 'inline-block';
		        document.getElementById('postVideoControls').style.display = 'none';
		    }

		    // 모달이 닫힐 때 초기화
		    $('#videoModal').on('hide.bs.modal', function () {
		        // 제목을 원래 상태로 초기화
		        document.getElementById('videoModalTitle').innerText = '제목: ' + originalVideoTitle;

		        // 수정 버튼 표시, 확인과 취소 버튼 숨김
		        document.getElementById('editVideoButton').style.display = 'inline-block';
		        document.getElementById('editVideoControls').style.display = 'none';

		        // 공개 여부 선택과 코멘트 입력 필드 숨기기
		        document.getElementById('videoSubmitSection').style.display = 'none';
		        document.getElementById('postVideoButton').style.display = 'inline-block';
		        document.getElementById('postVideoControls').style.display = 'none';

		        // 공개 여부 기본값 설정 및 코멘트 입력 초기화
		        document.getElementById('videoPublicOption').value = 'public';
		        document.getElementById('videoComment').value = '';
		    });

		    // 비디오 삭제
		    function deleteVideo() {
		        var creationId = document.getElementById('videoCreationIdField').value;

		        if (confirm('정말 이 비디오를 삭제하시겠습니까?')) {
		            // 서버로 삭제 요청 전송 (AJAX)
		            $.ajax({
		                url: contextPath + '/novel/delete-video',
		                method: 'POST',
		                contentType: 'application/json',
		                data: JSON.stringify({
		                    creationId: parseInt(creationId)
		                }),
		                success: function(response) {
		                    if (response.success) {
		                        alert('비디오가 성공적으로 삭제되었습니다.');
		                        window.location.href = contextPath + '/storage';
		                    } else {
		                        alert('비디오 삭제에 실패했습니다. 다시 시도해주세요.');
		                    }
		                },
		                error: function() {
		                    alert('오류가 발생했습니다. 나중에 다시 시도해주세요.');
		                }
		            });
		        }
		    }
		    

		</script>
	</div>
</body>
</html>