<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <img src="${music.imageUrl}" alt="Cover Image" style="width:200px;height:200px;">
        <h2>${music.title}</h2>
        <p>Lyrics: ${music.lyric}</p>
        <p>Audio URL: <a href="${music.audioUrl}">${music.audioUrl}</a></p>
        <p>Model: ${music.modelName}</p>
        <p>Type: ${music.type}</p>
        <p>Tags: ${music.tags}</p>
        <p>Created At: ${music.createdAt}</p>
    </c:if>
    <c:if test="${music == null}">
        <p>Music data not available.</p>
    </c:if>
</body>
</html>
