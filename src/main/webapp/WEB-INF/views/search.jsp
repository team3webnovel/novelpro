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

	<!-- 장르 섹션 -->
	<section id="genres" class="genres-section" style="margin-top: 30px;">
	    <div class="container">
	        <div class="row row-cols-7">
	            <div class="col">
	                <div class="card text-center genre selected" data-genre="all">
	                    <div class="card-body">
	                        <h5 class="card-title">전체</h5>
	                    </div>
	                </div>
	            </div>
	            <!-- 동적으로 장르 렌더링 -->
	            <c:forEach var="genre" items="${genres}">
	                <div class="col">
	                    <div class="card text-center genre" data-genre="${genre.code}">
	                        <div class="card-body">
	                            <h5 class="card-title">${genre.name}</h5>
	                        </div>
	                    </div>
	                </div>
	            </c:forEach>
	        </div>
	    </div>
	</section>


	<!-- 소설 리스트 섹션 -->
	<section id="novels" class="novel-section">
	    <div class="container">
	        <div class="row" id="novel-list">
	            <c:forEach var="novel" items="${results}">
	                <div class="col-12 novel-item mb-4 p-3 border rounded" data-genre="${novel.genre}">
	                    <div class="row align-items-center">
	                        <div class="col-md-2">
	                            <!-- 이미지 크기 설정을 위해 스타일 추가 -->
	                            <img src="${novel.imageUrl}" alt="${novel.title}" class="img-fluid rounded" style="max-width: 100%; height: auto;">
	                        </div>
	                        <div class="col-md-10">
	                            <h5 class="mt-3"><a href="${pageContext.request.contextPath}/novel/novel-detail/${novel.novelId}" class="text-dark">${novel.title}</a></h5>
	                            <p class="mb-2"><span class="badge badge-primary">${novel.genre}</span></p>
	                            <p class="text-muted">${novel.intro}</p>
	                        </div>
	                    </div>
	                </div>
	            </c:forEach>
	        </div>
	    </div>
	</section>


    <!-- 검색 결과가 없을 때 -->
    <c:if test="${empty results}">
        <div class="no-results text-center mt-5">
            <p>검색 결과가 없습니다.</p>
        </div>
    </c:if>
</div>

<!-- 푸터 포함 -->
<jsp:include page="/WEB-INF/views/includes/footer.jsp" />

<!-- Bootstrap JS, jQuery, and Popper.js -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    // 페이지 로딩 시 '전체' 장르가 선택된 상태로 소설 목록 표시
    filterNovels('all');

    // 장르 선택 시 필터링 동작을 위한 이벤트 리스너 설정
    document.querySelectorAll('.genre').forEach(function (element) {
        element.addEventListener('click', function () {
            document.querySelectorAll('.genre').forEach(function (el) {
                el.classList.remove('selected');
            });
            this.classList.add('selected');
            const selectedGenre = this.getAttribute('data-genre');
            filterNovels(selectedGenre);
        });
    });

    // 소설 필터링 함수
    function filterNovels(genre) {
        const novels = document.querySelectorAll('.novel-item');
        novels.forEach(function (novel) {
            if (genre === 'all' || novel.getAttribute('data-genre') === genre) {
                novel.style.display = 'block';
            } else {
                novel.style.display = 'none';
            }
        });
    }
</script>

</body>
</html>