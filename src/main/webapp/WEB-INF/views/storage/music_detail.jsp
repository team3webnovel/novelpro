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
    <h1>Music Details</h1>
    <c:if test="${music != null}">
        <img src="${music.imageUrl != null ? music.imageUrl : '/default-image.jpg'}" alt="Cover Image" style="width:200px;height:200px;">
        <h2>${music.title != null ? music.title : 'Untitled'}</h2>
        <p>Lyrics: ${music.lyric != null ? music.lyric : 'No lyrics available'}</p>
        <p>Audio URL: <c:choose>
            <c:when test="${music.audioUrl != null}">
                <a href="${music.audioUrl}">${music.audioUrl}</a>
            </c:when>
            <c:otherwise>
                Not available
            </c:otherwise>
        </c:choose></p>
        <p>Type: ${music.type != null ? music.type : 'N/A'}</p>
        <p>Tags: ${music.tags != null ? music.tags : 'No tags'}</p>
        <p>Created At: <fmt:formatDate value="${music.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
    </c:if>
    <c:if test="${music == null}">
        <p>Music data not available.</p>
    </c:if>
</body>
</html>
