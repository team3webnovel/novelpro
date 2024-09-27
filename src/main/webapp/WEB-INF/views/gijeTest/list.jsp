<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<style>
	body {
	    font-family: Arial, sans-serif;
	    background-color: #f4f4f9;
	    margin: 0;
	    padding: 0;
	}

	h1 {
	    text-align: center;
	    margin-top: 20px;
	    color: #333;
	}

	table {
	    width: 80%;
	    margin: 50px auto;
	    border-collapse: collapse;
	    background-color: white;
	    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	    table-layout: fixed;
	}

	th, td {
	    text-align: center;
	    border: 1px solid #ddd;
	}

	th {
		padding: 15px;
	    background-color: #95A2DB;
	    color: white;
	    font-weight: bold;
	    position: sticky;
	    top: 0;
	    z-index: 2;
	}

	td {
		font-size: 13px;
		padding: 10px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	tr:nth-child(even) {
	    background-color: #D9C9C5;
	}

	tr:hover {
	    background-color: #D9C6D1;
	    transition: background-color 0.2s ease;
	}

	a {
	    text-decoration: none;
	    color: #D979B1;
	}

	a:hover {
	    text-decoration: underline;
	}

	/* 게시글 작성 버튼 */
	.create-btn {
	    display: block;
	    width: 150px;
	    margin: 20px auto;
	    padding: 10px 20px;
	    background-color: #D979B1;
	    color: white;
	    text-align: center;
	    border-radius: 5px;
	    font-size: 16px;
	    font-weight: bold;
	    cursor: pointer;
	    text-decoration: none;
	    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	    transition: background-color 0.3s ease;
	}

	.create-btn:hover {
	    background-color: #c45a8b;
	}

	/* 페이징 스타일 */
	.pagination {
	    text-align: center;
	    margin: 20px auto;
	}

	.page-link {
	    display: inline-block;
	    padding: 10px 15px;
	    margin: 0 5px;
	    background-color: #95A2DB;
	    color: white;
	    border-radius: 5px;
	    text-decoration: none;
	    transition: background-color 0.3s ease;
	}

	.page-link:hover {
	    background-color: #c45a8b;
	}
	
	.page-link.active {
	    background-color: #D979B1; /* 강조 색상 */
	    color: white; /* 텍스트 색상 */
	    pointer-events: none; /* 클릭 방지 */
	}
	
	.alert {
	    padding: 15px;
	    background-color: #95A2DB;
	    color: white;
	    margin: 20px auto;
	    width: 70%;
	    text-align: center;
	    border-radius: 5px;
	}

	/* 반응형 디자인 */
	@media (max-width: 768px) {
	    body {
	        font-size: 18px;
	    }
	
	    table {
	        width: 100%;
	        font-size: 16px;
	    }
	
	    th, td {
	        padding: 12px;
	    }
	
	    h1 {
	        font-size: 24px;
	    }

	    .create-btn {
	        width: 100px;
	        font-size: 14px;
	    }
	}
</style>
<script src="<%= request.getContextPath()%>/static/js/board.js"></script>
</head>
<body>
	<c:if test="${not empty message}">
        <div class="alert">${message}</div>
    </c:if>
	<h1>게시판</h1>
	<table>
	    <tr>
	        <th>번호</th>
	        <th>제목</th>
	        <th>작성자</th>
	        <th>작성일</th>
	        <th>조회수</th>
	    </tr>
	    <c:forEach items="${list}" var="board">
	        <tr onclick="goToPage('${board.boardId}', ${currentPage})">
	            <td>${board.boardId}</td>
	            <td><a href="view/${board.boardId}?page=${currentPage}">${board.title}</a></td>
	            <td>${board.userName}</td>
	            <td>${board.createdAt}</td>
	            <td>${board.viewCount}</td>
	        </tr>
	    </c:forEach>
	</table>
	
	<!-- 게시글 작성 버튼 -->
	<a href="/team3webnovel/gije/write" class="create-btn">게시글 작성</a>
	
	<div class="pagination">
	    <c:if test="${totalPages > 1}">
	        <c:forEach var="i" begin="1" end="${totalPages}">
	            <c:choose>
	                <c:when test="${i == currentPage}">
	                    <span class="page-link active">${i}</span> <!-- 현재 페이지 강조 -->
	                </c:when>
	                <c:otherwise>
	                    <a href="/team3webnovel/gije/board?page=${i}" class="page-link">${i}</a>
	                </c:otherwise>
	            </c:choose>
	        </c:forEach>
	    </c:if>
	</div>
</body>
</html>
