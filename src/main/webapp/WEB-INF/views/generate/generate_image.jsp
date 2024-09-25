<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Image</title>
</head>
<body>
    <form action="<%= request.getContextPath() %>/generate-image" method="post">
        <h1>Generate Image</h1>

        <label for="prompt">Enter a prompt for your image:</label>
        <input type="text" id="prompt" name="prompt" required>

        <label for="make_high_resolution">
            <input type="checkbox" id="make_high_resolution" name="make_high_resolution">
            High Resolution?
        </label>

        <button type="submit">Generate</button>
        
        <!-- 로딩 메시지 -->
        <div id="loadingMessage" style="display:none;">
            <p>Generating your image, please wait...</p>
        </div>

        <!-- 에러 메시지 표시 -->
        <div id="errorMessage" style="display: none;">
            <strong>Error:</strong> ${errorMessage}
        </div>

        <!-- 경고 메시지 표시 -->
        <div id="warningMessage" style="display: none;">
            <strong>Warning:</strong> ${warningMessage}
        </div>
    </form>

    <!-- 이미지 보관함으로 이동하는 링크 추가 -->
    <p>
        <a href="<%= request.getContextPath() %>/storage-image">Go to Image Storage</a>
    </p>

    <script>
        document.querySelector('form').addEventListener('submit', function() {
            document.getElementById('loadingMessage').style.display = 'block';
        });

        // 에러 메시지나 경고 메시지가 있는 경우 표시
        if ('${errorMessage}' !== '') {
            document.getElementById('errorMessage').style.display = 'block';
        }
        if ('${warningMessage}' !== '') {
            document.getElementById('warningMessage').style.display = 'block';
        }
    </script>
</body>
</html>
