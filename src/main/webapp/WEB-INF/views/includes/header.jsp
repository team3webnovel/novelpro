<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <div class="container">
        <h1>웹소설 플랫폼에 오신 것을 환영합니다</h1>
        <p>웹소설의 세계를 탐험하거나, AI의 도움으로 당신만의 이야기를 꾸며보세요.</p>
        <div class="top-right-links">
            <a href="<%= request.getContextPath() %>/" class="btn-primary">홈</a> <!-- 홈 버튼 추가 -->
            <a href="<%= request.getContextPath() %>/generate-music" class="btn-primary">음악 생성</a>
            <a href="<%= request.getContextPath() %>/generate-image" class="btn-primary">이미지 생성</a>

            <!-- 세션에 사용자가 있는지 확인하여 로그인/로그아웃 및 마이페이지 표시 -->
            <c:choose>
               
                <c:when test="${not empty sessionScope.user_id}">
                    <!-- 로그인 상태일 때 -->
                    <a href="<%= request.getContextPath() %>/mypage" class="btn-primary">마이페이지</a>
                    <a href="<%= request.getContextPath() %>/logout" class="btn-login">로그아웃</a>
                </c:when>
                <c:otherwise>
                    <!-- 로그아웃 상태일 때 -->
                    <a href="<%= request.getContextPath() %>/login" class="btn-login">로그인</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
