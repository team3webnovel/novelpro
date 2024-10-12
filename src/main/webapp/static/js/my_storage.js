function writeBoard(creationId, imageUrl) {
	document.getElementById("creationIdField").value = creationId;
	
    // 모달 창 열기
    $('#myModal').modal('show');

    // 이미지 URL을 모달에 있는 img 태그에 적용
    document.getElementById("modalImage").src = imageUrl;
}

function closeModal() {
    // 모달 창 닫기
    $('#myModal').modal('hide');
    
    // 모달이 닫힐 때 이미지를 비워줍니다 (선택 사항)
    document.getElementById("modalImage").src = "";
}

function submitData() {
    // 선택한 공개 여부와 코멘트 값 가져오기
    var publicOption = document.getElementById("publicOption").value;
    var comment = document.getElementById("comment").value;
    var creationId = document.getElementById("creationIdField").value;

    // 데이터 전송
    fetch('/team3webnovel/images/board/write', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            creationId: creationId, // hidden input 필드에서 가져온 값 사용
            publicOption: publicOption,
            comment: comment
        })
    }).then(response => {
        if (response.ok) {
            // 전송 성공 시 리다이렉트
            window.location.href = '/team3webnovel/images/board';
        } else {
            alert('데이터 전송에 실패했습니다.');
        }
    });
}
// 이벤트 위임을 통해 버튼 클릭 이벤트 처리
document.addEventListener('click', function(event) {
    const overlay = document.getElementById("overlay");
    const messageBox = document.getElementById("messageBox");
    const confirmButton = document.getElementById("confirmButton");

    // 클릭된 요소가 AI 창작 스튜디오 버튼인지 확인
    if (event.target && event.target.id === 'aiStudioButton') {
        event.preventDefault(); // 기본 동작 방지
        console.log('AI 창작 스튜디오 버튼이 클릭되었습니다.');

        if (overlay && messageBox && confirmButton) {
            // 오버레이 보이기
            overlay.style.display = "flex";

            // 메시지 박스 천천히 보이기
            setTimeout(() => {
                messageBox.style.opacity = 1; // 메시지 박스 나타나기
            }, 500); // 0.5초 후 메시지 나타남

            // 확인 버튼 표시
            setTimeout(() => {
                confirmButton.style.display = "block"; // 확인 버튼 보이기
                console.log("확인 버튼이 보입니다.");

                // 확인 버튼 클릭 시 리다이렉션 설정
                confirmButton.onclick = function() {
                    console.log("확인 버튼 클릭됨. /creation-studio로 이동");
                    window.location.href = contextPath + "/creation-studio"; // 이동할 페이지 경로 설정
                };

            }, 1000); // 메시지가 완전히 사라진 후 확인 버튼 나타남
        } else {
            console.error("오버레이 또는 메시지 박스 요소가 없습니다.");
        }
    }

    // 바깥을 클릭하면 오버레이 닫기
    if (overlay && overlay.style.display === "flex" && !messageBox.contains(event.target) && !event.target.closest('#aiStudioButton')) {
        overlay.style.display = "none"; // 오버레이 숨기기
        messageBox.style.opacity = 0; // 메시지 박스 투명도 0으로 설정
        confirmButton.style.display = "none"; // 확인 버튼 숨기기
        console.log("오버레이가 닫혔습니다.");
    }
});




// 이미지 모달을 열고 creationId 값을 설정
function openImageModal(creationId, imageUrl, createdAt, sampler, prompt, modelCheck, title) {
    document.getElementById('modalImage').src = imageUrl;
    document.getElementById('modalCreatedAt').innerText = '생성일: ' + createdAt;
    document.getElementById('modalSampler').innerText = '샘플러: ' + sampler;
    document.getElementById('modalPrompt').innerText = '프롬프트: ' + prompt;
    document.getElementById('modalModelCheck').innerText = '모델 체크: ' + modelCheck;

    // 이미지 제목 설정
    document.getElementById('modalTitle').innerText = '제목: ' + title;
    originalTitle = title;  // 원래 제목 저장

    // creationId 숨겨진 필드에 저장
    document.getElementById('creationIdField').value = creationId;

    // 모달 띄우기
    $('#myModal').modal('show');
}

// 제목 수정 가능하게 변경
function enableEditTitle() {
    var titleElement = document.getElementById('modalTitle');
    var currentTitle = titleElement.innerText.replace('제목: ', '');

    // 제목을 인풋 필드로 변경
    titleElement.innerHTML = '<input type="text" id="editableTitle" class="form-control" value="' + currentTitle + '" />';

    // 수정 버튼 숨기고 확인, 취소 버튼 표시
    document.getElementById('editButton').style.display = 'none';
    document.getElementById('editControls').style.display = 'inline-block';
}

