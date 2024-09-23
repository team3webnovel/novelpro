<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>웹소설 플랫폼</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/index.css">
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

    <!-- 장르 섹션 -->
    <section id="genres" class="genres-section" style="margin-top: 30px;">
        <div class="container">
            <h2 class="my-4">장르 선택</h2>
            <div class="row">
                <div class="col-md-2">
                    <div class="card text-center genre" data-genre="all">
                        <div class="card-body">
                            <h5 class="card-title">전체</h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="card text-center genre" data-genre="romance_fantasy">
                        <div class="card-body">
                            <h5 class="card-title">로맨스판타지</h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="card text-center genre" data-genre="modern_fantasy">
                        <div class="card-body">
                            <h5 class="card-title">현대판타지</h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="card text-center genre" data-genre="fantasy">
                        <div class="card-body">
                            <h5 class="card-title">판타지</h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="card text-center genre" data-genre="martial_arts">
                        <div class="card-body">
                            <h5 class="card-title">무협</h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="card text-center genre" data-genre="romance">
                        <div class="card-body">
                            <h5 class="card-title">로맨스</h5>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- 카테고리별 소설 섹션 -->
    <section id="novels" class="novel-section" style="margin-top: 50px;">
        <div class="container">
            <h2 class="my-4">소설 목록</h2>
            <div class="row" id="novel-list">
                <!-- 소설 항목들 (장르에 맞게 필터링 가능) -->
                <div class="col-md-4 novel" data-genre="romance_fantasy">
                    <div class="card mb-4">
                        <img src="<%=request.getContextPath()%>/static/images/romance_fantasy1.png" class="card-img-top" alt="소설 1">
                        <div class="card-body">
                            <h5 class="card-title">소설 1</h5>
                            <p class="card-text">로맨스판타지 소설 설명.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 novel" data-genre="modern_fantasy">
                    <div class="card mb-4">
                        <img src="<%=request.getContextPath()%>/static/images/modern_fantasy1.png" class="card-img-top" alt="소설 2">
                        <div class="card-body">
                            <h5 class="card-title">소설 2</h5>
                            <p class="card-text">현대판타지 소설 설명.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 novel" data-genre="fantasy">
                    <div class="card mb-4">
                        <img src="<%=request.getContextPath()%>/static/images/fantasy1.png" class="card-img-top" alt="소설 3">
                        <div class="card-body">
                            <h5 class="card-title">소설 3</h5>
                            <p class="card-text">판타지 소설 설명.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 novel" data-genre="martial_arts">
                    <div class="card mb-4">
                        <img src="<%=request.getContextPath()%>/static/images/martial_arts1.png" class="card-img-top" alt="소설 4">
                        <div class="card-body">
                            <h5 class="card-title">소설 4</h5>
                            <p class="card-text">무협 소설 설명.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 novel" data-genre="romance">
                    <div class="card mb-4">
                        <img src="<%=request.getContextPath()%>/static/images/romance1.jpg" class="card-img-top" alt="소설 5">
                        <div class="card-body">
                            <h5 class="card-title">소설 5</h5>
                            <p class="card-text">로맨스 소설 설명.</p>
                        </div>
                    </div>
                </div>
                <!-- 추가 소설 항목들 -->
            </div>
        </div>
    </section>

    <!-- 푸터 포함 -->
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        // 장르 선택 이벤트
        document.querySelectorAll('.genre').forEach(function (element) {
            element.addEventListener('click', function () {
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
