<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Image</title>
</head>
<body>
    <h1>Generate Image from Prompt</h1>
    <form action="${pageContext.request.contextPath}/images/generate" method="post">
        <label for="prompt">Enter your prompt:</label><br><br>
        <input type="text" id="prompt" name="prompt" required><br><br>
        <input type="submit" value="Generate Image">
    </form>
</body>
</html>


