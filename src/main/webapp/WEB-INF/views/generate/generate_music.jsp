<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        
        <!-- 로딩 메시지 -->
        <div id="loadingMessage" style="display:none;">
            <p>Generating your music, please wait...</p>
        </div>
    </form>

    <script>
        document.querySelector('form').addEventListener('submit', function() {
            document.getElementById('loadingMessage').style.display = 'block';
        });
    </script>
</body>
</html>
