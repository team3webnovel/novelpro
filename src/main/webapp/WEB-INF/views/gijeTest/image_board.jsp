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
        
        .modal {
		    display: none; /* 기본적으로 숨김 */
		    position: fixed;
		    z-index: 1;
		    left: 0;
		    top: 0;
		    width: 100%;
		    height: 100%;
		    overflow: auto;
		    background-color: rgb(0, 0, 0);
		    background-color: rgba(0, 0, 0, 0.4);
		}
		
		.close {
		    color: #aaa;
		    float: right;
		    font-size: 28px;
		    font-weight: bold;
		}
		
		.close:hover,
		.close:focus {
		    color: black;
		    text-decoration: none;
		    cursor: pointer;
		}
		
		.modal-content {
		    background-color: #fefefe;
		    margin: 15% auto;
		    padding: 20px;
		    border: 1px solid #888;
		    width: 80%;
		    border-radius: 8px; /* 테두리 둥글게 */
		    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
		    max-height: 90vh; /* 화면 높이의 90%로 최대 높이를 설정하여 화면 밖으로 넘치지 않도록 함 */
    		overflow-y: auto; /* 세로로 스크롤 가능 */
		}
	</style>
	<script src="<%= request.getContextPath()%>/static/js/image_board.js"></script>
</head>
<body>
    <div class="container">
        <h1>게시판</h1>
        <div class="grid">
            <c:forEach var="image" items="${list}">
                <div class="image-card" onclick="openModal('${image.imageUrl}', ${image.creationId})">
                    <img src="${image.imageUrl }" alt="이미지">
                </div>
            </c:forEach>
        </div>
    </div>
    <div id="myModal" class="modal">
    	<div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <img id="modalImage" src="" alt="이미지" style="max-width: 100%; height: auto;"/>
        <p id="modalContent"></p>
		</div>
	</div>
</body>
</html>
