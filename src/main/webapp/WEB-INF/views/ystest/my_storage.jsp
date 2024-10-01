<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- JSTL 추가 -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <!-- JSTL functions 추가 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 보관함</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <div class="d-flex justify-content-between">
        <h2>내 보관함</h2>
        <!-- 글쓰기 버튼 -->
        <a href="<%=request.getContextPath()%>/cover" class="btn btn-primary">+new</a>
    </div>
    
    <!-- 탭 메뉴 -->
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="#all" data-toggle="tab">전체</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#mynovel" data-toggle="tab">내 소설</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#images" data-toggle="tab">내 이미지</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#music" data-toggle="tab">내 음악</a>
        </li>
    </ul>

    <!-- 탭 콘텐츠 -->
    <div class="tab-content mt-4">
        <div class="tab-pane fade show active" id="all">
            <h3>전체</h3>
            <p>전체 항목이 여기에 표시됩니다.</p>
        </div>
        
		<div class="form-group">
		    <label for="novel">내 소설</label>
		    <c:forEach var="novel" items="${novelList}">
		        <!-- 카드 전체를 링크로 감쌈 -->
		        <a href="<%=request.getContextPath()%>/ystest/novel_detail/${novel.novelId}" class="card-link">
		            <div class="card">
		                <div class="card-body">
		                    <p class="card-text">제목: ${novel.title}</p>
		                    <p class="card-text">장르: ${novel.genre}</p>
		                    <p class="card-text">내용: ${novel.intro}</p>
		                    <p class="card-text">작성일: ${novel.createdAt}</p>
		                </div>
		            </div>
		        </a>
		    </c:forEach>
		</div>



        <!-- 내 이미지 및 음악 탭 -->
        <div class="tab-pane fade" id="images">
            <h3>내 이미지</h3>

            <!-- 이미지 리스트를 반복해서 표시 -->
            <div class="row">
                <c:forEach var="image" items="${imageList}">
                    <div class="col-md-4 mb-4">
                        <div class="card">
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
        <div class="tab-pane fade" id="music">
        	<h3>내 음악</h3>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
