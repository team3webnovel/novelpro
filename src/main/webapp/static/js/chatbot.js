// 채팅 기록을 저장할 배열
let chatHistory = [];

// 이미지 미리보기 기능
function displaySelectedImage() {
    var select = document.getElementById('imageSelect');
    var fileName = select.options[select.selectedIndex].text;
    var imageUrl = select.options[select.selectedIndex].getAttribute('data-image-url');

    // 이미지 미리보기 설정
    var imgPreview = document.getElementById('selectedImagePreview');
    if (imageUrl) {
        imgPreview.src = imageUrl;
        imgPreview.style.display = 'block';  // 이미지 표시
        console.log("Selected image URL: " + imageUrl);  // 선택된 이미지 로그 출력
    } else {
        imgPreview.style.display = 'none';  // 이미지 숨김
    }

    document.getElementById('selectedImageFileName').innerText = fileName;
    console.log("Selected file name: " + fileName);
}

// 선택된 장르 값을 저장할 변수
let selectedGenre = "";

// 장르 선택 시 선택된 값을 바로 인식하도록 설정
document.getElementById('genre').addEventListener('change', function() {
    selectedGenre = this.value; // let 제거
    
    // 장르가 선택되지 않았을 경우 기본 값 설정
    if (!selectedGenre) {
        selectedGenre = "default";
        console.log("No genre selected. Using default genre.");
    } else {
        console.log("Selected genre: " + selectedGenre);
    }

    applyGenreInstruction(selectedGenre);
});

function applyGenreInstruction(genre) {
    // 기본 로직 혹은 장르별 로직을 여기에서 실행
    if (genre === "default") {
        console.log("Applying default instruction...");
        // 기본 지시문을 적용하는 로직
    } else {
        console.log("Applying instruction for genre: " + genre);
        // 해당 장르에 맞는 지시문을 적용하는 로직
    }
}

// 채팅 메시지를 추가하고 대화 기록을 유지하는 함수
function appendMessage(sender, message, isTemporary = false) {
    const messageData = { sender: sender, message: message, isTemporary: isTemporary };
    chatHistory.push(messageData);

    var chatbox = document.getElementById('chat-log');
    if (!chatbox) {
        console.error("chat-log 요소를 찾을 수 없습니다.");
        return;
    }

    chatbox.innerHTML = "";  // 기존 채팅 로그 초기화

    let messageWrapper = null;  // 반복문 밖에서 선언

    chatHistory.forEach((chat) => {
        messageWrapper = document.createElement('div');  // 반복문 안에서 값을 할당
        messageWrapper.classList.add('message-wrapper');

        var messageElement = document.createElement('div');
        messageElement.classList.add('message');

        if (chat.sender === 'user') {
            messageElement.classList.add('user-message');
            messageWrapper.classList.add('user-wrapper');
        } else {
            messageElement.classList.add('bot-message');
            messageWrapper.classList.add('bot-wrapper');
        }

        messageElement.innerHTML = chat.message;
        messageWrapper.appendChild(messageElement);
        chatbox.appendChild(messageWrapper);
    });

    chatbox.scrollTop = chatbox.scrollHeight;
    console.log(sender + " message appended: " + message);

    // 마지막으로 생성된 messageWrapper 반환
    return messageWrapper;
}



// 사용자 입력 처리 및 서버 요청
document.getElementById('submit-btn').addEventListener('click', function() {
    var userInput = document.getElementById('user-input').value;
    var genre = document.getElementById('genre').value;

    // contextPath 정의 (JSP에서 정의된 경로 사용)
    var contextPath = "${pageContext.request.contextPath}";
    var apiUrl = contextPath + "/novel/new_novel/api";

    if (userInput.trim() !== "" && genre.trim() !== "") {
        console.log("User input: " + userInput);
        console.log("Selected genre: " + genre);

        appendMessage('user', userInput);

        var sanitizedUserInput = userInput.replace(/[\n\r]/g, ' ');
        var sanitizedGenre = genre.replace(/[\n\r]/g, ' ');

        console.log("Sending JSON: ", {
            userMessage: sanitizedUserInput,
            genre: sanitizedGenre
        });

        // 로딩 중 메시지 추가
        const loadingMessageIndex = chatHistory.length;
        const loadingElement = appendMessage('bot', '생성 중...', true);

        if (loadingElement) {
            loadingElement.classList.add('loading');
        }

        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({ 
                userMessage: sanitizedUserInput,
                genre: sanitizedGenre
            })
        })
        .then(response => {
            console.log("Response Status: " + response.status);

            if (!response.ok) {
                throw new Error("Network response was not ok. Status: " + response.status);
            }

            return response.json();
        })
        .then(data => {
            console.log("Parsed JSON: ", data);  // 응답 데이터를 콘솔에 출력

            if (data.intro) {
                // "생성 중..." 메시지를 실제 응답으로 대체
                chatHistory[loadingMessageIndex].message = data.intro;
                chatHistory[loadingMessageIndex].isTemporary = false;

                // 기존 채팅 기록을 다시 렌더링하여 "생성 중..." 메시지를 대체
                var chatbox = document.getElementById('chat-log');
                chatbox.innerHTML = "";  // 기존 채팅 로그 초기화

                chatHistory.forEach((chat) => {
                    var messageWrapper = document.createElement('div');
                    messageWrapper.classList.add('message-wrapper');

                    var messageElement = document.createElement('div');
                    messageElement.classList.add('message');

                    if (chat.sender === 'user') {
                        messageElement.classList.add('user-message');
                        messageWrapper.classList.add('user-wrapper');
                    } else {
                        messageElement.classList.add('bot-message');
                        messageWrapper.classList.add('bot-wrapper');
                    }

                    messageElement.innerHTML = chat.message;
                    messageWrapper.appendChild(messageElement);
                    chatbox.appendChild(messageWrapper);
                });

                chatbox.scrollTop = chatbox.scrollHeight;
            } else {
                console.error("intro 필드를 찾을 수 없습니다.");  // intro 필드가 없을 때 오류 출력
            }
        })
        .catch(error => {
            console.error('Error occurred: ', error);
            // 실패 시 "생성 중..." 메시지를 오류 메시지로 대체
            chatHistory[loadingMessageIndex].message = "GPT 응답을 받을 수 없습니다.";

            var chatbox = document.getElementById('chat-log');
            chatbox.innerHTML = "";  // 기존 채팅 로그 초기화

            chatHistory.forEach((chat) => {
                var messageWrapper = document.createElement('div');
                messageWrapper.classList.add('message-wrapper');

                var messageElement = document.createElement('div');
                messageElement.classList.add('message');

                if (chat.sender === 'user') {
                    messageElement.classList.add('user-message');
                    messageWrapper.classList.add('user-wrapper');
                } else {
                    messageElement.classList.add('bot-message');
                    messageWrapper.classList.add('bot-wrapper');
                }

                messageElement.innerHTML = chat.message;
                messageWrapper.appendChild(messageElement);
                chatbox.appendChild(messageWrapper);
            });

            chatbox.scrollTop = chatbox.scrollHeight;
        });

        document.getElementById('user-input').value = "";
    } else {
        console.log("User input is empty.");
        alert("메시지와 장르를 입력해주세요.");
    }
});
