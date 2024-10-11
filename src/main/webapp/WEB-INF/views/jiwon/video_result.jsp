<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Image Generation Result</title>

    <!-- header.jsp를 포함 -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />

    <!-- 필요한 경우에만 video_result.css 포함 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/video/video_result.css">
</head>
<body>
    <div id="resultSection">
        <h1>Image Generation Result</h1>

        <% 
            String imageUrl = (String) session.getAttribute("videoUrl");  // 기존 videoUrl 대신 imageUrl로 사용
            Boolean imageGenerated = (Boolean) session.getAttribute("videoGenerated");  // 기존 videoGenerated 그대로 사용

            if (imageGenerated != null && imageGenerated) {
        %>
            <p class="success-message">Image generation was successful! You can view your image below:</p>
            <img src="<%= imageUrl %>" alt="Generated Image" class="generated-image" />
            
            <!-- 보관함으로 이동 버튼 -->
            <p><a href="${pageContext.request.contextPath}/storage" class="storage-btn">Go to My Storage</a></p>

        <% 
                session.removeAttribute("videoGenerated");  // 세션에서 제거
                session.removeAttribute("videoUrl");
            } else {
        %>
            <p class="loading-message">Image generation is still in progress. Please check back later.</p>
            <div class="loader"></div>
            <script>
                setTimeout(function () {
                    window.location.reload();
                }, 5000); // 5초마다 페이지를 새로고침
            </script>
        <% } %>
    </div>
</body>
</html>
