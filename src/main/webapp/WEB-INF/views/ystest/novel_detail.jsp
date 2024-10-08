<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
    <div class="row">
        <!-- 왼쪽: 커버 이미지 -->
        <div class="col-md-4">
            <img src="${novel.cover}" alt="소설 커버" class="img-fluid">
        </div>

        <!-- 오른쪽: 소설 정보 -->
        <div class="col-md-8">
            <h2>${novel.title}</h2>
            <p>작가: ${novel.author}</p>
            <p>장르: ${novel.genre}</p>
            <p>전체 에피소드: ${novel.totalEpisodes}화</p>
            <p>상태: 
                <c:choose>
                    <c:when test="${novel.status == 'ongoing'}">연재중</c:when>
                    <c:when test="${novel.status == 'paused'}">휴재중</c:when>
                    <c:when test="${novel.status == 'completed'}">완결</c:when>
                </c:choose>
            </p>

            <!-- 구분선 -->
            <hr>

            <!-- 에피소드 리스트 -->
            <h3>에피소드 목록</h3>
            <ul class="list-group">
                <c:forEach var="episode" items="${episodeList}">
                    <li class="list-group-item">
                        <a href="/novel/episode/${episode.episodeId}">
                            에피소드 ${episode.episodeNumber}화: ${episode.title}
                        </a>
                        <span class="float-right">작성일: ${episode.createdAt}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
	
</body>
</html>