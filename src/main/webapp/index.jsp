<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>웹소설 플랫폼</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/index.css">
</head>
<body>
    <!-- 헤더 포함 -->
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />

    <!-- 네비게이션 포함 -->
    <jsp:include page="/WEB-INF/views/includes/navbar.jsp" />

    <section id="new-arrivals" class="novel-section">
        <div class="container">
            <h2>신작 소설</h2>
            <div class="novel-gallery">
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 1"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 2"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 3"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 4"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 5"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 6"></div>
            </div>
        </div>
    </section>

    <section id="popular" class="novel-section">
        <div class="container">
            <h2>인기 소설</h2>
            <div class="novel-gallery">
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 7"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 8"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 9"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 10"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 11"></div>
                <div class="novel-item"><img src="<%= request.getContextPath() %>/static/images/placeholder.jpg" alt="소설 12"></div>
            </div>
        </div>
    </section>

    <!-- 푸터 포함 -->
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
