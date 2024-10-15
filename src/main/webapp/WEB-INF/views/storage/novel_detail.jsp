<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <!-- 헤더 포함, 인라인 스타일로 간격 추가 -->
    <header style="margin-bottom: 100px;">
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    </header>

    <!-- 내 보관함 섹션 추가 -->
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center">
            <h2>${novelCover.title}</h2>
            <div>
                <!-- 글쓰기 버튼 -->
                <a href="<%=request.getContextPath()%>/novel/write/${novelCover.novelId}" class="btn btn-primary">회차 쓰기</a>
                <!-- 편집 버튼 (수정 및 삭제) -->
                <a href="<%=request.getContextPath()%>/novel/edit-new-novel/${novelCover.novelId}" class="btn btn-secondary">편집</a>
            </div>
        </div>
    </div>

    <!-- 기존 소설 상세 페이지 내용 -->
    <div class="container mt-4">
        <div class="row">
            <!-- 왼쪽: 커버 이미지 -->
            <div class="col-md-4">
                <c:choose>
                    <c:when test="${empty novelCover.imageUrl}">
                        <img src="<%= request.getContextPath() %>/static/images/logo.png" alt="소설 커버" class="img-fluid">
                    </c:when>
                    <c:otherwise>
                        <img src="${novelCover.imageUrl}" alt="소설 커버" class="img-fluid">
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- 오른쪽: 소설 정보 -->
            <div class="col-md-8">
                <p>작가: ${novelCover.userName}</p>
                <p>장르: ${novelCover.genre}</p>
                <p>전체 에피소드: ${novelCover.episodeNo != null ? novelCover.episodeNo : 0}</p>

                <!-- 상태 표시 및 변경 -->
                <p>상태:
                    <select id="statusSelect" onchange="updateStatus()" class="form-control d-inline-block w-auto">
                        <option value="ongoing" ${novelCover.status == 'ongoing' ? 'selected' : ''}>연재중</option>
                        <option value="paused" ${novelCover.status == 'paused' ? 'selected' : ''}>휴재중</option>
                        <option value="completed" ${novelCover.status == 'completed' ? 'selected' : ''}>완결</option>
                        <option value="" ${novelCover.status == null ? 'selected' : ''}>미등록</option>
                    </select>
                </p>


	        <!-- 인트로 내용: 상태 밑에 고정 -->
	        <p>${novelCover.intro}</p>
	
	        <!-- 구분선: 커버와 에피소드 목록 사이 -->
	        <hr class="w-100">


                <!-- 에피소드 리스트: 커버 밑에 표시 -->
                <h3>에피소드 목록</h3>
                <ul class="list-group">
                    <c:forEach var="episode" items="${detailList}">
                        <li class="list-group-item d-flex align-items-center">
                            <!-- 에피소드 이미지 -->
                            <img src="${episode.imageUrl}" alt="소설 커버" class="img-fluid"
                                 style="width: 80px; height: 80px; object-fit: cover;"
                                 onerror="this.onerror=null; this.src='${novelCover.imageUrl}'; this.alt='대체 소설 커버 이미지';">

                            <!-- 에피소드 제목 및 링크 -->
                            <div class="flex-grow-1 ml-3">
                                <a href="<%=request.getContextPath()%>/novel/episodeview/${novelCover.novelId}/${episode.episodeNo}">
                                    에피소드 ${episode.episodeNo}화: ${episode.title}
                                </a>
                                <br>
                                <a href="<%=request.getContextPath()%>/novel/episode/${novelCover.novelId}/${episode.episodeNo}" class="text-muted">
                                    편집
                                </a>
                            </div>

                            <!-- 드롭다운 및 작성일 -->
                            <div class="ml-auto text-right">
                                <select id="visibilitySelect_${episode.episodeNo}" onchange="updateVisibility(${episode.episodeNo})" class="form-control d-inline-block w-auto">
                                    <option value="public" ${episode.visibility == 'public' ? 'selected' : ''}>공개</option>
                                    <option value="private" ${episode.visibility == 'private' ? 'selected' : ''}>비공개</option>
                                </select>
                                <small class="d-block mt-2">작성일: ${episode.createdAt}</small>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>

    <!-- JavaScript 부분 -->
    <script>
        function updateStatus() {
            var selectedStatus = document.getElementById('statusSelect').value;
            var novelId = "${novelCover.novelId}";
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "<%=request.getContextPath()%>/novel/updateStatus", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send("novelId=" + novelId + "&status=" + selectedStatus);
        }

        function updateVisibility(episodeNo) {
            var selectedVisibility = document.getElementById('visibilitySelect_' + episodeNo).value;
            var novelId = "${novelCover.novelId}";
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "<%=request.getContextPath()%>/novel/updateVisibility", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send("novelId=" + novelId + "&episodeNo=" + episodeNo + "&visibility=" + selectedVisibility);
        }
    </script>

    <!-- jQuery와 Bootstrap JavaScript -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
