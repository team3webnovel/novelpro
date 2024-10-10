<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소설 상세 페이지</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<!-- 내 보관함 섹션 -->
<div class="container mt-5">
    <div class="d-flex justify-content-between">
        <h2>${novelCover.title}</h2>
    </div>
</div>

<!-- 소설 상세 페이지 내용 -->
<div class="container mt-4">
    <div class="row">
        <!-- 왼쪽: 커버 이미지 및 인트로 -->
        <div class="col-md-4 d-flex flex-column align-items-center">
            <img src="${novelCover.imageUrl}" alt="소설 커버" class="img-fluid mb-4" style="max-width: 100%; height: auto;">
            <div class="text-center">
                <h2>인트로</h2>
                <p>${novelCover.intro}</p>
            </div>
        </div>

        <!-- 오른쪽: 소설 정보 -->
        <div class="col-md-8">
            <p>작가: ${novelCover.userName}</p>
            <p>장르: ${novelCover.genre}</p>
            <p>전체 에피소드: ${novelCover.episodeNo != null ? novelCover.episodeNo : 0}</p>

            <!-- 상태 표시 -->
            <p><strong>상태:</strong> 
                <span>
                    ${novelCover.status == 'ongoing' ? '연재중' : 
                      novelCover.status == 'paused' ? '휴재중' : 
                      novelCover.status == 'completed' ? '완결' : '미등록'}
                </span>
            </p>

            <!-- 구분선 -->
            <hr>

            <!-- 에피소드 리스트 -->
            <h3>에피소드 목록</h3>
            <ul class="list-group">
                <c:forEach var="episode" items="${detailList}">
                    <li class="list-group-item d-flex align-items-center">
                        <img src="${episode.imageUrl}" alt="소설 커버" class="img-fluid mr-3" style="width: 80px; height: 80px; object-fit: cover;">
                        <div class="flex-grow-1">
                            <a href="<%=request.getContextPath()%>/novel/episode/${novelCover.novelId}/${episode.episodeNo}">
                                에피소드 ${episode.episodeNo}화: ${episode.title}
                            </a>
                        </div>
                        <span class="text-muted">작성일: ${episode.createdAt}</span>
                    </li>
                </c:forEach>
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
