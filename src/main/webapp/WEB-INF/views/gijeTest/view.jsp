<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<style>
	body {
	    font-family: Arial, sans-serif;
	    background-color: #f4f4f9;
	    margin: 0;
	    padding: 0;
	}
	
	table {
	    width: 70%;
	    margin: 50px auto;
	    border-collapse: collapse;
	    background-color: white;
	    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	}
	
	th, td {
	    padding: 15px;
	    text-align: left;
	    border: 1px solid #ddd;
	}
	
	th {
	    background-color: #95A2DB;
	    color: white;
	    font-weight: bold;
	}
	
	td {
	    background-color: #f9f9f9;
	    font-size: 14px;
	    padding: 20px;
	}
	
	tr:nth-child(odd) td {
	    background-color: #f4f4f9;
	}
	
	h1 {
	    text-align: center;
	    margin-top: 20px;
	    color: #333;
	}
	
	.content-row {
	    background-color: white;
	}
	
	.content-cell {
	    padding: 20px;
	    font-size: 16px;
	    line-height: 1.6;
	}

	.button-container {
	    text-align: center;
	    margin-top: 20px;
	}

	.back-button {
	    background-color: #95A2DB;
	    color: white;
	    border: none;
	    padding: 10px 20px;
	    font-size: 16px;
	    cursor: pointer;
	    text-decoration: none;
	    border-radius: 5px;
	}

	.back-button:hover {
	    background-color: #7f8bc1;
	}
</style>
</head>
<body>
	<h1>게시글 상세보기</h1>
	<table>
	    <tr>
	        <th>번호</th>
	        <td>${board.boardId}</td>
	        <th>제목</th>
	        <td>${board.title}</td>
	    </tr>
	    <tr>
	        <th>작성자</th>
	        <td>${board.userId}</td>
	        <th>조회수</th>
	        <td>${board.viewCount}</td>
	    </tr>
	    <tr>
	        <th>작성일</th>
	        <td colspan="3">${board.createdAt}</td>
	    </tr>
	    <tr class="content-row">
	        <th colspan="4">내용</th>
	    </tr>
	    <tr class="content-row">
	        <td colspan="4" class="content-cell">${board.content}</td>
	    </tr>
	</table>
	
	<!-- 글 목록으로 돌아가기 버튼 -->
	<div class="button-container">
	    <a href="/team3webnovel/gije/list" class="back-button">글 목록으로 돌아가기</a>
	</div>
</body>
</html>
