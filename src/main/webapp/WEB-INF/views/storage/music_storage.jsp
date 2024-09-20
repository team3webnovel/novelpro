<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Stored Music</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <style>
        .music-item {
            display: inline-block;
            text-align: center;
            margin: 10px;
        }

        .music-item img {
            display: block;
            width: 200px;
            height: 200px;
            object-fit: cover;
        }

        .music-item h3 {
            margin-top: 10px;
            font-size: 16px;
            color: #333;
        }
    </style>
</head>
<body>
    <h1>Your Stored Music</h1>
    <div class="music-gallery">
        <c:forEach var="music" items="${musicList}">
            <div class="music-item">
                <a href="${pageContext.request.contextPath}/music_detail/${music.creationId}">
                    <img src="${music.imageUrl}" alt="Cover Image">
                    <h3>${music.title}</h3>
                </a>
            </div>
        </c:forEach>
    </div>
</body>
</html>
