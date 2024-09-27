<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 보관함</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <div class="d-flex justify-content-between">
        <h2>내 보관함</h2>
        <!-- 글쓰기 버튼 -->
        <a href="/write" class="btn btn-primary">글쓰기</a>
    </div>
    
    <!-- 탭 메뉴 -->
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="#all" data-toggle="tab">전체</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#playground" data-toggle="tab">내 소설</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#images" data-toggle="tab">내 이미지랑 음악</a>
        </li>
    </ul>

    <!-- 탭 콘텐츠 -->
    <div class="tab-content mt-4">
        <div class="tab-pane fade show active" id="all">
            <h3>전체</h3>
            <p>전체 항목이 여기에 표시됩니다.</p>
        </div>
        <div class="tab-pane fade" id="playground">
            <h3>내소설</h3>
            <p>내소설에 대한 내용이 여기에 표시됩니다.</p>
        </div>
        <div class="tab-pane fade" id="images">
            <h3>이미지 및 음악</h3>
            <p>이미지 보관함의 내용이 여기에 표시됩니다.</p>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
