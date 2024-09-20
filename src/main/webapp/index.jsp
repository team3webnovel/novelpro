<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>웹소설 플랫폼</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/index.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/footer.css">
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
				<img src="<%=request.getContextPath()%>/static/images/banner1.png"
					alt="Banner 1">
				<div class="carousel-caption"></div>
			</div>
			<div class="carousel-item type2">
				<img src="<%=request.getContextPath()%>/static/images/banner2.png"
					alt="Banner 2">
				<div class="carousel-caption"></div>
			</div>
			<div class="carousel-item type3">
				<img src="<%=request.getContextPath()%>/static/images/banner3.png"
					alt="Banner 3">
				<div class="carousel-caption"></div>
			</div>
		</div>
		<a class="carousel-control-prev" href="#banner" role="button"
			data-slide="prev"> <span class="carousel-control-prev-icon"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="carousel-control-next" href="#banner" role="button"
			data-slide="next"> <span class="carousel-control-next-icon"
			aria-hidden="true"></span> <span class="sr-only">Next</span>
		</a>
	</section>

	<!-- 카테고리별 소설 섹션 -->
	<section id="new-arrivals" class="novel-section"
		style="margin-top: 50px;">
		<div class="container">
			<h2 class="my-4">신작 소설</h2>
			<div class="row">
				<div class="col-md-4">
					<div class="card mb-4">
						<img
							src="<%=request.getContextPath()%>/static/images/placeholder.jpg"
							class="card-img-top" alt="소설 1">
						<div class="card-body">
							<h5 class="card-title">소설 1</h5>
							<p class="card-text">간단한 설명을 여기에 추가하세요.</p>
						</div>
					</div>
				</div>
				<!-- 추가 소설 항목들 -->
			</div>
		</div>
	</section>

	<section id="popular" class="novel-section" style="margin-top: 50px;">
		<div class="container">
			<h2 class="my-4">인기 소설</h2>
			<div class="row">
				<div class="col-md-4">
					<div class="card mb-4">
						<img
							src="<%=request.getContextPath()%>/static/images/placeholder.jpg"
							class="card-img-top" alt="소설 7">
						<div class="card-body">
							<h5 class="card-title">소설 7</h5>
							<p class="card-text">간단한 설명을 여기에 추가하세요.</p>
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
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

	<script>
		window.addEventListener('scroll', function() {
			const header = document.querySelector('header');
			if (window.scrollY > 50) { // 스크롤이 50px 이상 내려가면
				header.classList.add('scrolled');
			} else {
				header.classList.remove('scrolled');
			}
		});
	</script>
</body>
</html>
