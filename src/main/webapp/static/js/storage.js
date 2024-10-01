function writeBoard(creationId, imageUrl) {
	document.getElementById("creationIdField").value = creationId;
	
    // 모달 창 열기
    document.getElementById("myModal").style.display = "block";

    // 이미지 URL을 모달에 있는 img 태그에 적용
    document.getElementById("modalImage").src = imageUrl;
}

function closeModal() {
    // 모달 창 닫기
    document.getElementById("myModal").style.display = "none";
    
    // 모달이 닫힐 때 이미지를 비워줍니다 (선택 사항)
    document.getElementById("modalImage").src = "";
}

function submitData() {
    // 선택한 공개 여부와 코멘트 값 가져오기
    var publicOption = document.getElementById("publicOption").value;
    var comment = document.getElementById("comment").value;
    var creationId = document.getElementById("creationIdField").value;

    // 데이터 전송
    fetch('/team3webnovel/gije/image/board/write', {
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
            window.location.href = '/team3webnovel/gije/image/board';
        } else {
            alert('데이터 전송에 실패했습니다.');
        }
    });
}