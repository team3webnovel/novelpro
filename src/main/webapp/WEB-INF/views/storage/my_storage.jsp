<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 보관함</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/my_storage.css">
    <!-- jQuery 전체 버전 로드 -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="static/js/my_storage.js"></script>
<<<<<<< HEAD
</head>
<body>

=======
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />

</head>

<body>
    <div class="main-content">
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center">
        <h2>내 보관함</h2>
        <div class="btn-group">
            <a class="btn btn-studio" id="aiStudioButton">AI 창작 스튜디오</a>
<<<<<<< HEAD
            	<div id="overlay" class="overlay" style="display: none;">
    				<div id="messageBox" class="message-box">
        				<p>당신의 창작을 도와드리겠습니다.</p>
        				<button id="confirmButton">확인</button>
    				</div>
				</div>
            <a href="<%=request.getContextPath()%>/generate-music" class="btn btn-primary">BGM 만들기</a>
            <a href="<%=request.getContextPath()%>/gije/test" class="btn btn-primary">표지 제작</a>
            <a href="<%=request.getContextPath()%>/generate-font" class="btn btn-primary">표지 폰트</a>
            <a href="<%=request.getContextPath()%>/new_novel" class="btn btn-primary">글쓰기</a>
        </div>
    </div>
=======
            <div id="overlay" class="overlay" style="display: none;">
                <div id="messageBox" class="message-box">
                    <p>당신의 창작을 도와드리겠습니다.</p>
                    <button id="confirmButton">확인</button>
                </div>
            </div>
            <a href="<%=request.getContextPath()%>/generate-music" class="btn btn-primary">BGM 만들기</a>
            <a href="<%=request.getContextPath()%>/images" class="btn btn-primary">표지 제작</a>
            <a href="<%=request.getContextPath()%>/generate-font" class="btn btn-primary">표지 폰트</a>
            <a href="<%=request.getContextPath()%>/cover" class="btn btn-primary">글쓰기</a>
        </div>
    </div> 
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645

    <!-- 탭 메뉴 -->
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="#mynovel" data-toggle="tab">내 소설</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#images" data-toggle="tab">내 이미지</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#music" data-toggle="tab">내 음악</a>
        </li>
    </ul>

    <!-- 탭 콘텐츠 -->
    <div class="tab-content">
        <!-- 내 소설 탭 -->
        <div class="tab-pane fade show active" id="mynovel">
            <div class="row">
                <c:forEach var="novel" items="${novelList}">
                    <div class="col-md-4 mb-4">
                        <a href="<%=request.getContextPath()%>/novel_detail/${novel.novelId}" class="card-link">
                            <div class="card h-100">
<<<<<<< HEAD
                            	<img src="${novel.imageUrl}" class="card-img-top" style="max-height: 200px; object-fit: cover;">
=======
                                <img src="${novel.imageUrl}" class="card-img-top" style="max-height: 200px; object-fit: contain;">
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645
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

<<<<<<< HEAD
        <!-- 내 이미지 탭 -->
        <div class="tab-pane fade" id="images">
            <!-- 이미지 리스트를 반복해서 표시 -->
            <div class="row">
                <c:forEach var="image" items="${imageList}">
                    <div class="col-md-4 mb-4">
                        <div class="card" onclick="writeBoard(${image.creationId}, '${image.imageUrl }')">
                            <!-- 이미지 출력 -->
                            <img src="${image.imageUrl}" class="card-img-top" style="max-height: 200px; object-fit: cover;">
                            <div class="card-body">
                                <h5 class="card-title">이미지</h5>
                                <p class="card-text">생성일: ${image.createdAt}</p>
                                <p class="card-text">샘플러: ${image.sampler}</p>
                                <p class="card-text">프롬프트: ${image.prompt}</p>
                                <p class="card-text">모델 체크: ${image.modelCheck}</p>
