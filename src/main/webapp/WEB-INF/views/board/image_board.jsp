<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    
    <!-- Bootstrap CSS 추가 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    
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
        .grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }
        .card {
        	margin-bottom: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 8px;
            transition: transform 0.3s ease;
        }
        .card img{
        	
        }
        .card:hover {
            transform: translateY(-10px);
        }
        .comment-time {
		    font-size: 12px;
		    color: #999;
		    margin-left: 10px;
		}
        /* 모달 크기 조정 */
		@media (min-width: 768px) { /* md 이상의 화면 크기에서 적용 */
		    .modal-dialog {
		        max-width: 85%;
		    }
		}
		.like-container {
		    display: inline-block;
		    margin-top: 10px;
		}
		
		.like-btn {
		    background-color: transparent;
		    border: none;
		    cursor: pointer;
		    font-size: 18px;
		    color: #555;
		    display: flex;
		    align-items: center;
		}
		
		.like-btn:hover .like-icon {
		    color: #ff7675; /* 좋아요 버튼을 마우스로 올렸을 때 색상 변경 */
		}
		
		.like-icon {
		    margin-right: 5px;
		    font-size: 20px;
		    transition: color 0.3s ease;
		}
		
		.like-count {
		    font-size: 16px;
		}
    </style>

    <!-- JS 파일 추가 -->
    <script src="<%= request.getContextPath()%>/static/js/image_board.js"></script>
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-3">게시판</h1>
        <div class="row">
        	<input type="hidden" id="userId" value="${userId }">
            <c:forEach var="image" items="${list}">
                <div class="col-md-4">
                    <div class="card">
                        <img src="${image.imageUrl}" alt="이미지" class="card-img-top" onclick="openModal(${image.boardId}, '${image.imageUrl}', ${image.creationId}, '${image.content }', ${image.userId })">
                         <!-- 좋아요 버튼 및 카운트 표시 -->
						<div class="like-container">
						    <button class="like-btn" onclick="toggleLike(${image.boardId})">
						        <span class="like-icon">👍</span>
						        <span id="like-count-${image.boardId}">${image.like}</span> <!-- 좋아요 수 -->
						    </button>
						</div>     
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- 모달 -->
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	    <div class="modal-dialog modal-lg" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">이미지 정보</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <div class="modal-body">
	                <div class="row">
	                    <div class="col-md-6"> <!-- 이미지 영역 -->
	                        <img id="modalImage" src="" alt="이미지" class="img-fluid"/>
	                    </div>
	                    <div id="modal-content" class="col-md-6"> <!-- 내용 영역 -->
	                    </div>
	                </div>
	                <div class="row">
	                	<div class="col-md-12">
	                		<div id="comment-content">
	                		</div>
	                	</div>
	                </div>
	            </div>
	            
	            <div id="deleteBoard" class="modal-footer">
	            </div>
	        </div>
	    </div>
	</div>

    <!-- Bootstrap JS 및 jQuery 추가 -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
