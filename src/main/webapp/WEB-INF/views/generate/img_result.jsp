<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Generation Result</title>
</head>
<body>
    <div class="container">
        <h1>Image Generation Result</h1>

        <c:choose>
            <c:when test="${not empty imageList}">
                <c:forEach var="image" items="${imageList}">
                    <h2>Title: ${image.title}</h2>
                    <img src="${image.imageUrl}" alt="Generated Image" width="300px">
                    <p>Description: ${image.description}</p>
                    <hr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>Sorry, no image was generated.</p>
            </c:otherwise>
        </c:choose>

        <a href="<%= request.getContextPath() %>/generate-image" class="btn">Generate Another Image</a>
    </div>
    <p>
        <a href="<%= request.getContextPath() %>/storage-image">Go to Image Storage</a>
    </p>
</body>
</html>
