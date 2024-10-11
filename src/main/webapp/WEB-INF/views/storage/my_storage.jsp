<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
						href="<%=request.getContextPath()%>/cover" class="btn btn-primary">글쓰기</a>
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
									href="<%=request.getContextPath()%>/novel_detail/${novel.novelId}"
									class="card-link">
									<div class="card h-100">
										<img src="${novel.imageUrl}" class="card-img-top"
											style="max-height: 200px; object-fit: contain;">
										<div class="card-body">
											<h5 class="card-title">제목: ${novel.title}</h5>
											<p class="card-text">장르: ${novel.genre}</p>
											<p class="card-text">내용: ${novel.intro}</p>
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
									onclick="writeBoard(${image.creationId}, '${image.imageUrl}'); openImageModal(${image.creationId}, '${image.imageUrl}', '${image.createdAt}', '${image.sampler}', '${image.prompt}', '${image.modelCheck}', '${image.title}')">
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
											class="w-100 mt-2">
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
									onclick="writeBoard(${video.creationId}, '${video.videoUrl}')">
									<!-- 비디오 썸네일 출력 -->
									<img src="${video.videoUrl}" class="card-img-top"
										style="max-height: 200px; object-fit: cover;">
									<div class="card-body">
										<h5 class="card-title">비디오</h5>
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
								id="editButton" onclick="enableEditTitle()">수정</button>

							<!-- 확인과 취소 버튼 (처음에는 숨김 처리) -->
							<div id="editControls" style="display: none;">
								<button type="button" class="btn btn-primary btn-sm"
									onclick="saveTitle()">확인</button>
								<button type="button" class="btn btn-secondary btn-sm"
									onclick="cancelEditTitle()">취소</button>
							</div>
						</div>

						<p id="modalCreatedAt"></p>
						<p id="modalSampler"></p>
						<p id="modalPrompt"></p>
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

<script type="text/javascript">
    var contextPath = "<%=request.getContextPath()%>";
    var originalTitle = '';  // 제목 저장 변수
</script>
	</div>
</body>
</html>
