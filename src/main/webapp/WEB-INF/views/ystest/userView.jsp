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

<!-- 내 보관함 섹션 추가 -->
<div class="container mt-5">
    <div class="d-flex justify-content-between">
        <h2>${novelCover.title}</h2>
        <!-- 글쓰기 버튼 -->
        <a href="<%=request.getContextPath()%>/write/${novelCover.novelId}" class="btn btn-primary">회차 쓰기</a>
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
            <p>작가: 작가명</p>
            <p>장르: ${novelCover.genre}</p>
            <p>전체 에피소드: n화</p>

            <!-- 상태 표시 및 변경 -->
            <p>상태:
                <!-- 상태 선택 드롭다운 -->
                <select id="statusSelect" onchange="updateStatus()">
                    <option value="ongoing" ${novelCover.status == 'ongoing' ? 'selected' : ''}>연재중</option>
                    <option value="paused" ${novelCover.status == 'paused' ? 'selected' : ''}>휴재중</option>
                    <option value="completed" ${novelCover.status == 'completed' ? 'selected' : ''}>완결</option>
                    <option value="" ${novelCover.status == null ? 'selected' : ''}>미등록</option>
                </select>
            </p>

            <!-- 구분선 -->
            <hr>

            <!-- 에피소드 리스트 -->
            <h3>에피소드 목록</h3>
            <ul class="list-group">
                <c:forEach var="episode" items="${detailList}">
                    <li class="list-group-item">
                        <img src="${episode.imageUrl}" alt="소설 커버" class="img-fluid">
                        <a href="<%=request.getContextPath()%>/novel/episode/${novelCover.novelId}/${episode.episodeNo}">
                            에피소드 ${episode.episodeNo}화: ${episode.title}
                        </a>
                        
                        <!-- 공개/비공개 선택 드롭다운 -->
                        <select class="float-right" id="visibilitySelect_${episode.episodeNo}" onchange="updateVisibility(${episode.episodeNo})">
                            <option value="public" ${episode.visibility == 'public' ? 'selected' : ''}>공개</option>
                            <option value="private" ${episode.visibility == 'private' ? 'selected' : ''}>비공개</option>
                        </select>
                        <span class="float-right">작성일: ${episode.createdAt}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<!-- JavaScript 부분 -->
<script>
    // 상태 변경시 서버로 AJAX 요청을 보내는 함수
    function updateStatus() {
        var selectedStatus = document.getElementById('statusSelect').value;
        var novelId = "${novelCover.novelId}";  // 소설 ID 가져오기
    
        // AJAX 요청으로 상태 변경
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "<%=request.getContextPath()%>/updateStatus", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    
        // 데이터 전송 (응답 처리는 생략)
        xhr.send("novelId=" + novelId + "&status=" + selectedStatus);
    }

    // 에피소드 공개/비공개 상태 변경시 서버로 AJAX 요청을 보내는 함수
    function updateVisibility(episodeNo) {
        var selectedVisibility = document.getElementById('visibilitySelect_' + episodeNo).value;
        var novelId = "${novelCover.novelId}";  // 소설 ID 가져오기

        // AJAX 요청으로 상태 변경
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "<%=request.getContextPath()%>/updateVisibility", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // 데이터 전송
        xhr.send("novelId=" + novelId + "&episodeNo=" + episodeNo + "&visibility=" + selectedVisibility);
    }
</script>

<!-- jQuery와 Bootstrap JavaScript -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
