<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Music</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/generate_music.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/footer.css">
</head>
<body>

    <!-- 페이지의 주요 콘텐츠 -->
    <div class="container mt-5">
        <form action="<%= request.getContextPath() %>/generate-music" method="post">
            <h1 class="text-center">Generate Music</h1>

            <div class="form-group">
                <label for="prompt">Enter a prompt for your song:</label>
                <input type="text" class="form-control" id="prompt" name="prompt" required>
            </div>

            <div class="form-check mb-3">
                <input type="checkbox" class="form-check-input" id="make_instrumental" name="make_instrumental">
                <label class="form-check-label" for="make_instrumental">Instrumental?</label>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Generate</button>
            
            <!-- 로딩 메시지 -->
            <div id="loadingMessage" style="display:none;" class="text-center mt-3">
                <p>Generating your music, please wait...</p>
            </div>

            <!-- 에러 메시지 표시 -->
            <div id="errorMessage" style="display: none;" class="alert alert-danger mt-3">
                <strong>Error:</strong> ${errorMessage}
            </div>

            <!-- 경고 메시지 표시 -->
            <div id="warningMessage" style="display: none;" class="alert alert-warning mt-3">
                <strong>Warning:</strong> ${warningMessage}
            </div>
        </form>

        <!-- 음악 보관함으로 이동하는 링크 추가 -->
        <p class="text-center mt-3">
            <a href="<%= request.getContextPath() %>/storage-music">Go to Music Storage</a>
        </p>
    </div>

    <!-- Footer 시작 -->
    <footer class="footer mt-5">
        <div class="footer-container">
            <p>© 2024 자유 연재 플랫폼. 모든 권리 보유.</p>
        </div>
    </footer>
    <!-- Footer 끝 -->

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