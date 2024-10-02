// 페이지가 로드된 후 이벤트 리스너를 설정
document.addEventListener("DOMContentLoaded", function () {
    // 모달을 제어하는 코드
    var modal = document.getElementById("imageModal");
    var btn = document.getElementById("openModalBtn");
    var span = document.getElementsByClassName("close")[0];

    // 모달 열기
    btn.onclick = function () {
        modal.style.display = "block";
    };

    // 모달 닫기
    span.onclick = function () {
        modal.style.display = "none";
    };

    // 모달 밖을 클릭하면 닫기
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };

	// 모달에서 이미지를 선택할 때 플래그를 true로 설정
	function selectImageAndDraw(imageUrl) {
	    img = new Image();  // img를 새 이미지로 초기화
		img.crossOrigin = "anonymous";  // CORS 설정 추가
	    img.onload = function () {
	        canvas.width = img.width;
	        canvas.height = img.height;
	        drawCanvas();  // 이미지를 캔버스에 그린 후 텍스트 박스 등을 다시 그립니다.
	    };
	    img.src = imageUrl;  // 선택한 이미지 URL 설정
	    modalImageSelected = true;  // 모달 이미지를 선택했음을 표시
		modal.style.display = "none";  // 선택한 후 모달 닫기
	}

    // 모든 모달 이미지에 클릭 이벤트 리스너 추가
    document.querySelectorAll('.modal-image-item').forEach(function (imgElement) {
        imgElement.addEventListener('click', function () {
            var imageUrl = imgElement.getAttribute('src');
            selectImageAndDraw(imageUrl);  // 이미지 클릭 시, 선택한 이미지를 캔버스에 그리기
        });
    });
});
