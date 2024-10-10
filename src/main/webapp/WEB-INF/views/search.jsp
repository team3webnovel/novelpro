<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>검색 결과</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/index.css">
    <script src="<%= request.getContextPath()%>/static/js/imageStatus.js"></script>
</head>
<body>

<!-- 헤더 포함 -->
<header>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
</header>

    <!-- 슬라이드 배너 -->
    <section id="banner" class="carousel slide" data-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active type1">
                <img src="<%=request.getContextPath()%>/static/images/banner1.png" alt="Banner 1">
            </div>
            <div class="carousel-item type2">
                <img src="<%=request.getContextPath()%>/static/images/banner2.png" alt="Banner 2">
            </div>
            <div class="carousel-item type3">
                <img src="<%=request.getContextPath()%>/static/images/banner3.png" alt="Banner 3">
            </div>
        </div>
        <a class="carousel-control-prev" href="#banner" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#banner" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </section>

<div class="container mt-5">
    <!-- 검색 바 -->
    <div class="search-bar mb-4">
        <form action="${pageContext.request.contextPath}/generate-search" method="get" onsubmit="event.preventDefault(); goToSearch();">
            <div class="input-group">
                <input type="text" id="index" class="form-control" placeholder="검색어를 입력하세요" required>
                <div class="input-group-append">
                    <button class="btn btn-primary" type="submit">검색</button>
                </div>
            </div>
        </form>
    </div>

    <script type="text/javascript">
        function goToSearch() {
            var searchTerm = document.getElementById("index").value;
            window.location.href = "${pageContext.request.contextPath}/generate-search/" + encodeURIComponent(searchTerm);
        }
    </script>

    <!-- 검색 결과 -->
    <c:if test="${not empty searchQuery}">
        <h3 class="search-results-header">"'${searchQuery}'" 검색 결과입니다.</h3>

        <!-- 검색 결과가 있을 때 -->
        <c:if test="${not empty results}">
            <div class="row">
                <c:forEach var="novel" items="${results}">
                    <div class="col-md-4">
                        <a href="${pageContext.request.contextPath}/novel_detail/${novel.novelId}" style="text-decoration: none; color: inherit;">
                            <div class="card novel-card">
                                <img src="${novel.imageUrl}" alt="${novel.title}" class="card-img-top">
                                <div class="card-body">
                                    <h5 class="novel-title">${novel.title}</h5>
                                    <p class="novel-intro">${novel.intro}</p>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <!-- 검색 결과가 없을 때 -->
        <c:if test="${empty results}">
            <div class="no-results">
                <p>검색 결과가 없습니다.</p>
            </div>
        </c:if>
    </c:if>

    <!-- 검색어가 없을 때 -->
    <c:if test="${empty searchQuery}">
        <div class="no-results">
            <p>검색어를 입력해 주세요.</p>
        </div>
    </c:if>
</div>

<!-- 푸터 포함 -->
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />

<!-- Bootstrap JS, jQuery, and Popper.js -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
