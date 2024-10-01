<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 1200px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }
        .image-card {
            width: 200px;
            padding: 20px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            text-align: center;
            transition: transform 0.3s ease;
        }
        .image-card:hover {
            transform: translateY(-10px);
        }
        .image-card img {
            width: 100%;
            height: auto;
            border-radius: 8px;
        }
        .image-card h2 {
            font-size: 1.2em;
            margin: 10px 0;
            color: #555;
        }
        .image-card button {
            padding: 10px 20px;
            background-color: #28a745;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .image-card button:hover {
            background-color: #218838;
        }
	</style>
</head>
<body>
    <div class="container">
        <h1>게시판</h1>
        <div class="grid">
            <%-- 모델 리스트를 반복문으로 출력 --%>
            <c:forEach var="image" items="${list}">
                <div class="image-card">
                    <!-- 모델의 이름에 맞는 이미지 파일 출력 -->
                    <img src="${image.imageUrl }" alt="모델 예시 이미지">
                    <!-- 모델 이름 출력 -->
                    <h2><c:out value="${image.creationId}"/></h2>
                    <!-- 모델 선택 버튼 -->
                    <button onclick="changeModel('${model}')">상세 보기</button>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
