<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 보관함</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="<%= request.getContextPath()%>/static/js/storage.js"></script>
</head>

<body>
<div class="container mt-5">
	<div class="d-flex justify-content-between">
		<h2>내 보관함</h2>
		<!-- 글쓰기 버튼 -->
		<a href="<%=request.getContextPath()%>/cover" class="btn btn-primary">소설 쓰기</a>
	</div>

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
					<div class="col mb-3">
    					<a href="<%=request.getContextPath()%>/novel_detail/${novel.novelId}" class="card-link">
       						<div class="card h-100">
       							<img src="${novel.imageUrl}" class="card-img-top" style="max-height: 200px; object-fit: cover;">
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
			<!-- 이미지 리스트를 반복해서 표시 -->
			<div class="row">
			    <c:forEach var="image" items="${imageList}">
			        <!-- col-md-3를 사용하여 한 행에 4개의 열 배치 -->
			        <div class="col mb-4">
			            <div class="card" onclick="writeBoard(${image.creationId}, '${image.imageUrl}')">
			                <!-- 이미지 출력 -->
			                <img src="${image.imageUrl}" class="card-img-top" style="max-height: 200px; object-fit: cover;">
			                <div class="card-body">
			                    <h5 class="card-title">이미지</h5>
			                    <p class="card-text">생성일: ${image.createdAt}</p>
			                    <p class="card-text">샘플러: ${image.sampler}</p>
			                    <p class="card-text">프롬프트: ${image.prompt}</p>
			                    <p class="card-text">모델 체크: ${image.modelCheck}</p>
			                </div>
			            </div>
			        </div>
			    </c:forEach>
			</div>
		</div>



		<!-- 내 음악 탭 -->
		<div class="tab-pane fade" id="music">
		</div>
	</div>
</div>

<!-- 모달 창 -->
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
	  </div>
	</div>
  </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
