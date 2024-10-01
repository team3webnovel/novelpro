<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- JSTL 추가 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 보관함</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="<%= request.getContextPath()%>/static/js/storage.js"></script>
    <style>
	.modal {
	    display: none; /* 기본적으로 숨김 */
	    position: fixed;
	    z-index: 1;
	    left: 0;
	    top: 0;
	    width: 100%;
	    height: 100%;
	    overflow: auto;
	    background-color: rgb(0, 0, 0);
	    background-color: rgba(0, 0, 0, 0.4);
	}
	
	.close {
	    color: #aaa;
	    float: right;
	    font-size: 28px;
	    font-weight: bold;
	}
	
	.close:hover,
	.close:focus {
	    color: black;
	    text-decoration: none;
	    cursor: pointer;
	}
	
	.modal-content {
	    background-color: #fefefe;
	    margin: 15% auto;
	    padding: 20px;
	    border: 1px solid #888;
	    width: 80%;
	    border-radius: 8px; /* 테두리 둥글게 */
	    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
	    max-height: 90vh; /* 화면 높이의 90%로 최대 높이를 설정하여 화면 밖으로 넘치지 않도록 함 */
	  	overflow-y: auto; /* 세로로 스크롤 가능 */
	}
    </style>
</head>
<body>

<div class="container mt-5">
    <div class="d-flex justify-content-between">
        <h2>내 보관함</h2>
        <!-- 글쓰기 버튼 -->
        <a href="<%= request.getContextPath()%>/write" class="btn btn-primary">글쓰기</a>
    </div>
    
    <!-- 탭 메뉴 -->
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="#all" data-toggle="tab">전체</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#playground" data-toggle="tab">내 소설</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#images" data-toggle="tab">내 이미지랑 음악</a>
        </li>
    </ul>

    <!-- 탭 콘텐츠 -->
    <div class="tab-content mt-4">
        <div class="tab-pane fade show active" id="all">
            <h3>전체</h3>
            <p>전체 항목이 여기에 표시됩니다.</p>
        </div>
        <div class="tab-pane fade" id="playground">
            <h3>내소설</h3>
            <p>내소설에 대한 내용이 여기에 표시됩니다.</p>
        </div>
        <div class="tab-pane fade" id="images">
            <h3>이미지 및 음악</h3>

            <!-- 이미지 리스트를 반복해서 표시 -->
            <div class="row">
                <c:forEach var="image" items="${imageList}">
                    <div class="col-md-4 mb-4">
                        <div class="card" onclick="writeBoard(${image.creationId}, '${image.imageUrl }')">
                            <!-- 이미지 출력 -->
                            <img src="${image.imageUrl}" class="card-img-top" style="max-height: 200px; object-fit: cover;">
                            <div class="card-body">
                                <h5 class="card-title">이미지</h5>
                                <p class="card-text">제목: ${image.title }</p>
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
    </div>
</div>
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
