<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>모델 선택</title>
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
        .model-card {
            width: 200px;
            padding: 20px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            text-align: center;
            transition: transform 0.3s ease;
        }
        .model-card:hover {
            transform: translateY(-10px);
        }
        .model-card img {
            width: 100%;
            height: auto;
            border-radius: 8px;
        }
        .model-card h2 {
            font-size: 1.2em;
            margin: 10px 0;
            color: #555;
        }
        .model-card button {
            padding: 10px 20px;
            background-color: #28a745;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .model-card button:hover {
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
		
		h3 {
		    margin-top: 20px; /* h3 상단 여백 추가 */
		    color: #333; /* h3 텍스트 색상 */
		}
		
		div > label {
		    margin-bottom: 10px; /* 라벨 간 여백 추가 */
		}
		
		button {
		    background-color: #28a745; /* 버튼 색상 */
		    color: white; /* 버튼 텍스트 색상 */
		    padding: 10px 20px; /* 버튼 패딩 */
		    border: none; /* 테두리 제거 */
		    border-radius: 5px; /* 둥근 모서리 */
		    cursor: pointer; /* 커서 포인터 */
		    margin-top: 20px; /* 버튼 상단 여백 추가 */
		}
		
		button:hover {
		    background-color: #218838; /* 버튼 호버 색상 */
		}
		
		label input{
			display:none;
		}
		
		/* 체크박스 스타일링을 위한 추가 레이블 */
		label {
		    align-items: center;
		    cursor: pointer; /* 커서 포인터로 변경 */
		    padding: 10px 0; /* 위아래 패딩 추가 */
		}
		
		label span {
			display: inline-block;
		    padding: 5px 10px; /* 여백 추가 */
		    margin-bottom: 10px;
		    border: 2px solid #ddd; /* 기본 테두리 */
		    border-radius: 5px; /* 둥근 모서리 */
		    transition: background-color 0.3s, border-color 0.3s; /* 애니메이션 효과 */
		}
				
		label input:checked + span {
			background-color:#E07D3A;
			color: white;
			border-color: #E0433A; /* 체크된 테두리 색상 */
		}
	
		/* 애니메이션 효과가 적용될 클래스 */
		.hidden {
		    transform: scale(0); /* 처음에 축소 상태 */
            transition: transform 0.5s ease; /* 애니메이션 추가 */
		}
		
		.visible {
		    transform: scale(1); /* 확대 상태로 변경 */
		    transition: transform 0.5s ease; /* 애니메이션 추가 */
		}
    </style>
    <script src="<%= request.getContextPath()%>/static/js/test123.js"></script>
</head>
<body>
    <div class="container">
        <h1>모델 선택</h1>
        <div class="grid">
            <%-- 모델 리스트를 반복문으로 출력 --%>
            <c:forEach var="model" items="${models}">
                <div class="model-card">
                    <!-- 모델의 이름에 맞는 이미지 파일 출력 -->
                    <img src="/example_images/${model}.png" alt="모델 예시 이미지">
                    <!-- 모델 이름 출력 -->
                    <h2><c:out value="${model}"/></h2>
                    <!-- 모델 선택 버튼 -->
                    <button onclick="changeModel('${model}')">모델 선택</button>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <div id="myModal" class="modal">
    	<div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <form id="create">
        	<div>
        		<label>
        			<input type="checkbox" value="1girl, high resolution, Beautiful detailed eyes">
        			<span> 여자</span>
        		</label>
        		<label><input type="checkbox" value="1boy, man"><span> 남자</span></label>
        	</div>
		    
		    <div class="hidden">
		        <label><input type="checkbox" value="black hair"><span> 검은 머리</span></label>
		        <label><input type="checkbox" value="blonde hair"><span> 금발 머리</span></label>
		        <label><input type="checkbox" value="silver hair"><span> 은발 머리</span></label>
		        <label><input type="checkbox" value="red hair"><span> 붉은 머리</span></label>
		        <label><input type="checkbox" value="long hair"><span> 긴 머리</span></label>
		        <label><input type="checkbox" value="short hair"><span> 짧은 머리</span></label>
		        <label><input type="checkbox" value="ponytail hair"><span> 포니테일</span></label>
		        <label><input type="checkbox" value="twin tails hair"><span> 트윈테일</span></label>
		        <label><input type="checkbox" value=""><span> 선택 안함</span></label>
		    </div>
		    
		    <div class="hidden">
		    	<label><input type="checkbox" value="hair ribbon"><span> 헤어 리본</span></label>
		        <label><input type="checkbox" value="crown"><span> 왕관</span></label>
		        <label><input type="checkbox" value="hairband"><span> 머리띠</span></label>
		        <label><input type="checkbox" value="hairpin"><span> 머리핀</span></label>
		        <label><input type="checkbox" value=""><span> 선택 안함</span></label>
		    </div>
		

		    <div class="hidden">
		        <label><input type="checkbox" value="blue eyes"><span> 파란 눈</span></label>
		        <label><input type="checkbox" value="red eyes"><span> 빨간 눈</span></label>
		        <label><input type="checkbox" value="black eyes"><span> 검은 눈</span></label>
		        <label><input type="checkbox" value="brown eyes"><span> 갈색 눈</span></label>
		        <label><input type="checkbox" value="smile"><span> 미소</span></label>
		        <label><input type="checkbox" value="blush"><span> 블러쉬</span></label>
		        <label><input type="checkbox" value="open mouth"><span> 열린 입</span></label>
		        <label><input type="checkbox" value="eyelashes"><span> 속눈썹</span></label>
		        <label><input type="checkbox" value="tongue"><span> 혀</span></label>
		        <label><input type="checkbox" value=""><span> 선택 안함</span></label>
		    </div>
		
		    <div class="hidden">
		        <label><input type="checkbox" value="school uniform"><span> 교복</span></label>
		        <label><input type="checkbox" value="armor"><span> 갑옷</span></label>
		        <label><input type="checkbox" value="shirt"><span> 셔츠</span></label>
		        <label><input type="checkbox" value="robe"><span> 로브</span></label>
		        <label><input type="checkbox" value="magical girl"><span> 마법소녀</span></label>
		        <label><input type="checkbox" value="long sleeves"><span> 긴 소매</span></label>
		        <label><input type="checkbox" value="earring"><span> 귀걸이</span></label>
		        <label><input type="checkbox" value="choker"><span> 초커</span></label>
		    </div>
		
		    <div class="hidden">
		    	<label><input type="checkbox" value="simple background"><span> 간단한 배경</span></label>
		        <label><input type="checkbox" value="pink background"><span> 분홍 배경</span></label>
		        <label><input type="checkbox" value="heart background"><span> 심장 배경</span></label>
		        <label><input type="checkbox" value="night sky"><span> 밤하늘</span></label>
		        <label><input type="checkbox" value="in park"><span> 공원</span></label>
		        <label><input type="checkbox" value="Inside the cave"><span> 동굴 안</span></label>
		        <label><input type="checkbox" value="in castle"><span> 성 안</span></label>
		        <label><input type="checkbox" value="cliff"><span> 절벽</span></label>
		    </div>
		    
		    <div class="hidden">
		    	<label><input type="checkbox" value="animal ears"><span> 동물귀</span></label>
		    	<label><input type="checkbox" value="thighhighs"><span> 사이하이</span></label>
		    	<label><input type="checkbox" value=""><span> 선택 안함</span></label>
		    </div>

		    <button type="submit">이미지 생성</button>
		    <div id="spinner" style="display:none;">Loading...</div> <!-- 스피너 추가 -->
		</form>
    </div>
</div>
</body>
</html>
