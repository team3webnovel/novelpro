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
    // 클릭된 요소가 AI 창작 스튜디오 버튼인지 확인
    if (event.target && event.target.id === 'aiStudioButton') {
        event.preventDefault(); // 기본 동작 방지
        console.log('AI 창작 스튜디오 버튼이 클릭되었습니다.');

        const overlay = document.getElementById("overlay");
        const messageBox = document.getElementById("messageBox");
        const confirmButton = document.getElementById("confirmButton");

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
});