function saveTitle() {
    var editableTitle = document.getElementById('editableTitle');
    var newTitle = editableTitle.value;
    var creationId = document.getElementById('creationIdField').value;

    // 서버로 수정된 제목을 전송 (AJAX)
    $.ajax({
        url: contextPath + '/update-image-title',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            creationId: parseInt(creationId),  // creationId는 숫자로 변환
            title: newTitle                    // title 필드로 전송
        }),
        success: function(response) {
            if (response.success) {
                // 성공 시 리다이렉트
                window.location.href = contextPath + '/storage';
            } else {
                alert('제목 수정에 실패했습니다. 다시 시도해주세요.');
            }
        },
        error: function() {
            alert('오류가 발생했습니다. 나중에 다시 시도해주세요.');
        }
    });
}



// 제목 수정 취소
function cancelEditTitle() {
    // 제목을 원래 상태로 복구
    document.getElementById('modalTitle').innerText = '제목: ' + originalTitle;

    // 확인, 취소 버튼 숨기고 수정 버튼 다시 표시
    document.getElementById('editButton').style.display = 'inline-block';
    document.getElementById('editControls').style.display = 'none';
}

// 게시판 올리기 버튼 눌렀을 때 공개 여부 및 코멘트 입력 필드 보이기
function enablePost() {
    document.getElementById('submitSection').style.display = 'block';

    // 게시판 올리기 버튼 숨기고 확인, 취소 버튼 표시
    document.getElementById('postButton').style.display = 'none';
    document.getElementById('postControls').style.display = 'inline-block';
}



// 게시판 올리기 취소
function cancelPost() {
    // 공개 여부와 코멘트 입력 필드를 숨기고 게시판 올리기 버튼을 다시 표시
    document.getElementById('submitSection').style.display = 'none';
    document.getElementById('postButton').style.display = 'inline-block';
    document.getElementById('postControls').style.display = 'none';
}

// 모달이 닫힐 때 초기화
$('#myModal').on('hide.bs.modal', function () {
    // 제목을 원래 상태로 초기화
    document.getElementById('modalTitle').innerText = '제목: ' + originalTitle;

    // 수정 버튼 표시, 확인과 취소 버튼 숨김
    document.getElementById('editButton').style.display = 'inline-block';
    document.getElementById('editControls').style.display = 'none';

    // 공개 여부 선택과 코멘트 입력 필드 숨기기
    document.getElementById('submitSection').style.display = 'none';
    document.getElementById('postButton').style.display = 'inline-block';
    document.getElementById('postControls').style.display = 'none';

    // 공개 여부 기본값 설정 및 코멘트 입력 초기화
    document.getElementById('publicOption').value = 'public';
    document.getElementById('comment').value = '';
});

function writeBoard(creationId, imageUrl) {
    console.log("게시판에 이미지 올리기: " + creationId + ", " + imageUrl);
}

function deleteImage() {
    var creationId = document.getElementById('creationIdField').value;

    if (confirm('정말 이 이미지를 삭제하시겠습니까?')) {
        // 서버로 삭제 요청을 전송 (AJAX)
        $.ajax({
            url: contextPath + '/delete-image',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                creationId: parseInt(creationId) // 삭제할 이미지의 creationId 전송
            }),
            success: function(response) {
                if (response.success) {
                    // 성공 시 리다이렉트
                    alert('이미지가 성공적으로 삭제되었습니다.');
                    window.location.href = contextPath + '/storage';
                } else {
                    alert('이미지 삭제에 실패했습니다. 다시 시도해주세요.');
                }
            },
            error: function() {
                alert('오류가 발생했습니다. 나중에 다시 시도해주세요.');
            }
        });
    }
}

$(document).ready(function() {
    var hash = window.location.hash;
    if (hash) {
        $('.nav-tabs a[href="' + hash + '"]').tab('show'); // 해당 탭을 활성화
    }

    // 탭 클릭 시 URL에 해시값 추가
    $('.nav-tabs a').on('shown.bs.tab', function(e) {
        window.location.hash = e.target.hash;
    });
});


// 이미지 클릭 시 음악 재생 함수
function playMusic(creationId, audioUrl) {
    const audioPlayer = document.getElementById('audioPlayer' + creationId);
    
    if (audioPlayer.paused) {
        // 모든 오디오 정지
        const allPlayers = document.querySelectorAll('audio');
        allPlayers.forEach(player => {
            player.pause();
            player.currentTime = 0;
        });
        
        // 클릭한 오디오 플레이어 재생
        audioPlayer.play();
    } else {
        // 재생 중이면 일시 정지
        audioPlayer.pause();
    }
}

// 음악 탭 초기화 함수 (페이지 로드 시 실행)
document.addEventListener('DOMContentLoaded', () => {
    const musicCards = document.querySelectorAll('.card');
    
    // 각 카드에 마우스 오버 시 커스텀 스타일 적용
    musicCards.forEach(card => {
        card.addEventListener('mouseover', () => {
            card.style.boxShadow = '0 8px 16px rgba(0, 0, 0, 0.2)';
        });
        
        card.addEventListener('mouseleave', () => {
            card.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
        });
    });
});
