function openModal() {
    document.getElementById("myModal").style.display = "block";
}

function closeModal() {
    document.getElementById("myModal").style.display = "none";
}

function changeModel(modelName) {
    fetch('/team3webnovel/change/checkpoint', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
		body: JSON.stringify({
			            "sd_model_checkpoint": modelName + ".safetensors"
		}) // 모델 이름을 JSON 형태로 전송
    })
    .then(response => {
        if (response.ok) {
            alert('모델이 성공적으로 변경되었습니다.');
			openModal();
        } else {
            alert('모델 변경에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('서버와의 연결에 문제가 발생했습니다.');
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const modelForm = document.getElementById("create");
    if (modelForm) {
        modelForm.onsubmit = async function(event) {
            event.preventDefault(); // 기본 폼 제출 방지
            
			const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
			const selectedParams = Array.from(checkboxes).map(checkbox => checkbox.value).join(', ');
            const prompt = "score_9, score_8_up, best quality. 4K, White lighting:1.2. masterpiece, high quality. " + selectedParams ;
			
            // 스피너 표시
            document.getElementById("spinner").style.display = "block";
            
            try {
                const response = await fetch('/team3webnovel/create/image', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ 
						prompt: prompt,
						sampler_index: "DFM++ 2M",
						steps: 20,
						width: 896,
						height: 1152,
						cfg_scale: 5,
						negative_prompt:"realistic, monochrome, greyscale, artist name, signature, watermark, ugly hands"
					 })
                });

				// 서버에 이미지를 POST 방식으로 전송
				if (response.ok) {
				    const result = await response.json();
				    const imageData = result.image;

				    // POST 요청으로 이미지 데이터 전송
				    await fetch('/team3webnovel/image/result', {
				        method: 'POST',
				        headers: {
				            'Content-Type': 'application/json'
				        },
				        body: JSON.stringify({ image: imageData, prompt: prompt }) // 이미지 데이터를 JSON 형태로 전송
				    }).then(() => {
				        // 요청이 성공하면 리다이렉트
				        window.location.href = '/team3webnovel/image/result';
				    }).catch(error => {
				        console.error('Error:', error);
				        alert('이미지 데이터 전송에 실패했습니다.');
				    });
				}
            } catch (error) {
                console.error('Error:', error);
                alert('서버와의 연결에 문제가 발생했습니다.');
            } finally {
                // 스피너 숨김
                document.getElementById("spinner").style.display = "none";
            }
        };
    } else {
        console.error("Form with id 'modelForm' not found.");
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const nextDiv = this.closest('div').nextElementSibling;
            
            if (nextDiv) {
                if (this.checked) {
                    nextDiv.classList.remove('hidden');
                    nextDiv.classList.add('visible');
//                } else {
//                    nextDiv.classList.remove('visible');
//                    nextDiv.classList.add('hidden');
                }
            }
        });
    });
});