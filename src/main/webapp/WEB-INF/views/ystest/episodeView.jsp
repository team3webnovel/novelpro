<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${episode.title} - 소설 보기</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light d-flex justify-content-center align-items-start" style="min-height: 100vh; padding-top: 40px; padding-bottom: 40px;">

<div class="container">
    <div class="card shadow-sm mx-auto" style="max-width: 800px;">
        <div class="card-body">
            <!-- 소설 제목 -->
            <h1 class="card-title text-center">${episode.title}</h1>

            <!-- 작성자 정보와 회차 정보 -->
            <p class="card-text text-center text-muted">${episode.episodeNo}화</p>

            <!-- 소설 이미지 -->
            <c:if test="${episode.imageUrl != null}">
                <div class="text-center my-4">
                    <img src="${episode.imageUrl}" alt="소설 이미지" class="img-fluid rounded">
                </div>
            </c:if>
            
            <!-- BGM 플레이어 -->
            <c:if test="${episode.bgmUrl != null}">
                <div class="bg-light p-3 rounded mt-4 text-center">
                    <h5>배경 음악</h5>
                    <audio controls class="w-100">
                        <source src="https://cdn1.suno.ai/${episode.bgmUrl.split('=')[1]}.mp4" type="audio/mp4">
                        Your browser does not support the audio element.
                    </audio>
                </div>
            </c:if>

            <!-- 소설 내용 -->
            <div class="mt-4 text-center">
                <p>${episode.contents}</p>
            </div>

            <!-- 다시보기 버튼 -->
            <div class="text-center mt-4">
                <button type="button" class="btn btn-primary" onclick="goBack()">목록으로 돌아가기</button>
            </div>
        </div>
    </div>
</div>

<script>
    // 목록으로 돌아가기 함수
    function goBack() {
        var contextPath = "<%= request.getContextPath() %>";
        window.location.href = contextPath + "/novel_detail/${episode.novelId}"; // 소설 목록 페이지로 이동
    }
</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
