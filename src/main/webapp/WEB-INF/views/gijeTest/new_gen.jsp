<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>모델 선택</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        /* 기존 CSS는 필요한 부분만 남기고 수정 */
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }
        .container {
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
        .card {
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            text-align: center;
            transition: transform 0.3s ease;
        }
        .card:hover {
            transform: translateY(-10px);
        }
        .card img {
            width: 100%;
            height: auto;
            border-radius: 8px;
        }
        .card h2 {
            font-size: 1.2em;
            margin: 10px 0;
            color: #555;
        }
        .card button {
            padding: 10px 20px;
            background-color: #28a745;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .card button:hover {
            background-color: #218838;
        }

        /* 체크박스 스타일링을 위한 추가 레이블 */
        label input{
			display:none;
		}
		
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
        
        .hidden {
		    height: 0;
		    opacity: 0;
		    visibility: hidden;
		    transition: opacity 0.5s ease, height 0.5s ease;
		}
		.visible {
		    height: auto;
		    opacity: 1;
		    visibility: visible;
		    transition: opacity 0.5s ease, height 0.5s ease;
		}
		@media (max-width: 768px) {
		    .grid {
		        flex-direction: column;
		    }
		}

    </style>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath()%>/static/js/new_gen.js"></script>
</head>
<body>
    <div class="container">
        <h1>모델 선택</h1>
        <div class="row">
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/prefectPonyXL_v3.png" class="card-img-top" alt="모델 예시 이미지">
                    <div class="card-body">
                        <h5 class="card-title">prefectPonyXL_v3</h5>
                        <button class="btn btn-success" onclick="changeModel('prefectPonyXL_v3.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelVer2('prefectPonyXL_v3.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card" onclick="changeModel('romanticprism_v10.safetensors')">
                    <img src="<%= request.getContextPath()%>/static/images/ComfyUI_00403_.png" class="card-img-top" alt="모델 예시 이미지">
                    <div class="card-body">
                        <h5 class="card-title">romanticprism_v10</h5>
                        <button class="btn btn-success" onclick="changeModel('romanticprism_v10.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelVer2('romanticprism_v10.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            <div class="col-md-4" onclick="changeModel('animagineXLV31_v31.safetensors')">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/prefectPonyXL_v2CleanedStyle.png" class="card-img-top" alt="모델 예시 이미지">
                    <div class="card-body">
                        <h5 class="card-title">animagineXLV31_v31</h5>
                        <button class="btn btn-success" onclick="changeModel('animagineXLV31_v31.safetensors')">모델 선택</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div id="myModal" class="modal fade">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">이미지 생성</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form id="create">
	          <div>
	            <label><input type="checkbox" class="option-trigger" value="1girl, high resolution, Beautiful detailed eyes"><span> 여자</span></label>
	            <label><input type="checkbox" class="option-trigger" value="1boy, man"><span> 남자</span></label>
	          </div>
	
	          <div class="hidden mt-3">
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
	
	          <div class="hidden mt-3">
	            <label><input type="checkbox" value="hair ribbon"><span> 헤어 리본</span></label>
		        <label><input type="checkbox" value="crown"><span> 왕관</span></label>
		        <label><input type="checkbox" value="hairband"><span> 머리띠</span></label>
		        <label><input type="checkbox" value="hairpin"><span> 머리핀</span></label>
		        <label><input type="checkbox" value=""><span> 선택 안함</span></label>
	          </div>
	          
	          <div class="hidden mt-3">
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
	          
	          <div class="hidden mt-3">
	            <label><input type="checkbox" value="school uniform"><span> 교복</span></label>
		        <label><input type="checkbox" value="armor"><span> 갑옷</span></label>
		        <label><input type="checkbox" value="shirt"><span> 셔츠</span></label>
		        <label><input type="checkbox" value="robe"><span> 로브</span></label>
		        <label><input type="checkbox" value="magical girl"><span> 마법소녀</span></label>
		        <label><input type="checkbox" value="long sleeves"><span> 긴 소매</span></label>
		        <label><input type="checkbox" value="earring"><span> 귀걸이</span></label>
		        <label><input type="checkbox" value="choker"><span> 초커</span></label>
	          </div>
	          
	          <div class="hidden mt-3">
	            <label><input type="checkbox" value="simple background"><span> 간단한 배경</span></label>
		        <label><input type="checkbox" value="pink background"><span> 분홍 배경</span></label>
		        <label><input type="checkbox" value="heart background"><span> 심장 배경</span></label>
		        <label><input type="checkbox" value="night sky"><span> 밤하늘</span></label>
		        <label><input type="checkbox" value="in park"><span> 공원</span></label>
		        <label><input type="checkbox" value="Inside the cave"><span> 동굴 안</span></label>
		        <label><input type="checkbox" value="in castle"><span> 성 안</span></label>
		        <label><input type="checkbox" value="cliff"><span> 절벽</span></label>
	          </div>
	          
	          <div class="hidden mt-3">
	            <label><input type="checkbox" value="animal ears"><span> 동물귀</span></label>
		    	<label><input type="checkbox" value="thighhighs"><span> 사이하이</span></label>
		    	<label><input type="checkbox" value="thighs"><span> 선택 안함</span></label>
	          </div>
	
	          <button type="submit" class="btn btn-primary mt-3">이미지 생성</button>
	          <div id="spinner" style="display:none;" class="mt-3">Loading...</div>
	        </form>
	      </div>
	    </div>
	  </div>
	</div>
	<div id="myModal2" class="modal fade">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">이미지 생성</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form id="create-with-ai">
			  <div class="form-group">
          		<textarea id="comment" class="form-control" rows="4"></textarea>
        	  </div>
	          <button type="submit" class="btn btn-primary mt-3">이미지 생성</button>
	          <div id="spinner" style="display:none;" class="mt-3">Loading...</div>
	        </form>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>
