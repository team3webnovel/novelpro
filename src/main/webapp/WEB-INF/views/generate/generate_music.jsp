<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Music</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/style.css">
</head>
<body>
    <form action="<%= request.getContextPath() %>/generate-music" method="post">
        <h1>Generate Music</h1>

        <label for="prompt">Enter a prompt for your song:</label>
        <input type="text" id="prompt" name="prompt" required>

        <label for="make_instrumental">
            <input type="checkbox" id="make_instrumental" name="make_instrumental">
            Instrumental?
        </label>

        <button type="submit">Generate</button>

        <!-- 에러 메시지 출력 -->
        <c:if test="${not empty errorMessage}">
            <p style="color:red;">${errorMessage}</p>
        </c:if>
    </form>
</body>
</html>
