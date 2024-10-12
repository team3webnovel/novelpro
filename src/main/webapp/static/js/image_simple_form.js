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
	if (document.getElementById('seed')){
		document.getElementById('seed').value = seed;	
	}
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
				width: 768,
				height: 1152,
				cfg_scale: 5,
				checkpoint: checkpoint,
				seed: seed
			};
			
			fetch('/team3webnovel/images', {
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
			    window.location.href = "/team3webnovel/storage"; // 페이지 이동
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
				
				fetch('/team3webnovel/translate', {
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
				        width: 768,
				        height: 1152,
				        cfg_scale: 6,
				        checkpoint: checkpoint,
				        seed: seed
				    };
				    
					fetch('/team3webnovel/images', {
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
					    window.location.href = "/team3webnovel/storage"; // 페이지 이동
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
document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("detailSubmitButton").addEventListener("click", function() {
		
		document.getElementById("spinner3").style.display = "block";
		
		const imageData = {
	      prompt: document.getElementById('prompt').value,
	      negative_prompt: document.getElementById('negative_prompt').value || "realistic, monochrome, greyscale, artist name, signature, watermark, ugly hands",
	      sampler_index: document.getElementById('sampler_index').value,
	      steps: parseInt(document.getElementById('steps').value, 10),  // 숫자로 변환
	      width: parseInt(document.getElementById('width').value, 10),  // 숫자로 변환
	      height: parseInt(document.getElementById('height').value, 10),  // 숫자로 변환
	      cfg_scale: parseInt(document.getElementById('cfg_scale').value, 10),  // 숫자로 변환
	      checkpoint: document.getElementById('checkpoint').value,
	      seed: parseInt(document.getElementById('seed').value, 10)  // 숫자로 변환
	    };
	
	  	// Fetch API로 form 데이터를 JSON 형식으로 전송
	  	fetch('/team3webnovel/images', {
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
	  	.then(data => {
			console.log(data);  // 서버에서 받은 JSON 응답 출력
			document.getElementById("spinner3").style.display = "none";
		    alert("생성이 완료되었습니다!");
		    window.location.href = "/team3webnovel/storage"; // 페이지 이동
	  	})
	  	.catch(error => {
		    console.error("에러 발생:", error);
		    alert("생성 중 오류가 발생했습니다.");
	  	});
	});
});