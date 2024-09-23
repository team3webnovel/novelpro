<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Generation Result</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/style.css">
</head>
<body>
    <div class="container">
        <h1>Music Generation Result</h1>

        <c:choose>
            <c:when test="${not empty musicList}">
                <c:forEach var="music" items="${musicList}">
                    <h2>Title: ${music.title}</h2>
                    <p>Lyrics: ${music.lyric}</p>
					<img src="${music.imageUrl}" alt="Cover Image" width="300px">
                    <audio controls>
                        <source src="${music.audioUrl}" type="audio/mpeg">
                        Your browser does not support the audio tag.
                    </audio>
                    <hr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Sorry, no music was generated.</p>
            </c:otherwise>
        </c:choose>

        <a href="<%= request.getContextPath() %>/generate-music" class="btn">Generate Another Music</a>
    </div>
</body>
</html>
