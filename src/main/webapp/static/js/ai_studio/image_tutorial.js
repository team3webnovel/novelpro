let tutorialRunning = true; // 튜토리얼이 실행 중인지 확인하는 전역 플래그

window.onload = function() {
    // 어두운 배경 생성
    const overlay = document.createElement('div');
    overlay.id = 'darkOverlay';
    overlay.style.position = 'fixed';
    overlay.style.top = 0;
    overlay.style.left = 0;
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    overlay.style.zIndex = '9998';
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

    // 첫 번째 텍스트 타이핑 효과
    typeText("자, 이제 당신이 만들 웹소설의 표지를 만들 시간이에요.", function() {
        createNextButton(stepTwo);
    });

    // 스킵 버튼 생성
    createSkipButton();

    // 1. 첫 번째 단계: 키보드 타이핑과 다음 버튼 생성
    function stepTwo() {
        explanationText.innerHTML = '';
        typeText("어떤 등장인물을 표지에 등장시킬 건가요?", function() {
            createNextButton(stepThree);
        });
    }

    // 2. 두 번째 단계: 키보드 타이핑과 다음 버튼 생성
    function stepThree() {
        explanationText.innerHTML = '';
        typeText("배경에는 어떤 게 있으면 좋을까요?", function() {
            createNextButton(stepFour);
        });
    }

    // 3. 세 번째 단계: 이미지 강조 및 텍스트
    function stepFour() {
        explanationText.innerHTML = '';
        typeText("이제 원하는 그림체를 골라...", function() {
            highlightImages();
            createExitButton();
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
                setTimeout(typing, 100); // 각 글자마다 100ms 지연
            } else if (callback) {
                callback(); // 타이핑이 끝나면 콜백 실행
            }
        }
        typing();
    }

    // "다음" 버튼 생성 함수
    function createNextButton(nextStepFunction) {
        const nextButton = document.createElement('button');
        nextButton.innerHTML = '다음';
        nextButton.style.position = 'fixed';
        nextButton.style.bottom = '20px';
        nextButton.style.right = '20px';
        nextButton.style.padding = '10px 20px';
        nextButton.style.fontSize = '1.2rem';
        nextButton.style.zIndex = '10002';
        nextButton.id = 'nextButton';
        document.body.appendChild(nextButton);

        nextButton.addEventListener('click', function() {
            nextButton.remove(); // "다음" 버튼 제거
            nextStepFunction();  // 다음 단계로 이동
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
        skipButton.id = 'skipButton';
        document.body.appendChild(skipButton);

        skipButton.addEventListener('click', function() {
            tutorialRunning = false; // 튜토리얼 실행 중단
            closeTutorial(); // 튜토리얼 종료
        });
    }

    // "종료" 버튼 생성 함수
    function createExitButton() {
        const exitButton = document.createElement('button');
        exitButton.innerHTML = '종료';
        exitButton.style.position = 'fixed';
        exitButton.style.bottom = '20px';
        exitButton.style.left = '20px';
        exitButton.style.padding = '10px 20px';
        exitButton.style.fontSize = '1.2rem';
        exitButton.style.zIndex = '10002';
        exitButton.id = 'exitButton';
        document.body.appendChild(exitButton);

        exitButton.addEventListener('click', function() {
            closeTutorial(); // 튜토리얼 종료
        });
    }

    // 튜토리얼 종료 함수
    function closeTutorial() {
        const overlay = document.getElementById('darkOverlay');
        const explanationText = document.getElementById('explanationText');

        // 강조 및 오버레이 제거
        const images = document.querySelectorAll('.carousel-item');
        images.forEach(image => {
            image.classList.remove('highlight');
        });
        if (overlay) overlay.remove();
        if (explanationText) explanationText.remove();

        // 버튼 제거
        const exitButton = document.getElementById('exitButton');
        const skipButton = document.getElementById('skipButton');
        const nextButton = document.getElementById('nextButton');
        if (exitButton) exitButton.remove();
        if (skipButton) skipButton.remove();
        if (nextButton) nextButton.remove();
    }

	function highlightImages() {
		// 모든 carousel-item 선택
		const items = document.querySelectorAll('.carousel-item');
		items.forEach(item => {
		    // carousel-item에 강조 스타일 추가
		    item.classList.add('highlight');
			item.scrollIntoView({ behavior: 'smooth' });
		    console.log('Added highlight to carousel-item:', item);
		});
	};
	}