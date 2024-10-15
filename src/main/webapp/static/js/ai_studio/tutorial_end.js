document.addEventListener("DOMContentLoaded", function () {
    // 첫 번째 오디오 요소를 찾아 재생
    const firstAudio = document.querySelector('audio');
    if (firstAudio) {
        firstAudio.play();
    }

    // 어두운 배경 오버레이 생성 함수
    function createOverlay() {
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
    }

    // 텍스트를 타이핑하는 애니메이션 함수
    function typeText(text, callback) {
        const tutorialText = document.createElement('div');
        tutorialText.id = 'tutorialText';
        tutorialText.style.position = 'fixed';
        tutorialText.style.top = '50%';
        tutorialText.style.left = '50%';
        tutorialText.style.transform = 'translate(-50%, -50%)';
        tutorialText.style.color = '#fff';
        tutorialText.style.fontSize = '24px';
        tutorialText.style.textAlign = 'center';
        tutorialText.style.zIndex = '9999';
        document.body.appendChild(tutorialText);

        let index = 0;
        function typing() {
            if (index < text.length) {
                tutorialText.innerHTML += text.charAt(index);
                index++;
                setTimeout(typing, 100);  // 각 글자마다 100ms 지연
            } else if (callback) {
                callback();  // 타이핑이 끝나면 콜백 실행
            }
        }
        typing();  // 타이핑 시작
    }

    // 스킵 버튼 생성 함수
    function createSkipButton() {
        const skipButton = document.createElement('button');
        skipButton.innerHTML = '스킵';
        skipButton.style.position = 'fixed';
        skipButton.style.top = '20px';
        skipButton.style.right = '20px';
        skipButton.style.padding = '10px 20px';
        skipButton.style.fontSize = '16px';
        skipButton.style.zIndex = '10000';
        skipButton.id = 'skipButton';
        document.body.appendChild(skipButton);

        skipButton.addEventListener('click', function () {
            endTutorial();  // 스킵 시 튜토리얼 종료
        });
    }

    // 'tuto' 버튼 강조 함수
    function highlightTutoButton() {
        const tutoButton = document.getElementById('tuto');
        if (tutoButton) {
            tutoButton.style.zIndex = '10000';  // 강조 효과 추가
            tutoButton.style.position = 'relative';
            tutoButton.style.boxShadow = '0 0 10px 5px yellow';  // 노란색 외곽선 적용
            tutoButton.style.border = '2px solid yellow';  // 노란색 테두리 추가
        }
    }

    // 종료 버튼 생성 함수
    function createExitButton() {
        const tutorialText = document.getElementById('tutorialText');  // tutorialText 요소 가져오기
        const textRect = tutorialText.getBoundingClientRect();  // tutorialText 위치와 크기 가져오기

        const exitButton = document.createElement('button');
        exitButton.innerHTML = '종료';
        exitButton.style.position = 'absolute';
        exitButton.style.top = (textRect.bottom + 20) + 'px';  // 텍스트 바로 아래 20px 간격으로 배치
        exitButton.style.left = '50%';
        exitButton.style.transform = 'translateX(-50%)';  // 가로 중앙 정렬
        exitButton.style.padding = '10px 20px';
        exitButton.style.fontSize = '18px';
        exitButton.style.zIndex = '10000';
        exitButton.id = 'exitButton';
        document.body.appendChild(exitButton);

        exitButton.addEventListener('click', function () {
            endTutorial();  // '종료' 버튼을 누르면 튜토리얼 종료
            exitButton.remove();  // 버튼 제거
        });
    }

    // 튜토리얼 종료 함수
    function endTutorial() {
        const overlay = document.getElementById('darkOverlay');
        const tutorialText = document.getElementById('tutorialText');
        const skipButton = document.getElementById('skipButton');
        const exitButton = document.getElementById('exitButton');
        const tutoButton = document.getElementById('tuto');

        // 강조 효과 제거
        if (tutoButton) {
            tutoButton.style.zIndex = '';  // 원래 zIndex로 복원
            tutoButton.style.position = '';  // 원래 위치로 복원
            tutoButton.style.boxShadow = '';  // 외곽선 제거
            tutoButton.style.border = '';  // 테두리 제거
        }

        // 요소 제거
        if (overlay) overlay.remove();
        if (tutorialText) tutorialText.remove();
        if (skipButton) skipButton.remove();
        if (exitButton) exitButton.remove();
    }

    // 튜토리얼 시작
    createOverlay();  // 오버레이 생성
	highlightTutoButton();  // 'tuto' 버튼 강조
    typeText("이제 보관함에 가서 내가 만든 콘텐츠를 확인해보세요!", function () {
        createExitButton();  // 종료 버튼 생성
    });
    createSkipButton();  // 스킵 버튼 생성
});
