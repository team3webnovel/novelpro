<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>이미지 결과</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 20px;
        }
        img {
            max-width: 100%;
            height: auto;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h1>이미지 결과</h1>

    <div id="imageContainer">
	    <c:forEach var="imageData" items="${imageDataList}">
		    <div class="image-container">
		        <img src="data:image/png;base64,${imageData}" alt="Generated Image" />
		        <c:if test="${not empty prompt }">
	        		<p>"${prompt }"</p>
	        	</c:if>
		    </div>
		</c:forEach>
    </div>
</body>
</html>
