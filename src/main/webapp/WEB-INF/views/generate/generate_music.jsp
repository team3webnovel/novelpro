<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Creator</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/generate/generate_music.css">
    
    <c:if test="${not empty AImessage }">
    	<script src="static/js/ai_studio/music_tutorial.js"></script>
    </c:if>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <style>
        #loadingIndicator p {
            font-size: 1.2rem;
            color: #007bff;
        }

        #loadingSpinner {
            border: 4px solid #f3f3f3;
            border-top: 4px solid #007bff;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin: 0 auto;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body>

<!-- Music Creator Container -->
<div id="musicCreatorContainer" class="mt-5">
    <form action="<%= request.getContextPath() %>/generate-music" method="post" id="musicForm">
        <h1 id="musicCreatorTitle" class="text-center">당신만의 BGM을 만들어보세요!</h1>

        <div id="formInputGroup">
            <label for="prompt">어떤 BGM이 만들어지면 좋을까요?</label>
            <input type="text" class="form-control" id="prompt" name="prompt" required>
        </div>

        <div id="instrumentalOption" class="form-check mb-3">
            <input type="checkbox" class="form-check-input" id="make_instrumental" name="make_instrumental">
            <label class="form-check-label" for="make_instrumental">가사 없이?</label>
        </div>

        <button id="submitMusic" type="submit" class="btn btn-primary btn-block">BGM 생성</button>

        <!-- 로딩 메시지 -->
        <div id="loadingIndicator" style="display:none;" class="text-center mt-3">
            <div id="loadingSpinner"></div>
            <p>음악을 생성 중입니다. 잠시만 기다려주세요...</p>
        </div>

        <!-- 에러 메시지 표시 -->
        <div id="errorMessage" style="display: none;" class="alert alert-danger mt-3">
            <strong>Error:</strong> ${errorMessage}
        </div>

        <!-- 경고 메시지 표시 -->
        <div id="warningMessage" style="display: none;" class="alert alert-warning mt-3">
            <strong>Warning:</strong> ${warningMessage}
        </div>
    </form>

    <!-- 음악 보관함으로 이동하는 링크 추가 -->
    <p id="musicStorageLink" class="text-center mt-3">
        <a href="<%= request.getContextPath() %>/storage#music">음악 보관함으로 이동</a>
    </p>

</div>

    <!-- AImessage가 있을 경우 숨겨진 필드 추가 -->
    <c:if test="${not empty AImessage }">
        <input type="hidden" id="AImessage" value="${AImessage}">
    </c:if>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const AImessageElement = document.getElementById('AImessage');
    const form = document.getElementById('musicForm');

    // 폼 제출 시 숨겨진 필드로 AImessage 추가
    form.addEventListener('submit', function(event) {
        if (AImessageElement) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'AImessage';  // 폼으로 전송될 필드 이름
            input.value = AImessageElement.value;
            form.appendChild(input);
        }
    });

    // 해시 값이 '#music'이면 '내 음악' div를 활성화
    const hash = window.location.hash;
    if (hash === "#music") {
        document.querySelectorAll('.tab-pane').forEach(function(tabContent) {
            tabContent.classList.remove('show', 'active');
        });
        const musicTabContent = document.querySelector('#music');
        if (musicTabContent) {
            musicTabContent.classList.add('show', 'active');
        }
        document.querySelectorAll('.nav-link').forEach(function(tabLink) {
            tabLink.classList.remove('active');
        });
        const musicTabLink = document.querySelector('a[href="#music"]');
        if (musicTabLink) {
            musicTabLink.classList.add('active');
        }
    }

    // 에러 메시지 표시
    if ('${errorMessage}' !== '') {
        document.getElementById('errorMessage').style.display = 'block';
    }
    if ('${warningMessage}' !== '') {
        document.getElementById('warningMessage').style.display = 'block';
    }
});
</script>

</body>
</html>
