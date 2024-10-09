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
    <form action="<%= request.getContextPath() %>/generate-music" method="post">
        <h1 id="musicCreatorTitle" class="text-center">Create Your Music</h1>

        <div id="formInputGroup">
            <label for="prompt">Enter a prompt for your music:</label>
            <input type="text" class="form-control" id="prompt" name="prompt" required>
        </div>

        <div id="instrumentalOption" class="form-check mb-3">
            <input type="checkbox" class="form-check-input" id="make_instrumental" name="make_instrumental">
            <label class="form-check-label" for="make_instrumental">Instrumental?</label>
        </div>

        <button id="submitMusic" type="submit" class="btn btn-primary btn-block">Create Music</button>

        <!-- 로딩 메시지 -->
        <div id="loadingIndicator" style="display:none;" class="text-center mt-3">
            <p>Creating your music, please wait...</p>
        </div>

        <!-- 에러 메시지 표시 -->
        <div id="errorAlert" style="display: none;" class="alert alert-danger mt-3">
            <strong>Error:</strong> ${errorMessage}
        </div>

        <!-- 경고 메시지 표시 -->
        <div id="warningAlert" style="display: none;" class="alert alert-warning mt-3">
            <strong>Warning:</strong> ${warningMessage}
        </div>
    </form>

    <!-- 음악 보관함으로 이동하는 링크 추가 -->
    <p id="musicStorageLink" class="text-center mt-3">
        <a href="<%= request.getContextPath() %>/storage-music">Go to Music Storage</a>
    </p>
</div>

<script>
	document.querySelector('form').addEventListener('submit', function() {
	    document.getElementById('loadingIndicator').style.display = 'block';
	});
	
	// 에러 메시지나 경고 메시지가 있는 경우 alert로 표시
	const errorMessage = '${errorMessage}';
	const warningMessage = '${warningMessage}';
	
	if (errorMessage && errorMessage.trim() !== '') {
	    alert("Error: " + errorMessage);
	}
	
	if (warningMessage && warningMessage.trim() !== '') {
	    alert("Warning: " + warningMessage);
	}
	
	document.querySelector('form').addEventListener('submit', function() {
	    document.getElementById('loadingIndicator').style.display = 'block';

	    // 로딩 중 점이 계속 증가하는 애니메이션
	    let dots = document.getElementById("dots");
	    let dotCount = 0;

	    const dotInterval = setInterval(() => {
	        dotCount = (dotCount + 1) % 4;  // 점 개수를 0, 1, 2, 3으로 순환
	        dots.textContent = '.'.repeat(dotCount);  // 점을 반복해서 표시
	    }, 500);  // 0.5초마다 점 개수 변경


	});

</script>

<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
