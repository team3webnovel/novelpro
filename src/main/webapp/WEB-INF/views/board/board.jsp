<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	.container {
		max-width: 800px;
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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
</style>
<script src="<%= request.getContextPath()%>/static/js/board.js"></script>
</head>
<body>
	<div class ="container">
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
<<<<<<< HEAD:src/main/webapp/WEB-INF/views/gijeTest/list.jsp
		            <td><a href="view/${board.boardId}?page=${currentPage}">${board.title}</a></td>
=======
		            <td><a href="<%= request.getContextPath()%>/board/view/${board.boardId}?page=${currentPage}">${board.title}</a></td>
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645:src/main/webapp/WEB-INF/views/board/board.jsp
		            <td>${board.userName}</td>
		            <td>${board.formattedCreatedAt}</td>
		            <td>${board.viewCount}</td>
		        </tr>
		    </c:forEach>
		</table>
		
		<!-- 게시글 작성 버튼 -->
<<<<<<< HEAD:src/main/webapp/WEB-INF/views/gijeTest/list.jsp
		<a href="/team3webnovel/gije/write" class="create-btn">게시글 작성</a>
=======
		<a href="/team3webnovel/board/write" class="create-btn">게시글 작성</a>
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645:src/main/webapp/WEB-INF/views/board/board.jsp
		
		<div class="pagination">
		    <c:if test="${totalPages > 1}">
		        <c:forEach var="i" begin="1" end="${totalPages}">
		            <c:choose>
		                <c:when test="${i == currentPage}">
		                    <span class="page-link active">${i}</span> <!-- 현재 페이지 강조 -->
		                </c:when>
		                <c:otherwise>
<<<<<<< HEAD:src/main/webapp/WEB-INF/views/gijeTest/list.jsp
		                    <a href="/team3webnovel/gije/board?page=${i}" class="page-link">${i}</a>
=======
		                    <a href="/team3webnovel/board?page=${i}" class="page-link">${i}</a>
>>>>>>> fca975958283a01489625d9af5f4fb7a09190645:src/main/webapp/WEB-INF/views/board/board.jsp
		                </c:otherwise>
		            </c:choose>
		        </c:forEach>
		    </c:if>
		</div>
	</div>
</body>
</html>
