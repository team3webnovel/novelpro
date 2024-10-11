<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소설 쓰기</title>
    <!-- CSS 파일 경로 수정 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/new_novel.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <!-- contextPath를 JavaScript에서 사용할 수 있도록 변수로 설정 -->
    <script type="text/javascript">
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <!-- 조건부로 novel_tutorial.js 로드 -->
<c:if test="${not empty AImessage}">
        <script src="${pageContext.request.contextPath}/static/js/ai_studio/novel_tutorial.js"></script>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/ai_studio/novel_tutorial.css">
</c:if> 
</head>
<body>
    <!-- 헤더 포함, 인라인 스타일로 간격 추가 -->
    <header style="margin-bottom: 100px;">
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    </header>
<div class="container mt-5">
    <h2>소설 쓰기</h2>
    
    <div class="row">
        <!-- 왼쪽 폼: 소설 제목 및 줄거리 입력 -->
        <div class="col-md-6">
            <!-- 폼 action 경로 수정 -->
<<<<<<< HEAD
            <form action="${pageContext.request.contextPath}/novel/new-novel" method="POST">
=======
            <form action="${pageContext.request.contextPath}/new-novel" method="POST">
>>>>>>> ad35e6932a891afff47269c0781107bc78955957
                <!-- 제목 입력 -->
                <div class="form-group">
                    <label for="title">소설 제목</label>
                    <input type="text" class="form-control" id="title" name="title" placeholder="소설 제목을 입력하세요" required>
                </div>

                <!-- 장르 선택 -->
                <div class="form-group">
                    <label for="genre">장르</label>
                    <select class="form-control" id="genre" name="genre" required>
                        <option value="">장르 선택</option>
                        <option value="로판">로맨스판타지</option>
                        <option value="현판">현대판타지</option>
                        <option value="판타지">판타지</option>
                        <option value="무협">무협</option>
                        <option value="로맨스">로맨스</option>
                        <option value="일반">일반</option>
                    </select>
                </div>

                <!-- 이미지 선택 -->
                <div class="form-group">
                    <label for="imageSelect">소설 표지 이미지 선택</label>
                    <select id="imageSelect" class="form-control" name="illust" onchange="displaySelectedImage()">
                        <option value="">이미지를 선택하세요</option>
                        <c:forEach var="image" items="${imageList}">
                            <option value="${image.creationId}" data-image-url="${image.imageUrl}">
                                ${image.filename != null ? image.filename : image.imageUrl}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 선택한 이미지 미리보기 -->
                <div class="mt-3">
                    <img id="selectedImagePreview" src="" alt="선택된 이미지가 여기에 표시됩니다" style="max-width: 100%; height: auto; display: none;" />
                </div>

                <!-- 선택한 이미지 파일명 표시 -->
                <p>선택한 파일명: <span id="selectedImageFileName">없음</span></p>

                <!-- 줄거리 입력 -->
                <div class="form-group mt-4">
                    <label for="intro">소설 줄거리</label>
                    <textarea class="form-control" id="intro" name="intro" rows="6" placeholder="소설 줄거리를 입력하세요" required></textarea>
                </div>

                <!-- 저장 버튼 -->
                <div class="d-flex justify-content-between mt-4">
                    <button type="submit" class="btn btn-primary">소설 저장</button>
                </div>
            </form>
        </div>

        <!-- gpt를 통한 줄거리 및 등장인물 생성 -->
        <div class="col-md-6">
    <h4>줄거리&등장인물</h4>
    
    <!-- 채팅 로그 영역 -->
    <div id="chat-container" class="chat-window-container" style="position: relative;">
        <div id="chat-log" class="chat-window border p-3" style="height: 400px; overflow-y: scroll;">
            <!-- 채팅 로그 표시 영역 -->
        </div>

        <!-- 사용자 입력 -->
        <div id="input-area" class="mt-3 d-flex input-area-sticky">
            <input type="text" id="user-input" placeholder="메시지를 입력하세요" class="form-control">
            <button id="submit-btn" type="button" class="btn btn-primary ml-2">입력</button>
        </div>
    </div>
</div>

<!-- JavaScript 파일 링크 -->
<!-- JS 파일 경로 수정 -->
<script src="${pageContext.request.contextPath}/static/js/chatbot.js"></script>
<!-- jQuery의 slim 버전이 아닌 일반 버전 사용 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
