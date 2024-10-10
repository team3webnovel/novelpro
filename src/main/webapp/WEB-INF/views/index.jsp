<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>웹소설 플랫폼</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/index.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/modal.css">
    <script src="<%= request.getContextPath()%>/static/js/imageStatus.js"></script>    
</head>
<body>
    <!-- 헤더 포함 -->
    <header>
        <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    </header>
    
    <!-- 모달 HTML -->
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>Image generation completed!</h2>
            <img id="imageResultImg" src="" alt="Generated Image" style="max-width:100%;">
        </div>
    </div>

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

<<<<<<< HEAD
    <!-- 장르 섹션 -->
    <section id="genres" class="genres-section" style="margin-top: 30px;">
        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <div class="card text-center genre selected" data-genre="all">
                        <div class="card-body">
                            <h5 class="card-title">전체</h5>
                        </div>
                    </div>
                </div>
                <!-- 동적으로 장르 렌더링 -->
                <c:forEach var="genre" items="${genres}">
                    <div class="col-md-2">
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
=======
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
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645

    <!-- 카테고리별 소설 섹션 -->
    <section id="novels" class="novel-section" style="margin-top: 10px;">
        <div class="container">
            <div class="row" id="novel-list">
                <!-- 동적으로 소설 목록 렌더링 -->
                <c:forEach var="novel" items="${novelList}">
                    <div class="col-md-4 novel" data-genre="${novel.genre}">
                        <a href="${pageContext.request.contextPath}/novel_detail/${novel.novelId}" style="text-decoration: none; color: inherit;">
                            <div class="card mb-4">
                                <img src="${novel.imageUrl}" class="card-img-top" alt="${novel.title}" >
                                <div class="card-body">
                                    <h5 class="card-title">${novel.title}</h5>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- 푸터 포함 -->
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        // 기본적으로 전체 버튼이 선택된 상태로 표시
        filterNovels('all'); // 전체 소설 목록을 보여줌

        // 장르 선택 이벤트
        document.querySelectorAll('.genre').forEach(function (element) {
            element.addEventListener('click', function () {
                // 모든 장르에서 selected 클래스 제거
                document.querySelectorAll('.genre').forEach(function (el) {
                    el.classList.remove('selected');
                });
                // 클릭한 장르에 selected 클래스 추가
                this.classList.add('selected');

                const selectedGenre = this.getAttribute('data-genre');
                filterNovels(selectedGenre);
            });
        });

        function filterNovels(genre) {
            const novels = document.querySelectorAll('.novel');
            novels.forEach(function (novel) {
                if (genre === 'all' || novel.getAttribute('data-genre') === genre) {
                    novel.style.display = 'block'; // 보여줌
                } else {
                    novel.style.display = 'none'; // 숨김
                }
            });
        }
    </script>
</body>
</html>
