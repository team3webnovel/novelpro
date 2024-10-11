<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AI 창작 스튜디오</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    <style>
        /* 전체 페이지 스타일 */
        body {
            background: linear-gradient(to right, #ff6f61, #ff9671);
            font-family: 'Poppins', sans-serif;
            color: white;
            text-align: center;
        }

        /* 페이지 중앙 정렬 */
        .center-container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* 환영 메시지 스타일 */
        h1, h3, h4, h5, .tools, .start-btn {
            opacity: 0; /* 처음에 안 보이도록 설정 */
            transition: opacity 1s ease-in-out;
        }

        h1 {
            font-size: 3.5rem;
            font-weight: bold;
            text-transform: uppercase;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3); /* 텍스트 그림자 추가 */
        }

        h3 {
            font-size: 1.8rem;
            margin-bottom: 10px;
            color: #fffbea; /* 밝은 색상 */
        }

        h4 {
            font-size: 2rem;
            font-style: italic;
            margin-top: 20px;
            background-color: rgba(0, 0, 0, 0.2);
            padding: 10px 20px;
            border-radius: 10px;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3); /* 텍스트 그림자 추가 */
            animation: pulse 1.5s infinite; /* 강조 애니메이션 */
        }

        h5 {
            font-size: 1.5rem;
            margin-top: 10px;
            color: #ffe6e1;
        }

        /* 애니메이션 추가: 강조 효과 */
        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
        }

        /* 작업 시작 버튼 */
        .start-btn {
            background-color: #0056b3;
            color: white;
            padding: 15px 30px;
            border: none;
            font-size: 1.2rem;
            border-radius: 5px;
            text-transform: uppercase;
            margin-top: 30px;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .start-btn:hover {
            background-color: #003f7f;
            transform: scale(1.1);
        }

        /* 도구 소개 스타일 */
        .tools {
            margin-top: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 20px;
        }

        /* 각 도구 단계 스타일 */
        .tool-step {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        /* 단계 설명 스타일 */
        .step-description {
            font-size: 1rem;
            font-weight: bold;
            background-color: rgba(0, 0, 0, 0.2);
            padding: 10px 20px;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
        }

        /* 화살표 스타일 */
        .arrow {
            font-size: 2rem;
            color: white;
            font-weight: bold;
        }

        /* 어두운 배경 오버레이 */
        #darkOverlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.85); /* 어두운 배경 */
            display: none; /* 기본적으로 보이지 않음 */
            z-index: 10; /* 화면 최상단에 위치 */
        }

        /* 소설 구상하기 스타일 (초기 상태) */
        #novelPlanning {
            /* 클릭 전에는 아무런 스타일이 없음 */
            cursor: pointer; /* 마우스를 가져가면 손가락 모양 */
        }

        /* 소설 구상하기 강조 (버튼 클릭 후 적용될 스타일) */
        .highlight-novelPlanning {
            background-color: #fff;
            color: #333;
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.3);
            z-index: 20; /* 어두운 배경 위에 위치 */
            transition: all 0.5s ease;
        }

    </style>
</head>
<body>

<!-- 어두운 배경 오버레이 -->
<div id="darkOverlay"></div>


<div class="center-container">
    <h1>${AImessage}</h1>
    <h3>AI가 당신의 창작을 도와드립니다.</h3>
    <h4>준비물은 오직 당신의 '창의력'뿐!</h4>
    <h5>아이디어만 있다면, 나머지는 AI가 도와드립니다.</h5>
    
    <!-- 창작 시작 버튼 -->
    <a href="#" class="start-btn">작업 시작하기</a>

    <!-- 제공 도구 소개 -->
    <div class="tools">
        <div class="tool-step">
            <div class="step-description" id="novelPlanning">📖 소설 구상 및 구조화</div>
            <div class="arrow">→</div>
        </div>
        <div class="tool-step">
            <div class="step-description">🎨 웹소설 표지 제작</div>
            <div class="arrow">→</div>
        </div>
        <div class="tool-step">
            <div class="step-description">🔤 맞춤형 표지 폰트 생성</div>
            <div class="arrow">→</div>
        </div>
        <div class="tool-step">
            <div class="step-description">🎵 내 소설에 어울리는 BGM 생성</div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // 페이지 로드 후 0.5초 뒤에 요소들이 나타나도록 설정
    window.onload = function() {
        setTimeout(function() {
            document.querySelector('h1').style.opacity = '1';  // h1 요소 보이기
            document.querySelector('h3').style.opacity = '1';  // h3 요소 보이기
            document.querySelector('h4').style.opacity = '1';  // 준비물: 창의력 보이기
            document.querySelector('h5').style.opacity = '1';  // 준비물 설명 보이기
            document.querySelector('.start-btn').style.opacity = '1';  // 작업 시작 버튼 보이기
            document.querySelector('.tools').style.opacity = '1';  // 도구 소개 보이기
        }, 500);  // 0.5초 후에 나타남
    }

    document.querySelector('.start-btn').addEventListener('click', function(event) {
        event.preventDefault(); // 기본 동작 방지

        // 화면 어두워지기
        const overlay = document.getElementById('darkOverlay');
        overlay.style.display = 'block';

        // 소설 구상하기 강조 애니메이션
        const novelPlanning = document.getElementById('novelPlanning');
        novelPlanning.classList.add('highlight-novelPlanning');
        
        // "소설 구상하기" 클릭 시 POST 방식으로 이동
        novelPlanning.addEventListener('click', function() {
            // 동적으로 폼을 생성하여 POST 방식으로 전송
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '<%=request.getContextPath()%>/creation-studio/novel'; // 컨텍스트 경로 포함

            // 필요한 입력값이 있으면 input 요소를 동적으로 추가 가능
            // 예: hidden 필드
            const hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = 'novelTitle';
            hiddenField.value = 'My New Novel';
            form.appendChild(hiddenField);

            // 폼을 DOM에 추가하고 제출
            document.body.appendChild(form);
            form.submit();
        });

        // 오버레이 클릭 시 초기화
        overlay.addEventListener('click', function() {
            overlay.style.display = 'none';  // 어두운 배경 제거
            novelPlanning.classList.remove('highlight-novelPlanning');  // 강조 해제
        });
    });

</script>

</body>
</html>
