<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Generation Result</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/music_result.css"> <!-- CSS 링크 추가 -->
</head>
<body class="bg-dark text-white">
    <div class="container mt-5">
        <h1 class="text-center mb-4">Music Generation Result</h1>

        <c:choose>
            <c:when test="${not empty musicList}">
                <div class="music-gallery">
                    <c:forEach var="music" items="${musicList}">
                        <div class="music-item">
                            <img src="${music.imageUrl}" alt="Cover Image" class="img-fluid">
                            <div class="lyrics">
                                <h2 class="h5">Title: ${music.title}</h2>
                                <div class="lyric-content">
                                    <p>Lyrics: ${music.lyric}</p>
                                </div>
                                <audio controls class="w-100 mt-2">
                                    <source src="${music.audioUrl}" type="audio/mpeg">
                                    Your browser does not support the audio tag.
                                </audio>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="text-danger">Sorry, no music was generated.</p>
            </c:otherwise>
        </c:choose>

        <div class="text-center mt-4">
            <a href="<%= request.getContextPath() %>/generate-music" class="btn btn-primary">Generate Another Music</a>
        </div>
        <p class="text-center mt-2">
            <a href="<%= request.getContextPath() %>/storage-music" class="text-white">Go to Music Storage</a>
        </p>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
