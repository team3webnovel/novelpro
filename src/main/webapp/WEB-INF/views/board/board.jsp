<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <style>
        body {
            font-family: Arial, sans-serif;
            margin-top: 60px;
        }
        .table {
		    table-layout: fixed; /* 테이블의 열 너비를 고정 */
		}
		td {
		    overflow: hidden;
		    text-overflow: ellipsis; /* 내용이 넘치면 생략 표시 (...) */
		    white-space: nowrap; /* 줄 바꿈 없이 한 줄로 표시 */
		}
        .create-btn {
            margin-top: 20px;
            margin-bottom: 20px;
        }
        .pagination {
            justify-content: center;
        }
        .table-hover tbody tr:hover {
            background-color: #f2f2f2;
        }
    </style>
    <script src="<%= request.getContextPath()%>/static/js/board.js"></script>
</head>
<body>
<div class="main-container">
    <div class="container mt-4">
        <!-- 메시지 표시 -->
        <c:if test="${not empty message}">
            <div class="alert alert-info" role="alert">${message}</div>
        </c:if>
        
        <ul class="nav nav-tabs mb-4" id="boardTab" role="tablist">
		  <li class="nav-item">
		    <a class="nav-link active" href="/team3webnovel/board" role="tab">리뷰 홍보 게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/team3webnovel/images/board" role="tab">이미지 게시판</a>
		  </li>
		</ul>
        
        <table class="table table-bordered table-hover">
		    <thead class="thead-light">
		        <tr>
		            <th scope="col" style="width: 10%;">번호</th>
		            <th scope="col" style="width: 40%;">제목</th>
		            <th scope="col" style="width: 20%;">작성자</th>
		            <th scope="col" style="width: 17%;">작성일</th>
		            <th scope="col" style="width: 13%;">조회수</th>
		        </tr>
		    </thead>
		    <tbody>
		        <c:forEach items="${list}" var="board">
		            <tr onclick="goToPage('${board.boardId}', ${currentPage})">
		                <td>${board.boardId}</td>
		                <td><a href="board/view/${board.boardId}?page=${currentPage}" class="text-decoration-none">${board.title}</a></td>
		                <td>${board.userName}</td>
		                <td>${board.formattedCreatedAt}</td>
		                <td>${board.viewCount}</td>
		            </tr>
		        </c:forEach>
		    </tbody>
		</table>
        
        <!-- 게시글 작성 버튼 -->
        <div class="text-right">
            <a href="board/write" class="btn btn-primary create-btn">게시글 작성</a>
        </div>

        <!-- 페이지네이션 -->
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${totalPages > 1}">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <li class="page-item active" aria-current="page">
                                    <span class="page-link">${i}</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <a href="/team3webnovel/board?page=${i}" class="page-link">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
