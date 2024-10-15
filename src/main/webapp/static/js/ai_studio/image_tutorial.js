let tutorialRunning = true; // 튜토리얼이 실행 중인지 확인하는 전역 플래그

// 어두운 배경 생성
function createOverlay() {
	if (tutorialRunning) {
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
}

// 설명 텍스트 생성 (중앙 배치)
function createExplanationText() {
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
    explanationText.style.maxWidth = '80%';  // 텍스트 폭 제한
    document.body.appendChild(explanationText);
}

// 타이핑 애니메이션 함수
function typeText(text, callback) {
    const explanationText = document.getElementById('explanationText');
    let index = 0;
    let typingTimeout;

    explanationText.innerHTML = ''; // 이전 텍스트 초기화

    function typing() {
        if (!tutorialRunning) return; // 튜토리얼이 중단되면 타이핑 중단
        if (index < text.length) {
            explanationText.innerHTML += text.charAt(index);
            index++;
            typingTimeout = setTimeout(typing, 100); // 각 글자마다 100ms 지연
        } else {
            clearTimeout(typingTimeout); // 타이핑 끝났을 때 타임아웃 해제
            if (callback) {
                callback(); // 타이핑이 끝나면 콜백 실행
            }
        }
    }
    typing();
}


// "다음" 버튼 생성 함수
function createNextButton(nextStepFunction) {
    const nextButton = document.createElement('button');
    const explanationText = document.getElementById('explanationText');
    nextButton.innerHTML = '다음';
    nextButton.style.position = 'absolute';
    nextButton.style.top = `${explanationText.getBoundingClientRect().bottom + 20}px`; // 설명 텍스트 아래에 위치
    nextButton.style.left = '50%';
    nextButton.style.transform = 'translateX(-50%)';
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

// "스킵" 버튼 생성 함수
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
    const explanationText = document.getElementById('explanationText');
    exitButton.innerHTML = '종료';
    exitButton.style.position = 'absolute';
    exitButton.style.top = `${explanationText.getBoundingClientRect().bottom + 20}px`; // 설명 텍스트 아래에 위치
    exitButton.style.left = '50%';
    exitButton.style.transform = 'translateX(-50%)';
    exitButton.style.padding = '10px 20px';
    exitButton.style.fontSize = '1.2rem';
    exitButton.style.zIndex = '10002';
    exitButton.id = 'exitButton';
    document.body.appendChild(exitButton);

    exitButton.addEventListener('click', function() {
        closeTutorial(); // 튜토리얼 종료
    });
}

// 튜토리얼 종료 함수 (강조 및 모든 관련 요소 제거)
function closeTutorial() {
	tutorialRunning = false; // 튜토리얼 종료
    // 모든 오버레이 및 강조된 요소 제거
    const overlay = document.getElementById('darkOverlay');
    const explanationText = document.getElementById('explanationText');
    const highlightedElements = document.querySelectorAll('.highlight');

    // 강조된 모든 요소의 스타일 초기화
    highlightedElements.forEach(element => {
        element.classList.remove('highlight');
        element.style.border = '';
        element.style.boxShadow = '';
        element.style.animation = '';
    });

    // 오버레이 및 설명 텍스트 제거
    if (overlay) overlay.remove();
    if (explanationText) explanationText.remove();

    // 버튼 제거
    const exitButton = document.getElementById('exitButton');
    const skipButton = document.getElementById('skipButton');
    const nextButton = document.getElementById('nextButton');
    const arrow = document.getElementById('highlightArrow');
    
    if (exitButton) exitButton.remove();
    if (skipButton) skipButton.remove();
    if (nextButton) nextButton.remove();
    if (arrow) arrow.remove();

    // 기타 추가된 요소나 애니메이션, 이벤트 리스너 제거
    const tutoButton = document.getElementById('tuto');
    if (tutoButton) {
        tutoButton.style.border = '';
        tutoButton.style.boxShadow = '';
        tutoButton.style.animation = '';
        tutoButton.style.zIndex = '0';
    }

    const tutoButton2 = document.getElementById('tuto2');
    if (tutoButton2) {
        tutoButton2.style.border = '';
        tutoButton2.style.boxShadow = '';
        tutoButton2.style.animation = '';
        tutoButton2.style.zIndex = '0';
    }
	
    const tutoButton3 = document.getElementById('tuto3');
    if (tutoButton3) {
        tutoButton3.classList.remove('highlight');
        tutoButton3.style.border = '';
        tutoButton3.style.boxShadow = '';
        tutoButton3.style.zIndex = '0';
    }
	
    $('.modal').modal('hide');
	
	// 추가적으로 모달 이미지에 적용된 highlight 클래스도 제거
	$('#imageModal').on('shown.bs.modal', function () {
	const modalImages = document.querySelectorAll('.carousel-item.highlight');
	modalImages.forEach(img => {
	    img.classList.remove('highlight');
	});
	})
	
	clearAllTimeouts(); // 모든 타이머 제거
}

$(window).on('load', function() {
    $('img').each(function() {
        const img = new Image();
        img.src = $(this).attr('src');
    });
});

let hasScrolled = false; // 스크롤이 이미 발생했는지 확인하는 플래그

function highlightImages(noScroll = false) {
	
	highlightModalImages();
	
    const images = document.querySelectorAll('.card-img-top');
    
    images.forEach(img => {
        if (!img.classList.contains('highlight')) { // 중복 방지
            img.classList.add('highlight');
            console.log('Added highlight to image:', img);
        }

        if (!noScroll && !hasScrolled) {
            img.scrollIntoView({ behavior: 'smooth' });
        }
    });
	

    if (!noScroll && !hasScrolled) {
        hasScrolled = true;

        setTimeout(() => {
            images[0].scrollIntoView({ behavior: 'smooth', block: 'end' });

            setTimeout(() => {
                window.scrollTo({ top: 0, behavior: 'smooth' });
            }, 1000);
        }, 500);
    }
}

function highlightModalImages() {
	const items = document.querySelectorAll('.carousel-inner');
	items.forEach(item => {
		item.classList.add('highlight');
	})
}


// 각 단계별 함수
function stepTwo() {
    const explanationText = document.getElementById('explanationText');
    explanationText.innerHTML = '';
    typeText("어떤 등장인물을 표지에 등장시킬 건가요?", function() {
        createNextButton(stepThree);
    });
}

function stepThree() {
    const explanationText = document.getElementById('explanationText');
    explanationText.innerHTML = '';
    typeText("배경에는 어떤 게 있으면 좋을까요?", function() {
        createNextButton(stepFour);
    });
}

function stepFour() {
    const explanationText = document.getElementById('explanationText');
    explanationText.innerHTML = '';
    typeText("이제 원하는 그림체를 골라...", function() {
        highlightImages();
    	highlightModalImages();
        createNextButton(stepFive);
    });
}

function stepFive() {
	const explanationText = document.getElementById('explanationText');
	explanationText.innerHTML = '';
	highlightElementWithArrow('tuto');
	stepSix();
}
function stepSix() {
	const tutoButton = document.getElementById('tuto');
	if (tutoButton) {
		// 기존 이벤트 리스너를 제거하고 새로운 이벤트 리스너를 추가 (중복 방지)
		tutoButton.removeEventListener('click', handleTutoClick);  // 중복 방지
		tutoButton.addEventListener('click', handleTutoClick, { once: true });
	}
}

function stepSeven() {
    if (tutorialRunning) {  // 튜토리얼이 종료되었으면 더 이상 실행되지 않도록 처리

    const explanationText = document.getElementById('explanationText');
    explanationText.innerHTML = '';
    $('#choiceModal').modal('hide');

    highlightImages(true);
    highlightModalImages();
    highlightElementWithArrow('tuto2');
	
	if (tutorialRunning) {  // 튜토리얼이 실행 중일 때만 stepEight 실행
	    stepEight();

    // 'tuto2' 버튼 클릭 시 'stepEight' 실행
    const tutoButton2 = document.getElementById('tuto2');
    if (tutoButton2) {
        tutoButton2.removeEventListener('click', stepEight);  // 중복 방지
        tutoButton2.addEventListener('click', function() {

        });
        
        // 2초 후 자동으로 tuto2 버튼 클릭
        setTimeout(function() {
            if (tutorialRunning) {  // 튜토리얼이 종료되지 않았을 때만 클릭
                tutoButton2.click();
                console.log("Button with id 'tuto2' clicked.");
            }
        }, 2000);  // 2초 후 클릭
    }
}
}
}


function stepEight() {
	
	if (tutorialRunning) {// 튜토리얼이 중단되면 더 이상 실행되지 않도록 함
	
    const highlightedElements = document.querySelectorAll('.highlight');
    highlightedElements.forEach(element => {
        element.classList.remove('highlight');
        element.style.border = '';
        element.style.boxShadow = '';
        element.style.animation = '';
    });

    // tuto 버튼에서 스타일 제거
    const tutoButton = document.getElementById('tuto2');
    if (tutoButton) {
        tutoButton.style.border = '';
        tutoButton.style.boxShadow = '';
        tutoButton.style.animation = '';
        tutoButton.style.zIndex = '0';
    }
	const arrow = document.getElementById('highlightArrow');
	if (arrow) {
		arrow.remove();
	}
	
	const form = document.getElementById('tuto3');
	if (form) { 
		form.style.position = 'relative';
    	form.classList.add('highlight');
    }
	// 어두운 배경 제거
	const overlay = document.getElementById('darkOverlay');
	if (overlay) {
	    overlay.remove();
	}
	clearAllTimeouts();
	// 2초간 화면을 보여주고 오버레이와 타이핑 애니메이션을 다시 실행
	setTimeout(() => {
	    // 어두운 배경 다시 생성
	    createOverlay();
	    
	    // 타이핑 애니메이션 실행
	    typeText("직접 원하는 프롬프트를 입력해볼 수도 있어요.", function() {
	        createNextButton(stepNine);
	    });
	}, 2000); // 2초 후에 실행
}
}
// 모든 타이머 초기화 함수
function clearAllTimeouts() {
    let id = window.setTimeout(function() {}, 0);
    while (id--) {
        window.clearTimeout(id); // 모든 타임아웃 제거
    }
}
// 기존 이벤트 리스너 제거 함수
function removeAllListeners() {
    const allElements = document.querySelectorAll('*');  // 모든 DOM 요소 선택
    allElements.forEach(element => {
        const clone = element.cloneNode(true);
        element.parentNode.replaceChild(clone, element);  // 기존 이벤트 리스너 제거
    });
}

function stepNine() {
	clearAllTimeouts();
	removeAllListeners();
	
	const explanationText = document.getElementById('explanationText');
	explanationText.innerHTML = '';
	typeText("이제 당신만의 웹소설 표지를 만들어보세요!", function() {
		createExitButton();
		setTimeout(() => {
		closeTutorial();
		}, 2000);
	})
}

function handleTutoClick() {
	
	if (tutorialRunning) {
    // 강조된 모든 요소에서 강조 효과 제거
    const highlightedElements = document.querySelectorAll('.highlight');
    highlightedElements.forEach(element => {
        element.classList.remove('highlight');
        element.style.border = '';
        element.style.boxShadow = '';
        element.style.animation = '';
    });

    // tuto 버튼에서 스타일 제거
    const tutoButton = document.getElementById('tuto');
    if (tutoButton) {
        tutoButton.style.border = '';
        tutoButton.style.boxShadow = '';
        tutoButton.style.animation = '';
        tutoButton.style.zIndex = '0';
    }

    // choiceModal 강조 적용
    const choiceModal = document.querySelector('#choiceModal .modal-content');
    if (choiceModal) {
        choiceModal.style.position = 'relative';
        choiceModal.style.zIndex = '10010';
        choiceModal.classList.add('highlight');
    }

    // hidden 되어있는 모달 펼치기
    const hiddenElements = document.querySelectorAll('.hidden');
    hiddenElements.forEach(element => {
        element.classList.remove('hidden');
        element.classList.add('visible');
    });

    // 어두운 배경 제거
    const overlay = document.getElementById('darkOverlay');
    if (overlay) {
        overlay.remove();
    }
	
	const arrow = document.getElementById('highlightArrow');
	if (arrow) {
		arrow.remove();
	}

	    // 1초 후 오버레이 다시 생성하고 타이핑 메시지와 다음 버튼 표시
    setTimeout(() => {
        createOverlay();
        typeText("이곳에서 원하는 키워드를 고르거나...", function() {
            createNextButton(stepSeven);
        });
    }, 2000); // 2초간 사용자에게 화면을 보여주고 오버레이를 다시 생성
}
}

// tuto 아이디를 강조하고 화살표를 표시하는 함수
function highlightElementWithArrow(elementId) {
    const images = document.querySelectorAll('img');
    images.forEach(img => {
        img.style.animation = 'none'; // 이미지 애니메이션 중지
    });

    const element = document.getElementById(elementId);
    
    if (element) {
	    // 강조 스타일 적용
	    element.style.border = '3px solid #ffda44';
	    element.style.boxShadow = '0 0 15px rgba(255, 218, 68, 0.7)';
	    element.style.position = 'relative';
	    element.style.zIndex = '10010';  // z-index 설정
	
	    // 애니메이션 적용
	    element.style.animation = 'bounce 1s infinite';  // bounce 애니메이션

        // 화살표 생성
        const arrow = document.createElement('div');
        arrow.id = 'highlightArrow';
        arrow.innerHTML = '⬇';  // 화살표 표시
        arrow.style.position = 'absolute';
        arrow.style.fontSize = '3rem';
        arrow.style.color = '#ffda44';
        arrow.style.zIndex = '9999'; // 화살표 z-index 설정
        arrow.style.top = `${element.getBoundingClientRect().top - 50}px`; // 요소 바로 위에 표시
        arrow.style.left = `${element.getBoundingClientRect().left + (element.offsetWidth / 2) - 20}px`; // 가운데 정렬
        arrow.style.animation = 'bounce 1s infinite'; // 화살표에 bounce 애니메이션 적용
        document.body.appendChild(arrow);
		
		// 2초 후에 자동으로 클릭
		       setTimeout(function() {
		           element.click();
		           console.log("Button with id 'tuto' clicked.");
		       }, 2000); // 2초 후 클릭
		
    } else {
        console.log("Element with id " + elementId + " not found.");
    }
}



// window.onload로 함수 실행을 묶음
window.onload = function() {
    createOverlay();
    createExplanationText();
    typeText("자, 이제 당신이 만들 웹소설의 표지를 만들 시간이에요.", function() {
        createNextButton(stepTwo);
    });
    createSkipButton();
};
