<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소설 상세 페이지</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<!-- 내 보관함 섹션 추가 -->
<div class="container mt-5">
    <div class="d-flex justify-content-between">
        <h2>${novelCover.title}</h2>
        <!-- 글쓰기 버튼 -->
        <a href="<%=request.getContextPath()%>/write/${novelCover.novelId}" class="btn btn-primary">+new</a>
    </div>
</div>

<!-- 기존 소설 상세 페이지 내용 -->
<div class="container mt-4">
    <div class="row">
    
        <!-- 왼쪽: 커버 이미지 -->
        <div class="col-md-4">
            <img src="${novelCover.imageUrl}" alt="소설 커버" class="img-fluid">
        </div>

        <!-- 오른쪽: 소설 정보 -->
        <div class="col-md-8">
            <%-- <h2>${novelCover.title}</h2> --%>
            <p>작가: 작가명</p>
            <p>장르: ${novel.genre}</p>
            <p>전체 에피소드: n화</p>
            <p>상태: 구현예정
                <%-- <c:choose>
                    <c:when test="${novel.status == 'ongoing'}">연재중</c:when>
                    <c:when test="${novel.status == 'paused'}">휴재중</c:when>
                    <c:when test="${novel.status == 'completed'}">완결</c:when>
                </c:choose> --%>
            </p>

            <!-- 구분선 -->
            <hr>

            <!-- 에피소드 리스트 -->
            <h3>에피소드 목록</h3>
            <ul class="list-group">
                <%-- <c:forEach var="episode" items="${episodeList}">
                    <li class="list-group-item">
                        <a href="/novel/episode/${episode.episodeId}">
                            에피소드 ${episode.episodeNumber}화: ${episode.title}
                        </a>
                        <span class="float-right">작성일: ${episode.createdAt}</span>
                    </li>
                </c:forEach> --%>
            </ul>
        </div>
    </div>
</div>

<!-- jQuery와 Bootstrap JavaScript -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
