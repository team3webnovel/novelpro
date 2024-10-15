let tutorialRunning = true; // 전역 플래그 변수: 튜토리얼이 실행 중인지 여부

window.onload = function () {
    // 어두운 배경 생성
    const overlay = document.createElement('div');
    overlay.id = 'darkOverlay';
    overlay.style.position = 'fixed';
    overlay.style.top = 0;
    overlay.style.left = 0;
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    overlay.style.zIndex = '9999';
    document.body.appendChild(overlay);

    // 설명 텍스트 생성 (중앙 배치)
    const explanationText = document.createElement('div');
    explanationText.id = 'explanationText';
    explanationText.style.color = '#fff';
    explanationText.style.fontSize = '1.5rem';
    explanationText.style.textAlign = 'center';
    explanationText.style.position = 'fixed';
    explanationText.style.top = '50%';
    explanationText.style.left = '50%';
    explanationText.style.transform = 'translate(-50%, -50%)';
    explanationText.style.zIndex = '10002';
    document.body.appendChild(explanationText);

    // 첫 번째 단계 (장르 선택 강조)
    stepOne();

    // 스킵 버튼 생성
    createSkipButton();

    // 1. 첫 번째 단계: 장르 선택 부분 강조
    function stepOne() {
        const genreSelect = document.getElementById('genre');
        highlightElement(genreSelect);
        genreSelect.scrollIntoView({ behavior: 'smooth' });

        // 타이핑 애니메이션
        typeText('먼저 내가 쓰는 글의 장르를 선택합니다.', function () {
            if (tutorialRunning) { // 튜토리얼이 실행 중일 때만 다음 버튼 생성
                createNextButton(genreSelect, stepTwo); // 첫 번째 "다음" 버튼 생성
            }
        });
    }
	
	// 모든 강조 초기화 함수
	function resetHighlight() {
	    const highlightedElements = document.querySelectorAll('.highlight');
	    highlightedElements.forEach(element => {
	        element.classList.remove('highlight');
	        element.style.zIndex = ''; // z-index 초기화
	        element.style.position = ''; // position 초기화
	        element.style.boxShadow = ''; // box-shadow 초기화
	        element.style.border = ''; // border 초기화
	    });
	}

	// 요소 강조 함수
	function highlightElement(element) {
	    element.classList.add('highlight');
	    element.style.position = 'relative';  // 강조할 때만 상대 위치 적용
	    element.style.zIndex = '10001';       // z-index 설정
	}
	
    // 2. 두 번째 단계: 사용자 입력 영역 강조
    function stepTwo() {
        resetHighlight();
        const inputArea = document.getElementById('input-area');
        inputArea.classList.add('highlight');
        inputArea.scrollIntoView({ behavior: 'smooth' });

        // 텍스트 초기화 후 타이핑 애니메이션
        explanationText.innerHTML = '';
        typeText('챗봇과의 대화를 통해 시놉시스를 작성해보세요!', function () {
            if (tutorialRunning) { // 튜토리얼이 실행 중일 때만 다음 버튼 생성
                createNextButton(inputArea, stepThree); // 두 번째 "다음" 버튼 생성
            }
        });
    }

    // 3. 세 번째 단계: 줄거리 입력 강조 및 "종료" 버튼
    function stepThree() {
        const inputArea = document.getElementById('input-area');
        inputArea.classList.remove('highlight'); // 이전 강조 제거
        const introInput = document.getElementById('intro');
        introInput.classList.add('highlight');
		introInput.style.position = 'relative';
		introInput.style.zIndex = '10001';
        introInput.scrollIntoView({ behavior: 'smooth' });

        explanationText.innerHTML = ''; // 텍스트 초기화
        typeText('이제 당신의 글을 소개할 시간이에요!', function () {
            if (tutorialRunning) { // 튜토리얼이 실행 중일 때만 종료 버튼 생성
                createExitButton(); // "종료" 버튼 생성
            }
        });
    }

    // 타이핑 애니메이션 함수
    function typeText(text, callback) {
        let index = 0;
        explanationText.innerHTML = ''; // 이전 텍스트 초기화
        function typing() {
            if (!tutorialRunning) return; // 튜토리얼이 중단되면 타이핑 중단
            if (index < text.length) {
                explanationText.innerHTML += text.charAt(index);
                index++;
                setTimeout(typing, 100);
            } else if (callback) {
                callback(); // 타이핑이 끝나면 콜백 실행
            }
        }
        typing();
    }

    // "다음" 버튼 생성 함수
    function createNextButton(targetElement, nextStepFunction) {
        const nextButton = document.createElement('button');
        nextButton.innerHTML = '다음';
        nextButton.style.position = 'absolute';
        nextButton.style.top = targetElement.getBoundingClientRect().bottom + window.scrollY + 30 + 'px';
        nextButton.style.left = targetElement.getBoundingClientRect().left + (targetElement.offsetWidth / 2) - 30 + 'px';
        nextButton.style.padding = '10px 20px';
        nextButton.style.fontSize = '1.2rem';
        nextButton.style.zIndex = '10001';
        nextButton.id = 'nextButton'; // ID 추가
        document.body.appendChild(nextButton);

        nextButton.addEventListener('click', function () {
            nextButton.remove(); // "다음" 버튼을 제거하고 다음 단계로 이동
            nextStepFunction();
        });
    }

    // "종료" 버튼 생성 함수
    function createExitButton() {
        const exitButton = document.createElement('button');
        exitButton.innerHTML = '종료';

        // 설명 텍스트 바로 아래에 종료 버튼을 배치
        const explanationRect = explanationText.getBoundingClientRect(); // 설명 텍스트의 위치 계산

        exitButton.style.position = 'relative';
        exitButton.style.top = `${explanationRect.bottom + window.scrollY + 20}px`;  // 설명 텍스트 아래 20px 위치
        exitButton.style.left = `${(window.innerWidth / 2) - 50}px`;  // 화면 중앙에 위치
        exitButton.style.padding = '10px 20px';
        exitButton.style.fontSize = '1.2rem';
        exitButton.style.zIndex = '10001';
        exitButton.id = 'exitButton';
        document.body.appendChild(exitButton);

        // 2초 후 튜토리얼 자동 종료
        setTimeout(function () {
            closeTutorial(); // 2초 후 튜토리얼 종료
        }, 2000);

        exitButton.addEventListener('click', function () {
            closeTutorial(); // 종료 버튼 클릭 시 튜토리얼 종료
        });
    }

    // "스킵" 버튼 생성 함수 (오른쪽 상단에 고정)
    function createSkipButton() {
        const skipButton = document.createElement('button');
        skipButton.innerHTML = '스킵';
        skipButton.style.position = 'fixed';
        skipButton.style.top = '20px';
        skipButton.style.right = '20px';
        skipButton.style.padding = '10px 20px';
        skipButton.style.fontSize = '1.2rem';
        skipButton.style.zIndex = '10001'; // 항상 맨 위에 표시
        skipButton.id = 'skipButton'; // ID 추가
        document.body.appendChild(skipButton);

        // 스킵 버튼 클릭 시 튜토리얼 종료
        skipButton.addEventListener('click', function () {
            tutorialRunning = false; // 튜토리얼 실행 중단
            closeTutorial(); // 튜토리얼 종료
        });
    }

    // 튜토리얼 종료 함수
    function closeTutorial() {
        const overlay = document.getElementById('darkOverlay');
        const genreSelect = document.getElementById('genre');
        const inputArea = document.getElementById('input-area');
        const introInput = document.getElementById('intro');
        const explanationText = document.getElementById('explanationText');

        // 강조 및 오버레이 제거
        if (genreSelect) genreSelect.classList.remove('highlight');
        if (inputArea) inputArea.classList.remove('highlight');
        if (introInput) introInput.classList.remove('highlight');
        if (overlay) overlay.remove();
        if (explanationText) explanationText.remove();

        // 버튼 제거
        const exitButton = document.getElementById('exitButton');
        const skipButton = document.getElementById('skipButton');
        const nextButton = document.getElementById('nextButton'); // "다음" 버튼 참조 추가
        if (exitButton) exitButton.remove();
        if (skipButton) skipButton.remove();
        if (nextButton) nextButton.remove(); // "다음" 버튼 제거
    }
};
