<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %> <!-- javax.servlet 대신 jakarta.servlet -->

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소설 쓰기</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>소설 쓰기</h2>
    
    <form action="${pageContext.request.contextPath}/write" method="POST">
        <!-- 제목 -->
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="소설 제목을 입력하세요" required>
        </div>

        <!-- 몇화인지 -->
        <div class="form-group">
            <label for="episode">몇 화인지</label>
            <input type="number" class="form-control" id="episode" name="episode" placeholder="몇 화인지 입력하세요" required>
        </div>

        <!-- 장르 -->
        <div class="form-group">
            <label for="genre">장르</label>
            <select class="form-control" id="genre" name="genre" required>
                <option value="">장르 선택</option>
                <option value="로판">로맨스판타지</option>
                <option value="현판">현대판타지</option>
                <option value="판타지">판타지</option>
                <option value="무협">무협</option>
                <option value="로맨스">로맨스</option>
            </select>
        </div>

        <!-- 내용 -->
        <div class="form-group">
            <label for="content">내용</label>
            <textarea class="form-control" id="content" name="content" rows="10" placeholder="소설 내용을 입력하세요" required></textarea>
        </div>

        <!-- 버튼들 -->
        <div class="d-flex justify-content-between">
            <button type="button" class="btn btn-secondary" onclick="saveTemporary()">임시저장</button>
            <button type="submit" class="btn btn-primary">저장</button>
        </div>
    </form>
</div>

<script>
    function saveTemporary() {
        alert("임시 저장 기능이 구현될 예정입니다.");
        // 이 부분에 실제 임시 저장 로직을 추가할 수 있습니다.
    }
</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
