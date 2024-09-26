<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Generation Result</title>
</head>
<body>
    <h1>Image Generation Result</h1>

    <!-- 이미지 결과 표시 -->
    <c:if test="${not empty imageUrl}">
        <p>Generated Image:</p>
        <img src="${imageUrl}" alt="Generated Image" />
    </c:if>

    <!-- 메시지 표시 -->
    <p>${message}</p>

    <br><br>

    <a href="${pageContext.request.contextPath}/images/generate">Generate another image</a>
</body>
</html>
