<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

    <style type="text/css">
        .like-container {
            display: inline-block;
            margin-top: 10px;
        }

        .like-btn {
            background-color: transparent;
            border: none;
            cursor: pointer;
            font-size: 18px;
            color: #555;
            display: flex;
            align-items: center;
        }

        .like-btn:hover .like-icon {
            color: #ff7675; /* 좋아요 버튼을 마우스로 올렸을 때 색상 변경 */
        }

        .like-icon {
            margin-right: 5px;
            font-size: 20px;
            transition: color 0.3s ease;
        }

        .like-count {
            font-size: 16px;
        }
        .card-title {
		    height: 40px; /* 제목의 높이를 고정 */
		    line-height: 1.2; /* 줄 간격 조절 */
		    overflow: hidden; /* 내용이 넘치면 숨김 */
		    text-overflow: ellipsis; /* 넘친 텍스트는 ...으로 표시 */
		    white-space: normal; /* 텍스트 줄바꿈 허용 */
		    display: -webkit-box;
		    -webkit-line-clamp: 2; /* 최대 두 줄까지만 표시 */
		    -webkit-box-orient: vertical;
		}
		
		.genres-section .card {
		    height: 100%; /* 카드의 높이를 100%로 설정 */
		    display: flex;
		    align-items: center;
		    justify-content: center;
		}
		
		.genres-section .card-body {
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    padding: 0; /* 기존 패딩 제거 */
		}
		
		.genres-section .card-title {
		    margin: 0; /* 기존 마진 제거 */
		    height: auto; /* 고정 높이 제거 */
		    line-height: normal; /* 라인 높이 정상으로 복원 */
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    text-align: center;
		    width: 100%;
		    height: 100%;
		}
		
		.card-body:hover {
		    background-color: inherit; /* card-body에 호버 시 배경색 변화 없음 */
		}
		
		.card-body .card-title:hover {
		    color: inherit; /* card-title에 호버 시 텍스트 색상 변화 없음 */
		    cursor: default; /* 호버 시 커서가 기본 상태로 유지되도록 */
		}
				


		        
    </style>
    
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

    <!-- 카테고리별 소설 섹션 -->
<section id="novels" class="novel-section" style="margin-top: 10px;">
    <div class="container">
        <div class="row" id="novel-list">
            <!-- 동적으로 소설 목록 렌더링 -->
            <c:forEach var="novel" items="${novelList}">
                <div class="col-md-4 novel" data-genre="${novel.genre}">
                    <a href="${pageContext.request.contextPath}/novel/novel-detail/${novel.novelId}" style="text-decoration: none; color: inherit;">
                        <div class="card mb-4">
                            <c:choose>
                                <c:when test="${empty novel.imageUrl}">						
                                    <img src="<%=request.getContextPath()%>/static/images/logo.png" alt="logo" class="card-img-top">
                                </c:when>
                                <c:otherwise>
                                    <img src="${novel.imageUrl}" class="card-img-top" alt="${novel.title}">
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <h5 class="card-title">
                                    <c:choose>
                                        <c:when test="${fn:length(novel.title) > 18}">
                                            ${fn:substring(novel.title, 0, 18)}...
                                        </c:when>
                                        <c:otherwise>
                                            ${novel.title}
                                        </c:otherwise>
                                    </c:choose>
                                </h5>
                                <!-- 좋아요 버튼을 카드 안에 추가 -->
			                    </a>
								<div class="like-container mt-1">
								    <button class="like-btn" onclick="toggleLike(${novel.novelId})">
								        <span class="like-icon">❤️</span>
								        <span id="like-count-${novel.novelId}">${novel.likeCount}</span> <!-- 좋아요 수 -->
								    </button>
								</div>

                            </div>
                        </div> <!-- card 닫는 div -->
                </div> <!-- col-md-4 novel 닫는 div -->
            </c:forEach>
        </div> <!-- row 닫는 div -->
    </div> <!-- container 닫는 div -->
</section>



    <!-- 푸터 포함 -->
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />

    <script src="<%=request.getContextPath()%>/static/js/index.js"></script>
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
