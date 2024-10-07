<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Music Creator</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/generate/generate_music.css">
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</head>
<body>

<!-- Music Creator Container -->
<div id="musicCreatorContainer" class="mt-5">
    <form action="<%= request.getContextPath() %>/create-music" method="post">
        <h1 id="musicCreatorTitle" class="text-center">Create Your Music</h1>

        <div id="formInputGroup">
            <label for="songPrompt">Enter a prompt for your music:</label>
            <input type="text" class="form-control" id="songPrompt" name="songPrompt" required>
        </div>

        <div id="instrumentalOption" class="form-check mb-3">
            <input type="checkbox" class="form-check-input" id="createInstrumental" name="createInstrumental">
            <label class="form-check-label" for="createInstrumental">Instrumental?</label>
        </div>

        <button id="submitMusic" type="submit" class="btn btn-primary btn-block">Create Music</button>

        <!-- 로딩 메시지 -->
        <div id="loadingIndicator" style="display:none;" class="text-center mt-3">
            <p>Creating your music, please wait...</p>
        </div>

        <!-- 에러 메시지 표시 -->
        <div id="errorAlert" style="display: none;" class="alert alert-danger mt-3">
            <strong>Error:</strong> ${errorAlert}
        </div>

        <!-- 경고 메시지 표시 -->
        <div id="warningAlert" style="display: none;" class="alert alert-warning mt-3">
            <strong>Warning:</strong> ${warningAlert}
        </div>
    </form>

    <!-- 음악 보관함으로 이동하는 링크 추가 -->
    <p id="musicStorageLink" class="text-center mt-3">
        <a href="<%= request.getContextPath() %>/music-storage">Go to Music Storage</a>
    </p>
</div>

<script>
    document.querySelector('form').addEventListener('submit', function() {
        document.getElementById('loadingIndicator').style.display = 'block';
    });

    // 에러 메시지나 경고 메시지가 있는 경우 표시
    if ('${errorAlert}' !== '') {
        document.getElementById('errorAlert').style.display = 'block';
    }
    if ('${warningAlert}' !== '') {
        document.getElementById('warningAlert').style.display = 'block';
    }
</script>

<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
