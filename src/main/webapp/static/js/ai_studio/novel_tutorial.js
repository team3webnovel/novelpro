window.onload = function() {
    // 1. 페이지가 로드되면 화면이 어두워지고 장르 선택 부분을 강조
    const overlay = document.createElement('div');
    overlay.id = 'darkOverlay';
    document.body.appendChild(overlay);

    // 어두운 배경 스타일 적용
    overlay.style.position = 'fixed';
    overlay.style.top = 0;
    overlay.style.left = 0;
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    overlay.style.zIndex = '9999';

    // 장르 선택 부분 강조
    const genreSelect = document.getElementById('genre');
    genreSelect.classList.add('highlight');
    genreSelect.style.position = 'relative';
    genreSelect.style.zIndex = '10001';
    genreSelect.scrollIntoView({ behavior: 'smooth' });

    // 설명 텍스트 추가 (화면 정중앙에 배치)
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

    // 타이핑 애니메이션 함수
    const textToType = '먼저 내가 쓰는 글의 장르를 선택합니다.';
    let index = 0;

    function typeText(text, callback) {
        if (index < text.length) {
            explanationText.innerHTML += text.charAt(index);
            index++;
            setTimeout(() => typeText(text, callback), 100);
        } else if (callback) {
            callback();
        }
    }

    // 첫 타이핑 애니메이션 시작
    typeText(textToType, () => {
        nextButton.style.display = 'block'; // 타이핑이 끝나면 "다음" 버튼 보이기
    });

    // "다음" 버튼 생성
    const nextButton = document.createElement('button');
    nextButton.id = 'nextButton';
    nextButton.innerHTML = '다음';
    nextButton.style.position = 'absolute';
    nextButton.style.top = genreSelect.getBoundingClientRect().bottom + window.scrollY + 30 + 'px';
    nextButton.style.left = genreSelect.getBoundingClientRect().left + (genreSelect.offsetWidth / 2) - 30 + 'px';
    nextButton.style.padding = '10px 20px';
    nextButton.style.fontSize = '1.2rem';
    nextButton.style.zIndex = '10001';
    nextButton.style.display = 'none'; // 처음에는 숨기기
    document.body.appendChild(nextButton);

    // 2. "다음" 버튼 클릭 시 사용자 입력 영역 강조
    nextButton.addEventListener('click', function() {
        // 장르 선택 강조 제거
        genreSelect.classList.remove('highlight');
        explanationText.innerHTML = '';
        nextButton.style.display = 'none';

        // 사용자 입력 영역 강조
        const inputArea = document.getElementById('input-area');
        inputArea.classList.add('highlight');
        inputArea.style.zIndex = '10001';
        inputArea.scrollIntoView({ behavior: 'smooth' });

        // 챗봇 설명 텍스트 타이핑 애니메이션
        const newText = '챗봇과의 대화를 통해 시놉시스를 작성해보세요!';
        index = 0;
        typeText(newText, () => {
            nextButton.style.display = 'block'; // 두 번째 타이핑 후 버튼 보이기
            nextButton.style.top = inputArea.getBoundingClientRect().bottom + window.scrollY + 30 + 'px';
            nextButton.style.left = inputArea.getBoundingClientRect().left + (inputArea.offsetWidth / 2) - 30 + 'px';
        });
    });

    // 3. "다음" 버튼 클릭 시 줄거리 입력 강조
    nextButton.addEventListener('click', function() {
        // 사용자 입력 영역 강조 제거
        const inputArea = document.getElementById('input-area');
        inputArea.classList.remove('highlight');

        // 설명 텍스트 초기화
        explanationText.innerHTML = '';
        nextButton.style.display = 'none';

        // 줄거리 입력 강조
        const introInput = document.getElementById('intro');
        introInput.classList.add('highlight');
        introInput.style.zIndex = '10001';
        introInput.scrollIntoView({ behavior: 'smooth' });

        // 새로운 설명 텍스트 타이핑 애니메이션
        const finalText = '이제 당신의 글을 소개할 시간이에요!';
        index = 0;
        typeText(finalText, () => {
            // 마지막 타이핑이 끝나면 오버레이와 설명 제거
            setTimeout(function() {
                explanationText.remove();
                overlay.remove();
            }, 3000); // 3초 후 제거
        });
    });
};
