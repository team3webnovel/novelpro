<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${episode.title} - 소설 보기</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

</head>
<body class="bg-light d-flex justify-content-center align-items-start" style="min-height: 100vh; padding-top: 40px; padding-bottom: 40px;">
    <!-- 헤더 포함, 인라인 스타일로 간격 추가 -->
    <header style="margin-bottom: 100px;">
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    </header>
<div class="container">
    <div class="card shadow-sm mx-auto" style="max-width: 800px;">
        <div class="card-body">
            <!-- 소설 제목 -->
            <h1 class="card-title text-center">${episode.title}</h1>
			<c:if test="${not empty episode.bgmUrl}">
			    <audio id="audioPlayer" controls class="w-100 mt-2">
			        <source src="https://cdn1.suno.ai/${episode.bgmUrl.split('=')[1]}.mp4" type="audio/mp4">
			    </audio>
			</c:if>

            <!-- 작성자 정보와 회차 정보 -->
            <p class="card-text text-center text-muted">${episode.episodeNo}화</p>

            <!-- 소설 이미지 (이미지에 상단 마진 추가) -->
            <c:if test="${episode.imageUrl != null}">
                <div class="text-center my-4">
                    <img src="${episode.imageUrl}" alt="소설 이미지" class="img-fluid rounded" style="margin-bottom: 20px;">
                </div>
            </c:if>

            <!-- 소설 내용 -->
            <div class="mt-4 text-left" style="white-space: pre-wrap; line-height: 1.6; font-size: 1.25rem;">
                <c:out value="${episode.contents}" escapeXml="false" />
            </div>

            <!-- 이전/다음 화 버튼 -->
            <div class="d-flex justify-content-between mt-4">
                <!-- 이전 화 버튼 -->
                <button id="prevBtn" type="button" class="btn btn-secondary" style="visibility: hidden;">이전 화</button>
                
                <!-- 다음 화 버튼 -->
                <button id="nextBtn" type="button" class="btn btn-secondary" style="visibility: hidden;">다음 화</button>
            </div>
        </div>
    </div>
</div>

<script>
    const currentEpisode = ${episode.episodeNo};
    const maxEpisode = ${maxEpisode};
    const novelId = ${episode.novelId};

    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');

    // 이전 화 버튼 제어
    if (currentEpisode > 1) {
        prevBtn.style.visibility = 'visible';
        prevBtn.onclick = function() {
            window.location.href = `<%= request.getContextPath() %>/novel/episodeview/${novelId}/${episode.episodeNo - 1}`;
        };
    }

    // 다음 화 버튼 제어
    if (currentEpisode < maxEpisode) {
        nextBtn.style.visibility = 'visible';
        nextBtn.onclick = function() {
            window.location.href = `<%= request.getContextPath() %>/novel/episodeview/${novelId}/${episode.episodeNo + 1}`;
        };
    }
</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
