<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Image Generation Result</title>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</head>
<body>
    <h1>Image Generation Result</h1>

    <div id="resultSection">
        <% 
            String imageUrl = (String) session.getAttribute("videoUrl");  // 기존 videoUrl 대신 imageUrl로 사용
            Boolean imageGenerated = (Boolean) session.getAttribute("videoGenerated");  // 기존 videoGenerated 그대로 사용

            if (imageGenerated != null && imageGenerated) {
        %>
            <p>Image generation was successful! You can view your image below:</p>
            <img src="<%= imageUrl %>" alt="Generated Image" style="max-width: 100%;" />
            <p><a href="<%= imageUrl %>" download>Download Image</a></p>  <!-- 다운로드 링크 -->
        
        <% 
                session.removeAttribute("videoGenerated");  // 세션에서 제거
                session.removeAttribute("videoUrl");
            } else {
        %>
            <p>Image generation is still in progress. Please check back later.</p>
            <script>
                setTimeout(function () {
                    window.location.reload();
                }, 5000); // 5초마다 페이지를 새로고침
            </script>
        <% } %>
    </div>
</body>
</html>
