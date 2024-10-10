<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<style>
	.container {
		max-width: 800px;
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }
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

    .back-button, .delete-button {
        background-color: #95A2DB;
        color: white;
        border: none;
        padding: 10px 20px;
        font-size: 16px;
        cursor: pointer;
        text-decoration: none;
        border-radius: 5px;
        margin: 0 10px;
        transition: background-color 0.3s ease;
    }
    
    .delete-button {
        background-color: #D979B1;
    }

    .back-button:hover {
        background-color: #7f8bc1;
    }
    
    .delete-button:hover {
        background-color: #c45a8b;
    }

    /* 댓글 입력 및 표시 스타일 */
	textarea {
	    width: calc(100% - 120px);
	    height: 100px;
	    padding: 10px;
	    font-size: 14px;
	    border: 1px solid #ddd;
	    border-radius: 5px;
	    resize: none;
	    box-sizing: border-box;
	}
	
	.comment-container {
	    display: flex;
	    justify-content: space-between;
	    align-items: center;
	}
	
	.comment-button {
	    background-color: #D979B1;
	    color: white;
	    border: none;
	    padding: 10px 20px;
	    font-size: 16px;
	    cursor: pointer;
	    border-radius: 5px;
	    transition: background-color 0.3s ease;
	}
	
	.comment-button:hover {
	    background-color: #c45a8b;
	}

	.comment-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px solid #ddd;
	}
	
	.comment-content {
	    flex-grow: 1;
	    font-size: 14px;
	    line-height: 1.5;
	}
	
	.comment-time {
	    font-size: 12px;
	    color: #999;
	    margin-left: 10px;
	}
	
	.comment-delete {
	    background-color: #D979B1;
	    color: white;
	    border: none;
	    padding: 5px 10px;
	    font-size: 12px;
	    cursor: pointer;
	    border-radius: 5px;
	    transition: background-color 0.3s ease;
	}
	
	.comment-delete:hover {
	    background-color: #c45a8b;
	}
</style>
<script src="<%= request.getContextPath()%>/static/js/board_view.js"></script>
</head>
<body>
	<div class="container">
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
	            <td>${board.userName}</td>
	            <th>조회수</th>
	            <td>${board.viewCount}</td>
	        </tr>
	        <tr>
	            <th>작성일</th>
	            <td colspan="3">
	        		<fmt:formatDate value="${board.createdAt}" pattern="yyyy년 MM월 dd일 HH:mm:ss" />
	    		</td>
	        </tr>
	        <tr class="content-row">
	            <th colspan="4">내용</th>
	        </tr>
	        <tr class="content-row">
	            <td colspan="4" class="content-cell">${board.content}</td>
	        </tr>
	
	        <!-- 댓글 입력 섹션 -->
	        <tr>
	            <th colspan="4">댓글</th>
	        </tr>
	        <tr>
			    <td colspan="4">
			        <div class="comment-container">
			            <textarea id="comment" name="comment" placeholder="댓글을 입력하세요"></textarea>
			            <button type="submit" id="comment-submit" class="comment-button">작성</button>
			        </div>
			        <input type="hidden" id="boardId" name="boardId" value="${board.boardId}" />
			    </td>
			</tr>
	
	
	        <!-- 댓글 표시 섹션 -->
			<c:forEach var="comment" items="${comments}">
			    <tr>
			        <td colspan="4">
			            <div class="comment-row">
			                <div class="comment-content">
			                    <strong>${comment.userName}</strong>: ${comment.content}
			                    <span class="comment-time">${comment.formattedCreatedAt}</span>
			                </div>
			                <c:if test="${user.userId==comment.userId}">
			                    <button type="button" class="comment-delete" data-comment-id="${comment.commentId}">삭제</button>
			                </c:if>
			            </div>
			        </td>
			    </tr>
			</c:forEach>
	    </table>
	    
	    <!-- 버튼 컨테이너 -->
	    <div class="button-container">
	        <c:if test="${user.userId == board.userId}">
	            <form action="/team3webnovel/gije/delete/${board.boardId}" method="post" style="display:inline;">
	                <button type="submit" class="delete-button">게시글 삭제</button>
	            </form>
	        </c:if>
	        <a href="/team3webnovel/gije/board?page=${currentPage}" class="back-button">글 목록으로 돌아가기</a>
	    </div>
	</div>
</body>
</html>
