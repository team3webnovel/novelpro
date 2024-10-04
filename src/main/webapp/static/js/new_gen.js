function openChoiceModal() {
    $('#choiceModal').modal('show');
	console.log(checkpoint);
	console.log(seed);
}
function openAIModal(){
	$('#AIModal').modal('show');
}

var checkpoint = '';
var seed = 0;

function changeModelChoiceModal(model){
	checkpoint = model;
	openChoiceModal();
}

function changeModelAIModal(model){
	checkpoint = model;
	openAIModal();
}

function showImageInModal(imageElement) {
    var imageSrc = imageElement.src;
    document.getElementById("modalImage").src = imageSrc;
    $('#imageModal').modal('show');
}

function setRandomSeed() {
    // 랜덤 정수 생성 (예: 1부터 9999999까지)
    seed = Math.floor(Math.random() * 9999999) + 1;
}

//	안 보이는 checkbox 요소 보이게 하는 함수
document.addEventListener('DOMContentLoaded', function() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const nextDiv = this.closest('div').nextElementSibling;
            
            if (nextDiv) {
                if (this.checked) {
                    nextDiv.classList.remove('hidden');
                    nextDiv.classList.add('visible');
                }
            }
        });
    });
});

document.addEventListener("DOMContentLoaded", function() {
	const modelForm = document.getElementById("create");
	if (modelForm){
		modelForm.onsubmit = async function(event){
			event.preventDefault();
			
			const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
			const selectedParams = Array.from(checkboxes).map(checkbox => checkbox.value).join(', ');
			const prompt = "score_9, score_8_up, best quality. 4K, White lighting:1.2. masterpiece, high quality. " + selectedParams ;
			
			setRandomSeed();
			
			document.getElementById("spinner1").style.display = "block";
			
			const imageData = {
				prompt: prompt,
				negative_prompt: "realistic, monochrome, greyscale, artist name, signature, watermark, ugly hands",
				sampler_index: "dpmpp_2m",
				steps: 25,
				width: 896,
				height: 1152,
				cfg_scale: 5,
				checkpoint: checkpoint,
				seed: seed
			};
			
			fetch('/team3webnovel/gije/test', {
				method: 'POST',
				headers: {
		            'Content-Type': 'application/json', // JSON 형식으로 전송
		        },
		        body: JSON.stringify(imageData) // 데이터를 JSON으로 변환
			})
			.then(response => {
			    if (response.ok) {
			        return response.json(); // 응답을 JSON 형식으로 파싱
			    } else {
			        throw new Error('Network response was not ok');
			    }
			})
			.then(result => {
			    console.log(result);  // 서버에서 받은 JSON 응답 출력
			    document.getElementById("spinner1").style.display = "none";
			    window.location.href = "/team3webnovel/my_storage"; // 페이지 이동
			})
			.catch(error => {
			    console.error('Error:', error);
			    alert('이미지 생성에 실패했습니다.');
			});
		}
	}
});

document.addEventListener("DOMContentLoaded", function() {
	const modelForm = document.getElementById("create-with-ai");
	
	if (modelForm){
			modelForm.onsubmit = async function(event){
				event.preventDefault();
				document.getElementById("spinner2").style.display = "block";
				const inputPrompt = document.getElementById("comment").value;
				
				fetch('/team3webnovel/gije/ai', {
					method: 'POST',
			        headers: {
			            'Content-Type': 'application/json'
			        },
					body: JSON.stringify({
			            input: inputPrompt
			        })
				})
				.then(response => {
					if (response.ok){
						const contentLength = response.headers.get('Content-Length');
						if (contentLength && contentLength === '0') {
				            alert('입력 변환에 실패했습니다.')
				        }
						return response.json();
					} else {
						alert('입력 변환에 실패했습니다.')
					}
				})
				.then(data => {
					
					setRandomSeed();
				    
				    // 서버에서 받은 데이터를 기반으로 imageData 구성
				    const imageData = {
				        prompt: data.prompt,  // 서버에서 받은 prompt 값
				        negative_prompt: data.negative_prompt || "realistic, monochrome, greyscale, artist name, signature, watermark, ugly hands",  // 서버에서 받은 negative_prompt 값 또는 기본값
				        sampler_index: "dpmpp_2m",
				        steps: 25,
				        width: 896,
				        height: 1152,
				        cfg_scale: 6,
				        checkpoint: checkpoint,
				        seed: seed
				    };
				    
					fetch('/team3webnovel/gije/test', {
						method: 'POST',
						headers: {
				            'Content-Type': 'application/json', // JSON 형식으로 전송
				        },
				        body: JSON.stringify(imageData) // 데이터를 JSON으로 변환
					})
					.then(response => {
					    if (response.ok) {
					        return response.json(); // 응답을 JSON 형식으로 파싱
					    } else {
					        throw new Error('Network response was not ok');
					    }
					})
					.then(result => {
					    console.log(result);  // 서버에서 받은 JSON 응답 출력
					    document.getElementById("spinner2").style.display = "none";
					    window.location.href = "/team3webnovel/my_storage"; // 페이지 이동
					})
					.catch(error => {
					    console.error('Error:', error);
					    alert('이미지 생성에 실패했습니다.');
					});
				    
				})
				.catch(error => {
				    console.error('Error:', error);
				});
				
			}
		}
});