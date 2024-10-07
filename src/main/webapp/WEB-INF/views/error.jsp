<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page</title>
    <style>
        /* 기본적인 CSS 리셋 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(135deg, #f0f4f8, #d9e2ec);
            color: #333;
        }

        .error-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 600px;
            width: 100%;
        }

        .error-container h1 {
            font-size: 80px;
            margin-bottom: 20px;
            font-weight: bold;
            color: #ff6b6b;
        }

        .error-container p {
            font-size: 18px;
            margin-bottom: 20px;
            color: #555;
        }

        .error-details {
            font-size: 14px;
            color: #888;
            margin-bottom: 20px;
        }

        .error-container a {
            padding: 12px 30px;
            background-color: #4CAF50;
            color: #fff;
            border-radius: 25px;
            font-size: 16px;
            font-weight: 500;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .error-container a:hover {
            background-color: #45a049;
        }

        @media (max-width: 768px) {
            .error-container h1 {
                font-size: 60px;
            }

            .error-container p {
                font-size: 16px;
            }
        }

    </style>
</head>
<body>
    <div class="error-container">
        <h1>Error ${status}</h1> <!-- 에러 상태 코드 출력 -->
        <p>${error}</p> <!-- 전달된 오류 메시지 출력 -->
        <div class="error-details">
            <p>Details: ${message}</p> <!-- 예외 메시지 출력 -->
            <p>URL: ${url}</p> <!-- 오류 발생한 URL 출력 -->
        </div>
        <a href="${pageContext.request.contextPath}/">Go Back Home</a>
    </div>
</body>
</html>
