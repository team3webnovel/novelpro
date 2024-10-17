<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

body {
	display: flex;
}

.sidebar {
	width: 250px;
	height: 100vh;
	position: fixed;
	top: 0;
	left: 0;
	background-color: #f8f9fa;
	padding-top: 20px;
}

.sidebar a, .sidebar button {
	padding: 10px 15px;
	text-decoration: none;
	font-size: 18px;
	color: #333;
	display: block;
	background: none;
	border: none;
	width: 100%;
	text-align: left;
	transition: 0.3s;
}

.sidebar a:hover, .sidebar button:hover {
	background-color: #e0e0e0;
	color: black; /* 글자 색상은 흰색 배경에 대비되도록 검은색으로 유지 */
}

.main-content {
	margin-left: 130px;
	padding: 20px;
	width: calc(100% - 260px);
}
/* AI 창작 스튜디오 버튼 스타일 */
.btn-ai-studio {
	margin: 10px auto; /* 좌우 여백을 자동으로 설정하여 가운데 정렬 */
	font-size: 18px;
	color: #333;
	display: block;
	text-align: center;
	padding: 10px;
	background-color: #e9ecef;
	border: none;
	transition: 0.3s;
	width: 80%; /* 가로 폭을 80%로 설정 */
}

.btn-ai-studio:hover {
	background-color: #007bff;
	color: white;
}
/* 드롭다운 메뉴 스타일 */
.dropdown-container {
	display: none;
	padding-left: 15px;
}

.dropdown-container a {
	font-size: 16px;
}

/* 드롭다운 화살표 스타일 */
.dropdown-btn:after {
    content: '\25BC';
    float: right;
    margin-right: 15px;
    font-size: 12px; /* 화살표 크기 조정 (기본보다 작게 설정) */
}

.dropdown-btn.active:after {
    content: '\25B2'; /* 위로 화살표 */
    font-size: 12px; /* 위로 향하는 화살표도 동일하게 크기 조정 */
}
/* 내 정보 스타일 */
.profile-section {
    padding: 20px;
    text-align: center; /* 텍스트와 이미지 가운데 정렬 */
    border-bottom: 1px solid #ddd;
    display: flex;
    flex-direction: column;
    align-items: center; /* 콘텐츠를 중앙 정렬 */
}

.profile-section img {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    object-fit: cover;
    margin-bottom: 10px; /* 이미지 아래쪽 여백 추가 */
}

.profile-section h4 {
    margin-top: 10px;
    font-size: 20px;
    color: #333;
}

.profile-section p {
    font-size: 14px;
    color: #777;
}

</style>

</head>
<body>

	<!-- 왼쪽 사이드바 시작 -->
	<div class="sidebar">
		<h4 class="text-center">내 보관함</h4>
		        <!-- 내 정보 섹션 추가 -->
        <div class="profile-section">
            <h4>${user.username }</h4>
            <p>${user.email }</p>
        </div>
		<!-- AI 창작 스튜디오 버튼 추가 -->
		<button class="btn-ai-studio" id="aiStudioButton">AI 창작 스튜디오</button>
		<!-- 컨텐츠 생성 버튼 -->
		<button class="dropdown-btn">컨텐츠 생성</button>
		<div class="dropdown-container">
			<a href="<%=request.getContextPath()%>/images">표지 제작</a> <a
				href="<%=request.getContextPath()%>/generate-font">표지 폰트</a> <a
				href="<%=request.getContextPath()%>/videos/video">비디오 제작(임시)</a> <a
				href="<%=request.getContextPath()%>/generate-music">BGM 만들기</a>
		</div>

		<a href="<%=request.getContextPath()%>/novel/new-novel">글쓰기</a>
	</div>
	
</body>
</html>