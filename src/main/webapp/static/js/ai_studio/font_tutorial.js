document.addEventListener("DOMContentLoaded", function() {
    startTutorial();  // 페이지가 로드되면 튜토리얼 시작
});

function startTutorial() {
    // 어두운 배경 오버레이 생성
    createOverlay();
    
    // 첫 번째 메시지 출력
    typeText("방금 만든 표지에 폰트를 꾸며보세요!", function() {
        // '다음' 버튼 생성
        createNextButton(stepTwo, 'tutorialText'); // 'tutorialText' 아래에 버튼 위치
    });

    // 스킵 버튼 생성
    createSkipButton();
}

// 어두운 배경 생성
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

// 설명을 화면에 보여주는 함수
function typeText(text, callback) {
    const overlayText = document.createElement('div');
    overlayText.id = 'tutorialText';
    overlayText.style.position = 'fixed';
    overlayText.style.top = '50%';
    overlayText.style.left = '50%';
    overlayText.style.transform = 'translate(-50%, -50%)';
    overlayText.style.color = '#fff';
    overlayText.style.fontSize = '24px';
    overlayText.style.zIndex = '9999';
    document.body.appendChild(overlayText);

    let index = 0;
    function typing() {
        if (index < text.length) {
            overlayText.innerHTML += text.charAt(index);
            index++;
            setTimeout(typing, 100);  // 각 글자마다 100ms 지연
        } else if (callback) {
            callback();  // 타이핑이 끝나면 콜백 실행
        }
    }
    typing();
}

// '다음' 버튼 생성 함수
function createNextButton(nextStepFunction, referenceElementId) {
    const referenceElement = document.getElementById(referenceElementId);

    const nextButton = document.createElement('button');
    nextButton.innerHTML = '다음';
    nextButton.style.position = 'absolute';
    nextButton.style.top = (referenceElement.getBoundingClientRect().bottom + 20) + 'px';  // 텍스트 바로 아래에 위치
    nextButton.style.left = '50%';
    nextButton.style.transform = 'translateX(-50%)';
    nextButton.style.padding = '10px 20px';
    nextButton.style.fontSize = '18px';
    nextButton.style.zIndex = '10000';
    nextButton.id = 'nextButton';
    document.body.appendChild(nextButton);

    nextButton.addEventListener('click', function() {
        nextButton.remove();  // '다음' 버튼 제거
        nextStepFunction();   // 다음 단계로 이동
    });
}

// '스킵' 버튼 생성 함수
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

    skipButton.addEventListener('click', function() {
        endTutorial();  // 스킵 시 튜토리얼 종료
    });
}

// 튜토리얼 종료 함수 수정
function endTutorial() {
    const overlay = document.getElementById('darkOverlay');
    const tutorialText = document.getElementById('tutorialText');
    const skipButton = document.getElementById('skipButton');
    const exitButton = document.getElementById('exitButton');
    const highlightedElements = document.querySelectorAll('.highlight');  // 모든 강조된 요소들

    // 요소 제거
    if (overlay) overlay.remove();
    if (tutorialText) tutorialText.remove();
    if (skipButton) skipButton.remove();
    if (exitButton) exitButton.remove();

    // 모든 강조된 요소에서 highlight 클래스 제거
    highlightedElements.forEach(function (element) {
        element.classList.remove('highlight');
    });

    console.log("Tutorial Ended");
}


// 예시 단계 2: 이미지 업로드 작업 설명
function stepTwo() {
    // 첫 번째 메시지를 지우기
    const tutorialText = document.getElementById('tutorialText');
    if (tutorialText) tutorialText.remove();

    // 버튼 강조
    highlightButtons();

    // 두 번째 메시지 출력: 상단에서 180px 아래에 표시
    typeTextAtPosition("먼저, 폰트 작업을 할 이미지를 업로드합니다.", 100, function() {
        // '다음' 버튼 생성
        createNextButton(stepThree, 'tutorialText');
    });
}

