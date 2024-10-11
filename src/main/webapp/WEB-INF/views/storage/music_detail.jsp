<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/music_detail.css">
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</head>
<body>
    <div class="container mt-5">
        <c:if test="${music != null}">
            <div class="text-center mb-4">
                <video id="audioPlayer" controls style="width: 100%; max-width: 300px;">
                    <source src="https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4" type="video/mp4">
                    Your browser does not support the audio tag.
                </video>
                <br>
                <button id="pipButton" class="btn btn-primary mt-2">Enter Picture-in-Picture</button>
            </div>

            <h1 class="text-center">${music.title != null ? music.title : 'Untitled'}</h1>
            <div class="text-center mb-4">
                <img src="${music.imageUrl != null ? music.imageUrl : '/default-image.jpg'}" alt="Cover Image" class="img-fluid" style="max-width: 200px; height: auto;">
            </div>
            <div class="mb-3 dark-background">
                <h5>Lyrics:</h5>
                <p>${music.lyric != null ? music.lyric : 'No lyrics available'}</p>
            </div>

            <div class="mb-3">
                <p><strong>Type:</strong> ${music.type != null ? music.type : 'N/A'}</p>
                <p><strong>Tags:</strong> ${music.tags != null ? music.tags : 'No tags'}</p>
                <p><strong>Created At:</strong> <fmt:formatDate value="${music.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
            </div>
        </c:if>

        <c:if test="${music == null}">
            <p class="text-danger">Music data not available.</p>
        </c:if>
    </div>

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
