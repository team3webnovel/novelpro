<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/header.css">
<!-- 헤더 스타일 추가 -->
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
	<div class="header-container">
		<div class="top-right-links">
			<a href="<%=request.getContextPath()%>/" class="btn-primary"> <i
				class="bi bi-house"></i> <!-- 홈 -->
			</a> <a href="<%=request.getContextPath()%>/generate-music"
				class="btn-primary"> <i class="bi bi-music-note"></i> <!-- 음악 생성 -->
			</a> <a href="<%=request.getContextPath()%>/generate-image"
				class="btn-primary"> <i class="bi bi-image"></i> <!-- 이미지 생성 -->
			</a>
            <!-- 세션에 사용자가 있는지 확인하여 로그인/로그아웃 및 마이페이지 표시 -->
			<c:choose>

				<c:when test="${not empty sessionScope.user}">
					<a href="<%=request.getContextPath()%>/mypage"
						class="btn-primary"> <i class="bi bi-person-circle"></i> <!-- 마이페이지 -->
					</a>
					<a href="<%=request.getContextPath()%>/logout" class="btn-login">
						<i class="bi bi-box-arrow-right"></i> <!-- 로그아웃 -->
					</a>

				</c:when>
				<c:otherwise>
					<a href="<%=request.getContextPath()%>/login" class="btn-login">
						<i class="bi bi-person-plus"></i> <!-- 로그인 -->
					</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</header>

