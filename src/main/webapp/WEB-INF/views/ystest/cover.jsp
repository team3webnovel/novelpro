<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cover$BGM</title>
<!-- Bootstrap CSS 추가 -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>삽화 및 배경음악 추가</h2>
    
    <form action="${pageContext.request.contextPath}/cover" method="POST">
		<div class="form-group">
		    <label for="search">검색</label>
		    <div class="input-group">
		        <div class="input-group-prepend">
		            <select class="form-control" id="searchOption" name="searchOption" required>
		                <option value="name">이름</option>
		                <option value="date">날짜</option>
		            </select>
		        </div>
		        <input type="text" class="form-control" id="searchInput" name="searchInput" placeholder="검색어 입력" required>
		        <div class="input-group-append">
		            <button class="btn btn-primary" type="submit">검색</button>
		        </div>
		    </div>
		</div>

        <!-- 삽화 URL -->
        <div class="form-group">
            <label for="illust">삽화 URL</label>
            <input type="text" class="form-control" id="illust" name="illust" placeholder="삽화 URL을 입력하세요" required>
        </div>
        
        <!-- 배경음악 URL -->
        <div class="form-group">
            <label for="bgm">배경음악 URL</label>
            <input type="text" class="form-control" id="bgm" name="bgm" placeholder="배경음악 URL을 입력하세요" required>
        </div>
        
        <!-- 버튼들 -->
        <div class="d-flex justify-content-between">
            <button type="button" class="btn btn-secondary" onclick="saveTemporary()">임시저장</button>
            <button type="button" class="btn btn-primary" onclick="next()">다음</button>
        </div>
    </form>
</div>

<script>
    // 서버에서 contextPath 값을 미리 가져옴
    var contextPath = "<%= request.getContextPath() %>";

    function saveTemporary() {
        alert("임시 저장 기능이 구현될 예정입니다.");
    }

    function next() {
        // contextPath를 사용하여 write.jsp로 이동
        window.location.href = contextPath + "/write";
    }
</script>

<!-- Bootstrap JS 추가 -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
