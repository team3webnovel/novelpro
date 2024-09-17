<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <c:when test="${not empty musicUrl}">
                <p>Generated Music:</p>
                <audio controls>
                    <source src="${musicUrl}" type="audio/mpeg">
                    Your browser does not support the audio tag.
                </audio>
            </c:when>
            <c:otherwise>
                <p>Sorry, we could not generate the music.</p>
            </c:otherwise>
        </c:choose>

        <a href="<%= request.getContextPath() %>/generate-music" class="btn">Generate Another Music</a>
    </div>
</body>
</html>
