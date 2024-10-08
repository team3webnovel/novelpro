<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>모델 선택</title>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
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
        	margin-bottom: 5px;
            padding: 20px 20px 10px 20px;
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


        /* 체크박스 스타일링을 위한 추가 레이블 */
        label input{
			display:none;
		}
		
		label {
		    align-items: center;
		    cursor: pointer; /* 커서 포인터로 변경 */
		}
		
		label span {
			display: inline-block;
		    padding: 5px 10px; /* 여백 추가 */
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
    <script src="<%= request.getContextPath()%>/static/js/image_simple_form.js"></script>
</head>
<body>
    <div class="container">
        <h1>모델 선택</h1>
        <div class="row">
            <div class="col-md-4">
			    <div class="card">
			        <!-- Bootstrap Carousel -->
			        <div id="modelCarouselPony" class="carousel slide" data-ride="carousel" data-interval="3000">
			            <div class="carousel-inner">
			                <div class="carousel-item active">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/prefectPonyXL_v3.png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/prefectPonyXL_v3(2).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/prefectPonyXL_v3(3).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/prefectPonyXL_v3(4).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/prefectPonyXL_v3(5).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/prefectPonyXL_v3(6).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/prefectPonyXL_v3(7).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/prefectPonyXL_v3(8).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			            </div>
			            <!-- Carousel controls -->
			            <a class="carousel-control-prev" href="#modelCarouselPony" role="button" data-slide="prev">
			                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			                <span class="sr-only">이전</span>
			            </a>
			            <a class="carousel-control-next" href="#modelCarouselPony" role="button" data-slide="next">
			                <span class="carousel-control-next-icon" aria-hidden="true"></span>
			                <span class="sr-only">다음</span>
			            </a>
			        </div>
			        <!-- Card Body -->
			        <div class="card-body">
			            <h5 class="card-title">prefectPonyXL_v3</h5>
			            <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('prefectPonyXL_v3.safetensors')">모델 선택</button>
			            <button class="btn btn-success" onclick="changeModelAIModal('prefectPonyXL_v3.safetensors')">직접 입력</button>
			        </div>
			    </div>
			</div>
            
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/romanticprism_v10.png" class="card-img-top" alt="모델 예시 이미지" onclick="showImageInModal(this)">
                    <div class="card-body">
                        <h5 class="card-title">romanticprism_v10</h5>
                        <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('romanticprism_v10.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelAIModal('romanticprism_v10.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/prefectPonyXL_v2CleanedStyle.png" class="card-img-top" alt="모델 예시 이미지">
                    <div class="card-body">
                        <h5 class="card-title">animagineXLV31_v31</h5>
                        <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('animagineXLV31_v31.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelAIModal('animagineXLV31_v31.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
			    <div class="card">
			        <!-- Bootstrap Carousel -->
			        <div id="modelCarouselWaiAni" class="carousel slide" data-ride="carousel" data-interval="3000">
			            <div class="carousel-inner">
			                <div class="carousel-item active">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/waiANINSFWPONYXL_v80.png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/waiANINSFWPONYXL_v80(2).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/waiANINSFWPONYXL_v80(3).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			                <div class="carousel-item">
			                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/waiANINSFWPONYXL_v80(4).png" class="d-block w-100" alt="모델 예시 이미지 1" onclick="showImageInModal(this)">
			                </div>
			            </div>
			            <!-- Carousel controls -->
			            <a class="carousel-control-prev" href="#modelCarouselWaiAni" role="button" data-slide="prev">
			                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			                <span class="sr-only">이전</span>
			            </a>
			            <a class="carousel-control-next" href="#modelCarouselWaiAni" role="button" data-slide="next">
			                <span class="carousel-control-next-icon" aria-hidden="true"></span>
			                <span class="sr-only">다음</span>
			            </a>
			        </div>
			        <!-- Card Body -->
			        <div class="card-body">
			            <h5 class="card-title">waiANINSFWPONYXL_v80</h5>
			            <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('waiANINSFWPONYXL_v80.safetensors')">모델 선택</button>
			            <button class="btn btn-success" onclick="changeModelAIModal('waiANINSFWPONYXL_v80.safetensors')">직접 입력</button>
			        </div>
			    </div>
			</div>
            
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/model_example_images/aamXLAnimeMix_v10.png" class="card-img-top" alt="모델 예시 이미지" onclick="showImageInModal(this)">
                    <div class="card-body">
                        <h5 class="card-title">aamXLAnimeMix_v10(이거 되게 별로네)</h5>
                        <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('aamXLAnimeMix_v10.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelAIModal('aamXLAnimeMix_v10.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/prefectPonyXL_v2CleanedStyle.png" class="card-img-top" alt="모델 예시 이미지">
                    <div class="card-body">
                        <h5 class="card-title">juggernautXL_juggXIByRundiffusion</h5>
                        <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('juggernautXL_juggXIByRundiffusion.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelAIModal('juggernautXL_juggXIByRundiffusion.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/prefectPonyXL_v2CleanedStyle.png" class="card-img-top" alt="모델 예시 이미지">
                    <div class="card-body">
                        <h5 class="card-title">dreamshaper_8</h5>
                        <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('dreamshaper_8.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelAIModal('dreamshaper_8.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/prefectPonyXL_v2CleanedStyle.png" class="card-img-top" alt="모델 예시 이미지">
                    <div class="card-body">
                        <h5 class="card-title">majicmixRealistic</h5>
                        <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('majicmixRealistic.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelAIModal('majicmixRealistic.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath()%>/static/images/prefectPonyXL_v2CleanedStyle.png" class="card-img-top" alt="모델 예시 이미지">
                    <div class="card-body">
                        <h5 class="card-title">sdxlNijiSeven_sdxlNijiSeven.safetensors</h5>
                        <button class="btn btn-success mt-2 mb-2" onclick="changeModelChoiceModal('sdxlNijiSeven_sdxlNijiSeven.safetensors')">모델 선택</button>
                        <button class="btn btn-success" onclick="changeModelAIModal('sdxlNijiSeven_sdxlNijiSeven.safetensors')">직접 입력</button>
                    </div>
                </div>
            </div>
            
        </div>
    </div>
    
    <div id="choiceModal" class="modal fade">
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
	
	          <div class="hidden mt-1">
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
	
	          <div class="hidden mt-1">
	            <label><input type="checkbox" value="hair ribbon"><span> 헤어 리본</span></label>
		        <label><input type="checkbox" value="crown"><span> 왕관</span></label>
		        <label><input type="checkbox" value="hairband"><span> 머리띠</span></label>
		        <label><input type="checkbox" value="hairpin"><span> 머리핀</span></label>
		        <label><input type="checkbox" value=""><span> 선택 안함</span></label>
	          </div>
	          
	          <div class="hidden mt-1">
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
	          
	          <div class="hidden mt-1">
	            <label><input type="checkbox" value="school uniform"><span> 교복</span></label>
		        <label><input type="checkbox" value="armor"><span> 갑옷</span></label>
		        <label><input type="checkbox" value="shirt"><span> 셔츠</span></label>
		        <label><input type="checkbox" value="robe"><span> 로브</span></label>
		        <label><input type="checkbox" value="magical girl"><span> 마법소녀</span></label>
		        <label><input type="checkbox" value="shrine maiden"><span> 무녀복</span></label>
		        <label><input type="checkbox" value="long sleeves"><span> 긴 소매</span></label>
		        <label><input type="checkbox" value="earring"><span> 귀걸이</span></label>
		        <label><input type="checkbox" value="choker"><span> 초커</span></label>
	          </div>
	          
	          <div class="hidden mt-1">
	            <label><input type="checkbox" value="simple background"><span> 간단한 배경</span></label>
		        <label><input type="checkbox" value="pink background"><span> 분홍 배경</span></label>
		        <label><input type="checkbox" value="heart background"><span> 하트 배경</span></label>
		        <label><input type="checkbox" value="night sky"><span> 밤하늘</span></label>
		        <label><input type="checkbox" value="in park"><span> 공원</span></label>
		        <label><input type="checkbox" value="Inside the cave"><span> 동굴 안</span></label>
		        <label><input type="checkbox" value="in castle"><span> 성 안</span></label>
		        <label><input type="checkbox" value="cliff"><span> 절벽</span></label>
	          </div>
	          
	          <div class="hidden mt-1">
	          	<label><input type="checkbox" value="cherry blossoms"><span> 벚꽃</span></label>
	            <label><input type="checkbox" value="animal ears"><span> 동물귀</span></label>
		    	<label><input type="checkbox" value="thighhighs"><span> 사이하이</span></label>
		    	<label><input type="checkbox" value="thighs"><span> 허벅지</span></label>
	          </div>
	
	          <button type="submit" class="btn btn-primary mt-3">이미지 생성</button>
	          <div id="spinner1" style="display:none;" class="mt-3">Loading...</div>
	        </form>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div id="AIModal" class="modal fade">
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
	          <div id="spinner2" style="display:none;" class="mt-3">Loading...</div>
	        </form>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Image Modal -->
	<div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="imageModalLabel" aria-hidden="true">
	    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="imageModalLabel">이미지 확대 보기</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <div class="modal-body">
	                <img id="modalImage" src="" class="img-fluid" alt="확대된 이미지">
	            </div>
	        </div>
	    </div>
	</div>
</body>
</html>
