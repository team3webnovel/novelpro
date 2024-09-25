<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f9;
        margin: 0;
        padding: 0;
    }

    .container {
        width: 50%;
        margin: 50px auto;
        padding: 20px;
        background-color: white;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        box-sizing: border-box; /* 패딩과 테두리를 포함한 너비 계산 */
    }

    h2 {
        text-align: center;
        color: #333;
        margin-bottom: 20px;
    }

    label {
        font-size: 16px;
        color: #333;
        display: block;
        margin-bottom: 8px;
    }

    input[type="text"], textarea {
        width: 100%;
        padding: 10px;
        margin-bottom: 20px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 14px;
        box-sizing: border-box; /* 패딩과 테두리를 포함한 너비 계산 */
    }

    input[type="text"]:focus, textarea:focus {
        border-color: #95A2DB;
        outline: none;
        box-shadow: 0 0 5px rgba(149, 162, 219, 0.5);
    }

    textarea {
        resize: vertical;
    }

    input[type="submit"] {
        background-color: #D979B1;
        color: white;
        border: none;
        padding: 12px 20px;
        font-size: 16px;
        cursor: pointer;
        border-radius: 4px;
        width: 100%;
        transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
        background-color: #c068a5;
    }

    .form-group {
        margin-bottom: 20px;
    }

    .back-button {
        background-color: #95A2DB;
        color: white;
        border: none;
        padding: 12px 20px;
        font-size: 16px;
        cursor: pointer;
        border-radius: 4px;
        width: 100%;
        text-align: center;
        text-decoration: none;
        display: block;
        margin-top: 10px;
        box-sizing: border-box; /* 패딩과 테두리를 포함한 너비 계산 */
    }

    .back-button:hover {
        background-color: #7f8bc1;
    }

</style>
</head>
<body>
    <div class="container">
        <h2>게시글 작성</h2>
        
        <form action="/team3webnovel/gije/write" method="POST">
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" required>
            </div>
            
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content" rows="10" required></textarea>
            </div>
            
            <input type="submit" value="작성">
        </form>

        <!-- 글 목록으로 돌아가기 버튼 -->
        <a href="/team3webnovel/gije/list" class="back-button">글 목록으로 돌아가기</a>
    </div>
</body>
</html>
