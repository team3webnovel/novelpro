<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세보기</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<jsp:include page="/WEB-INF/views/includes/header.jsp" />
	<script src="<%= request.getContextPath()%>/static/js/board_view.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin-top: 60px;
        }

        .content-row td {
            white-space: normal;
            line-height: 1.6;
        }

        .button-container {
            text-align: right;
            margin-top: 20px;
        }

        .btn {
            margin-left: 10px;
        }

        /* 댓글 섹션 */
        textarea {
            width: 100%;
            height: 100px;
            resize: none;
        }

        .comment-row {
            border-bottom: 1px solid #ddd;
            padding: 10px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="mb-4">게시글 상세보기</h1>
    <table class="table table-bordered">
        <tr>
            <th style="width: 15%;">번호</th>
            <td style="width: 35%;">${board.boardId}</td>
            <th style="width: 15%;">제목</th>
            <td style="width: 35%;">${board.title}</td>
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
                <fmt:formatDate value="${board.createdAt}" pattern="yyyy년 MM월 dd일 HH:mm:ss"/>
            </td>
        </tr>
        <tr class="content-row">
            <th colspan="4">내용</th>
        </tr>
        <tr class="content-row">
            <td colspan="4">${board.content}</td>
        </tr>

        <!-- 댓글 입력 섹션 -->
        <tr>
            <th colspan="4">댓글</th>
        </tr>
        <tr>
            <td colspan="4">
            <div class="comment-container">
                <textarea id="comment" name="comment" placeholder="댓글을 입력하세요"></textarea>
                <button type="submit" id="comment-submit" class="btn btn-primary mt-2">작성</button>
            </div>
            <input type="hidden" id="boardId" name="boardId" value="${board.boardId}" />
            </td>
        </tr>

        <!-- 댓글 표시 섹션 -->
        <c:forEach var="comment" items="${comments}">
            <tr class="comment-row">
                <td colspan="4">
                    <strong>${comment.userName}</strong>: ${comment.content}
                    <c:if test="${user.userId == comment.userId}">
                        <button type="button" class="btn btn-danger btn-sm float-right" data-comment-id="${comment.commentId}">삭제</button>
                    </c:if>
                    <span class="float-right text-muted">${comment.formattedCreatedAt}</span>
                    
                </td>
            </tr>
        </c:forEach>
    </table>

    <!-- 버튼 컨테이너 -->
    <div class="button-container">
        <c:if test="${user.userId == board.userId}">
            <form id="deleteForm" style="display:inline;">
                <input type="hidden" id="boardId" name="boardId" value="${board.boardId}" />
                <button type="button" class="btn btn-danger">게시글 삭제</button>
            </form>
        </c:if>
        <a href="/team3webnovel/board?page=${currentPage}" class="btn btn-secondary">글 목록으로 돌아가기</a>
    </div>
</div>
</body>
</html>
