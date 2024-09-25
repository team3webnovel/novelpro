<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Details</title>
</head>
<body>
    <c:if test="${music != null}">
        <!-- 오디오 파일 재생 (PiP 지원) -->
        <video id="audioPlayer" controls style="width: 300px;">
            <source src="https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4" type="video/mp4">
            Your browser does not support the audio tag.
        </video>
        <br>
        <button id="pipButton">Enter Picture-in-Picture</button>

        <h1>Music Details</h1>
        <img src="${music.imageUrl != null ? music.imageUrl : '/default-image.jpg'}" alt="Cover Image" style="width:200px;height:200px;">
        <h2>${music.title != null ? music.title : 'Untitled'}</h2>
        <p>Lyrics: ${music.lyric != null ? music.lyric : 'No lyrics available'}</p>

        <p>Type: ${music.type != null ? music.type : 'N/A'}</p>
        <p>Tags: ${music.tags != null ? music.tags : 'No tags'}</p>
        <p>Created At: <fmt:formatDate value="${music.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
    </c:if>
    
    <c:if test="${music == null}">
        <p>Music data not available.</p>
    </c:if>

    <!-- JavaScript로 PiP 모드 구현 -->
    <script>
        const video = document.getElementById('audioPlayer');
        const pipButton = document.getElementById('pipButton');

        pipButton.addEventListener('click', async () => {
            try {
                if (document.pictureInPictureElement) {
                    await document.exitPictureInPicture();
                } else {
                    await video.requestPictureInPicture();
                }
            } catch (error) {
                console.error('Failed to enter PiP mode:', error);
            }
        });
    </script>
</body>
</html>
