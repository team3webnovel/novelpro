<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/css/header.css">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script>
        window.addEventListener('scroll', function() {
            const header = document.querySelector('header');
            if (window.scrollY > 0) {
                header.classList.add('scrolled');
            } else {
                header.classList.remove('scrolled');
            }
        });
    </script>
</head>

<header>
    <div class="header-container container d-flex justify-content-between align-items-center py-2">
        <div class="header-title">
            <a href="<%=request.getContextPath()%>/" style="text-decoration: none; color: inherit;"> 노벨미디어 </a>
        </div>
        <div class="board">
            <a href="<%=request.getContextPath()%>/board" style="text-decoration: none; color: inherit;"> 리뷰/홍보 </a>
        </div>
        <div class="top-right-links">
            <a href="<%=request.getContextPath()%>/generate-music" class="btn btn-primary btn-sm">
                <i class="bi bi-music-note"></i> <!-- 음악 생성 -->
            </a>
            <a href="<%=request.getContextPath()%>/generate-image" class="btn btn-primary btn-sm">
                <i class="bi bi-image"></i> <!-- 이미지 생성 -->
            </a>
            <a href="<%=request.getContextPath()%>/generate-search" class="btn btn-primary btn-sm">
                <i class="bi bi-search"></i> <!-- 검색 -->
            </a>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="<%=request.getContextPath()%>/mypage" class="btn btn-primary btn-sm">
                        <i class="bi bi-person-circle"></i> <!-- 마이페이지 -->
                    </a>
                    <a href="<%=request.getContextPath()%>/logout" class="btn btn-primary btn-sm">
                        <i class="bi bi-box-arrow-right"></i> <!-- 로그아웃 -->
                    </a>
                    <!-- 보관함 버튼 추가 (로그인한 사용자에게만 표시됨) -->
                    <a href="<%=request.getContextPath()%>/my_storage" class="btn btn-primary btn-sm">
                        <i class="bi bi-box"></i> <!-- 보관함 -->
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="<%=request.getContextPath()%>/login" class="btn btn-primary btn-sm">
                        <i class="bi bi-person-plus"></i> <!-- 로그인 -->
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