// 상단에서 지정된 px 아래에 텍스트를 표시하는 함수
function typeTextAtPosition(text, topPosition, callback) {
    const overlayText = document.createElement('div');
    overlayText.id = 'tutorialText';
    overlayText.style.position = 'fixed';
    overlayText.style.top = topPosition + 'px';  // 지정된 위치로 설정
    overlayText.style.left = '50%';
    overlayText.style.transform = 'translateX(-50%)';
    overlayText.style.color = '#fff';
    overlayText.style.fontSize = '24px';
    overlayText.style.zIndex = '9999';
    document.body.appendChild(overlayText);

    let index = 0;
    function typing() {
        if (index < text.length) {
            overlayText.innerHTML += text.charAt(index);
            index++;
            setTimeout(typing, 100);  // 각 글자마다 100ms 지연
        } else if (callback) {
            callback();  // 타이핑이 끝나면 콜백 실행
        }
    }
    typing();
}

// 예시 단계 3: 버튼 강조
function stepThree() {
    // 두 번째 메시지를 지우기
    const tutorialText = document.getElementById('tutorialText');
    if (tutorialText) tutorialText.remove();

            removeHighlightButtons();
            highlightToolbar();

}

// 버튼 강조 함수
function highlightButtons(callback) {
    const imageUploadBtn = document.getElementById('imageUpload');
    const openModalBtn = document.getElementById('openModalBtn');

    // 강조 스타일 적용
    imageUploadBtn.classList.add('highlight');
    openModalBtn.classList.add('highlight');

    // 1초 후 콜백 실행
    if (callback) {
        setTimeout(callback, 1000);
    }
}

// 버튼 강조 해제 함수
function removeHighlightButtons() {
    const imageUploadBtn = document.getElementById('imageUpload');
    const openModalBtn = document.getElementById('openModalBtn');

    // 강조 스타일 제거
    imageUploadBtn.classList.remove('highlight');
    openModalBtn.classList.remove('highlight');
}

// 툴바 강조 함수
function highlightToolbar() {
    const toolbars = document.querySelectorAll('.toolbar'); // 여러 툴바 요소를 가져옴

    toolbars.forEach(toolbar => {
        // .highlight 클래스 추가
        toolbar.classList.add('highlight');
    });

    // 새로운 메시지 출력
    typeTextAtPosition("텍스트 박스를 생성하고 글꼴, 글자 크기, 색상 등을 조정해보세요.", 130, function() {
        console.log("Toolbar highlighted and text displayed.");
        createNextButton(stepFour, 'tutorialText');
    });
}

function stepFour() {
    const toolbars = document.querySelectorAll('.toolbar'); // 여러 툴바 요소를 가져옴

    toolbars.forEach(toolbar => {
        toolbar.classList.remove('highlight'); // .highlight 클래스 제거
    });
    
    const saveBtn = document.getElementById('saveBtn');
    saveBtn.classList.add('highlight');
    
    const tutorialText = document.getElementById('tutorialText');
    if (tutorialText) tutorialText.remove();
    
    typeTextAtPosition("작업을 완료한 후 저장 버튼을 눌러주세요.", 480, function() {
        createNextButton(stepFive, 'tutorialText');
    });
}

function stepFive() {
	
	const saveBtn = document.getElementById('saveBtn');
	saveBtn.classList.remove('highlight');
	
    const tutorialText = document.getElementById('tutorialText');
    if (tutorialText) tutorialText.remove();

    // 새로운 메시지 출력
    typeTextAtPosition("이제 직접 폰트를 만들어보세요!", 150, function() {
        // '종료' 버튼 생성
        createExitButton();

        // 2초 후 자동 종료
        setTimeout(function() {
            endTutorial();
        }, 2000);  // 2초 후 튜토리얼 종료
    });
}

// '종료' 버튼 생성 함수
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

    exitButton.addEventListener('click', function() {
        endTutorial();  // '종료' 버튼을 누르면 튜토리얼 종료
        exitButton.remove();  // 버튼 제거
    });
}