=======
   <!-- 내 이미지 탭 -->
		<div class="tab-pane fade" id="images">
		    <div class="row">
		        <c:forEach var="image" items="${imageList}">
		            <div class="col-md-3 col-sm-6 mb-2">
		                <div class="card" onclick="writeBoard(${image.creationId}, '${image.imageUrl}'); openImageModal(${image.creationId}, '${image.imageUrl}', '${image.createdAt}', '${image.sampler}', '${image.prompt}', '${image.modelCheck}', '${image.title}')">
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
                            <img src="${music.imageUrl}" alt="Cover Image" class="card-img-top" style="max-height: 200px; object-fit: cover;" onclick="playMusic('${music.creationId}', '${music.audioUrl}')">
                            <div class="card-body">
                                <h5 class="card-title text-dark"><a href="${pageContext.request.contextPath}/music_detail/${music.creationId}" class="text-dark">${music.title}</a></h5>
                                <audio id="audioPlayer${music.creationId}" controls class="w-100 mt-2">
                                    <source id="audioSource${music.creationId}" src="https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4" type="audio/mp4">
                                    Your browser does not support the audio element.
                                </audio>
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

<<<<<<< HEAD
        <!-- 내 음악 탭 -->
        <div class="tab-pane fade" id="music">
    <!-- 음악 리스트를 반복해서 표시 -->
		    <div class="row">
		        <c:forEach var="music" items="${musicList}">
		            <div class="col-md-4 mb-4">
		                <div class="card h-100 text-center">
		                    <img src="${music.imageUrl}" alt="Cover Image" class="card-img-top" style="max-height: 200px; object-fit: cover;" onclick="playMusic('${music.creationId}', '${music.audioUrl}')">
		                    <div class="card-body">
		                        <h5 class="card-title text-dark"><a href="${pageContext.request.contextPath}/music_detail/${music.creationId}" class="text-dark">${music.title}</a></h5>
		                        <audio id="audioPlayer${music.creationId}" controls class="w-100 mt-2">
		                            <source id="audioSource${music.creationId}" src="https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4" type="audio/mp4">
		                            Your browser does not support the audio element.
		                        </audio>
		                    </div>
		                </div>
		            </div>
		        </c:forEach>
		    </div>
		</div>

    </div>
</div>

<!-- 모달 창 -->
=======
    </div>
</div>

<!-- 이미지 정보 모달 -->
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">이미지 정보</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <!-- 이미지 출력 -->
        <img id="modalImage" src="" alt="이미지" class="img-fluid" />
<<<<<<< HEAD
        <input type="hidden" id="creationIdField" value="">

        <!-- 공개 여부 선택 -->
        <div class="form-group">
          <label for="publicOption">생성 정보 공개 여부:</label>
          <select id="publicOption" class="form-control">
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
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
        <button type="button" class="btn btn-primary" onclick="submitData()">전송</button>
=======

        <!-- 숨겨진 creationId 필드 -->
        <input type="hidden" id="creationIdField" value="" />

        <!-- 제목 표시/수정 -->
        <div class="d-flex justify-content-between align-items-center">
          <p id="modalTitle" class="mb-2"></p>
          <button type="button" class="btn btn-secondary btn-sm" id="editButton" onclick="enableEditTitle()">수정</button>
		
          <!-- 확인과 취소 버튼 (처음에는 숨김 처리) -->
          <div id="editControls" style="display: none;">
            <button type="button" class="btn btn-primary btn-sm" onclick="saveTitle()">확인</button>
            <button type="button" class="btn btn-secondary btn-sm" onclick="cancelEditTitle()">취소</button>
          </div>
        </div>

        <p id="modalCreatedAt"></p>
        <p id="modalSampler"></p>
        <p id="modalPrompt"></p>
        <p id="modalModelCheck"></p>

        <!-- 공개 여부 선택 및 코멘트 입력 (처음에는 숨김 처리) -->
        <div id="submitSection" style="display: none;">
          <div class="form-group">
            <label for="publicOption">생성 정보 공개 여부:</label>
            <select id="publicOption" class="form-control">
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
        <button type="button" class="btn btn-primary" id="postButton" onclick="enablePost()">게시판 올리기</button>
		
		<!-- 이미지 삭제 버튼 -->
   		<button type="button" class="btn btn-danger" id="deleteButton" onclick="deleteImage()">삭제</button>
		
        <!-- 확인과 취소 버튼 (처음에는 숨김 처리) -->
        <div id="postControls" style="display: none;">
          <button type="button" class="btn btn-primary" onclick="submitData()">확인</button>
          <button type="button" class="btn btn-secondary" onclick="cancelPost()">취소</button>
        </div>
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645
      </div>
    </div>
  </div>
