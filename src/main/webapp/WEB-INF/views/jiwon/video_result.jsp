<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Video Generation Result</title>
</head>
<body>
    <h1>Video Generation Result</h1>

    <div id="resultSection">
        <% 
            String videoUrl = (String) session.getAttribute("videoUrl");
            Boolean videoGenerated = (Boolean) session.getAttribute("videoGenerated");

            if (videoGenerated != null && videoGenerated) {
        %>
            <p>Video generation was successful! You can watch your video below:</p>
            <video controls style="max-width: 100%;">
                <source src="<%= videoUrl %>" type="video/mp4">
                Your browser does not support the video tag.
            </video>
            <p><a href="<%= videoUrl %>" download>Download Video</a></p>
        
        <% 
                session.removeAttribute("videoGenerated");
                session.removeAttribute("videoUrl");
            } else {
        %>
            <p>Video generation is still in progress. Please check back later.</p>
            <script>
                setTimeout(function () {
                    window.location.reload();
                }, 5000); // 5초마다 페이지를 새로고침
            </script>
        <% } %>
    </div>
</body>
</html>
