document.addEventListener("DOMContentLoaded", function () {
    const tutorialTexts = [
        "이제 BGM을 만들어볼 차례예요.",
        "노래로 표현하고 싶은 상황이 있나요?",
        "혹은 원하는 노래의 주제가 있나요?",
        "어떤 장르의 노래가 좋을까요?",
        "가사는 있는 게 좋을까요? 아니면 없는 게 좋을까요?",
        "당신의 상상력을 펼쳐보세요!"
    ];

    let currentStep = 0;

    const tutorialOverlay = document.createElement('div');
    tutorialOverlay.id = 'tutorialOverlay';
    tutorialOverlay.style.position = 'fixed';
    tutorialOverlay.style.top = '0';
    tutorialOverlay.style.left = '0';
    tutorialOverlay.style.width = '100%';
    tutorialOverlay.style.height = '100%';
    tutorialOverlay.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    tutorialOverlay.style.display = 'flex';
    tutorialOverlay.style.justifyContent = 'center';
    tutorialOverlay.style.alignItems = 'center';
    tutorialOverlay.style.zIndex = '1000';
    tutorialOverlay.style.color = 'white';
    tutorialOverlay.style.fontSize = '1.5rem';

    const tutorialContent = document.createElement('div');
    tutorialContent.id = 'tutorialContent';
    tutorialContent.style.textAlign = 'center';
    
    const tutorialTextElement = document.createElement('p');
    tutorialTextElement.id = 'tutorialText';
    tutorialTextElement.style.fontSize = '1.2rem';

    const nextButton = document.createElement('button');
    nextButton.id = 'nextButton';
    nextButton.textContent = '다음';
    nextButton.style.display = 'none';
    nextButton.style.backgroundColor = '#007bff';
    nextButton.style.color = 'white';
    nextButton.style.border = 'none';
    nextButton.style.padding = '10px 20px';
    nextButton.style.marginTop = '20px';
    nextButton.style.cursor = 'pointer';
    nextButton.style.fontSize = '1rem';

    const endButton = document.createElement('button');
    endButton.id = 'endButton';
    endButton.textContent = '종료';
    endButton.style.display = 'none';
    endButton.style.backgroundColor = '#007bff';
    endButton.style.color = 'white';
    endButton.style.border = 'none';
    endButton.style.padding = '10px 20px';
    endButton.style.marginTop = '20px';
    endButton.style.cursor = 'pointer';
    endButton.style.fontSize = '1rem';

    const skipButton = document.createElement('button');
    skipButton.id = 'skipButton';
    skipButton.textContent = '스킵';
    skipButton.style.position = 'absolute';
    skipButton.style.top = '20px';
    skipButton.style.right = '20px';
    skipButton.style.backgroundColor = '#007bff';
    skipButton.style.color = 'white';
    skipButton.style.border = 'none';
    skipButton.style.padding = '10px 20px';
    skipButton.style.cursor = 'pointer';

    // "instrumentalOption" 강조 함수
    function toggleHighlightInstrumentalOption(highlight) {
		const checkBox = document.getElementById('make_instrumental');
        if (highlight) {
			checkBox.style.boxShadow = '0 0 10px 5px yellow';
			checkBox.style.position = 'relative';
			checkBox.style.zIndex = '10001';
        } else {
            instrumentalOption.style.boxShadow = 'none';  // 강조 효과 제거
        }
    }

    function typeText(text) {
        tutorialTextElement.textContent = '';  
        let typingIndex = 0;
        
        function typeChar() {
            tutorialTextElement.textContent += text.charAt(typingIndex);
            typingIndex++;
            if (typingIndex < text.length) {
                setTimeout(typeChar, 100);
            } else if (currentStep < tutorialTexts.length - 1) {
                nextButton.style.display = 'inline-block';  // 마지막이 아닌 경우에만 '다음' 버튼 표시
            } else {
                nextButton.style.display = 'none';  // 마지막 단계에서는 '다음' 버튼 숨김
                endButton.style.display = 'inline-block';  // 종료 버튼 표시
            }
        }
        typeChar();  
    }

    typeText(tutorialTexts[currentStep]);

    nextButton.addEventListener("click", function () {
        currentStep++;
        nextButton.style.display = 'none';  

        // 가사 여부를 묻는 질문일 때 "instrumentalOption" 강조
        if (currentStep === 4) {
            toggleHighlightInstrumentalOption(true);  // 강조
        } else {
            toggleHighlightInstrumentalOption(false);  // 강조 제거
        }

        if (currentStep < tutorialTexts.length) {
            typeText(tutorialTexts[currentStep]);  
        }
    });

    skipButton.addEventListener("click", function () {
        tutorialOverlay.style.display = 'none';
        toggleHighlightInstrumentalOption(false);  // 강조 제거
    });

    endButton.addEventListener("click", function () {
        tutorialOverlay.style.display = 'none';
        toggleHighlightInstrumentalOption(false);  // 강조 제거
    });

    tutorialContent.appendChild(tutorialTextElement);
    tutorialContent.appendChild(nextButton);
    tutorialContent.appendChild(endButton);
    tutorialOverlay.appendChild(tutorialContent);
    tutorialOverlay.appendChild(skipButton);
    document.body.appendChild(tutorialOverlay);
});