</div>

<<<<<<< HEAD
<!-- 음악 정보 모달 -->
<div class="modal fade" id="musicModal" tabindex="-1" role="dialog" aria-labelledby="musicModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="musicModalLabel">음악 정보</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- 음악 정보를 여기서 표시 -->
                <img id="modalMusicImage" src="" alt="Cover Image" class="img-fluid" />
                <h5 id="modalMusicTitle" class="mt-3"></h5>
                <p id="modalMusicPrompt"></p>
                <audio id="modalMusicAudio" controls class="w-100 mt-3">
                    <source id="modalAudioSource" src="" type="audio/mp4">
                    Your browser does not support the audio element.
                </audio>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    // JSP에서 request.getContextPath()로 컨텍스트 경로 가져와서 JavaScript 변수로 할당
    var contextPath = "<%= request.getContextPath() %>";

    var currentPlayingItem = null;

    function playMusic(itemId, audioUrl) {
        var audioPlayer = document.getElementById('audioPlayer' + itemId);
        var audioSource = document.getElementById('audioSource' + itemId);
        var playButton = document.getElementById('playButton' + itemId);

        // 오디오가 이미 로드된 상태라면 다른 음악 재생 중지
        if (currentPlayingItem && currentPlayingItem !== itemId) {
            var currentAudioPlayer = document.getElementById('audioPlayer' + currentPlayingItem);
            currentAudioPlayer.pause();
            document.getElementById('musicItem' + currentPlayingItem).classList.remove('playing');
        }

        // 음악 재생 및 일시정지 기능
        if (audioPlayer.paused) {
            audioPlayer.play();
            document.getElementById('musicItem' + itemId).classList.add('playing');
            currentPlayingItem = itemId;
        } else {
            audioPlayer.pause();
            document.getElementById('musicItem' + itemId).classList.remove('playing');
            currentPlayingItem = null;
        }

        // 음악이 끝나면 재생 중 표시 제거
        audioPlayer.onended = function() {
            document.getElementById('musicItem' + itemId).classList.remove('playing');
            currentPlayingItem = null;
        };
    }
    
    
</script>

=======
<script type="text/javascript">
    var contextPath = "<%= request.getContextPath() %>";
    var originalTitle = '';  // 제목 저장 변수

    // 이미지 모달을 열고 creationId 값을 설정
    function openImageModal(creationId, imageUrl, createdAt, sampler, prompt, modelCheck, title) {
        document.getElementById('modalImage').src = imageUrl;
        document.getElementById('modalCreatedAt').innerText = '생성일: ' + createdAt;
        document.getElementById('modalSampler').innerText = '샘플러: ' + sampler;
        document.getElementById('modalPrompt').innerText = '프롬프트: ' + prompt;
        document.getElementById('modalModelCheck').innerText = '모델 체크: ' + modelCheck;

        // 이미지 제목 설정
        document.getElementById('modalTitle').innerText = '제목: ' + title;
        originalTitle = title;  // 원래 제목 저장

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

    function saveTitle() {
        var editableTitle = document.getElementById('editableTitle');
        var newTitle = editableTitle.value;
        var creationId = document.getElementById('creationIdField').value;

        // 서버로 수정된 제목을 전송 (AJAX)
        $.ajax({
            url: contextPath + '/update-image-title',
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

    // 게시판 올리기 버튼 눌렀을 때 공개 여부 및 코멘트 입력 필드 보이기
    function enablePost() {
        document.getElementById('submitSection').style.display = 'block';

        // 게시판 올리기 버튼 숨기고 확인, 취소 버튼 표시
        document.getElementById('postButton').style.display = 'none';
        document.getElementById('postControls').style.display = 'inline-block';
    }



    // 게시판 올리기 취소
    function cancelPost() {
        // 공개 여부와 코멘트 입력 필드를 숨기고 게시판 올리기 버튼을 다시 표시
        document.getElementById('submitSection').style.display = 'none';
        document.getElementById('postButton').style.display = 'inline-block';
        document.getElementById('postControls').style.display = 'none';
    }

    // 모달이 닫힐 때 초기화
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
                url: contextPath + '/delete-image',
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
</script>
</div>
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645
</body>
</html>
