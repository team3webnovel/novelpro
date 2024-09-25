<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">게시판</h2>

    <!-- 게시글 작성 버튼 -->
    <div class="text-right mb-3">
        <a href="<%=request.getContextPath()%>/post/new" class="btn btn-primary">게시글 작성</a>
    </div>

    <!-- 게시글 목록 테이블 -->
    <table class="table table-hover">
        <thead class="thead-light">
        <tr>
            <th scope="col">번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
        </tr>
        </thead>
        <tbody>
        <!-- 게시글 목록 반복 -->
        <c:forEach var="post" items="${posts}">
            <tr>
                <td>${post.id}</td>
                <td>
                    <!-- 게시글 제목을 클릭하면 상세 페이지로 이동 -->
                    <a href="<%=request.getContextPath()%>/post/${post.id}">
                        ${post.title}
                    </a>
                </td>
                <td>${post.author}</td>
                <td>${post.createdDate}</td>
            </tr>
        </c:forEach>
        <!-- 게시글이 없을 경우 표시할 내용 -->
        <c:if test="${empty posts}">
            <tr>
                <td colspan="4" class="text-center">게시글이 없습니다.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>

<!-- Bootstrap JS, Popper.js, jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
