// High resolution 옵션 변경 시 숨기기/보이기 처리
document.getElementById('enable_hr').addEventListener('change', function() {
    const hrOptions = document.getElementById('hr_options');
    hrOptions.style.display = this.checked ? 'block' : 'none';
});

// 모델 체크포인트 변경 시 서버에 요청 보내기
document.getElementById('model_checkpoint').addEventListener('change', async function() {
    const modelCheckpoint = this.value;
	console.log(modelCheckpoint)
    // 페이지 비활성화
    document.getElementById('overlay').style.display = 'block';

	try {
	    const response = await fetch('/team3webnovel/change/checkpoint', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify({
	            "sd_model_checkpoint": modelCheckpoint + ".safetensors"
	        }),
	    });

	    if (!response.ok) {
	        throw new Error('Network response was not ok');
	    }
	    console.log(response.status);
		// 응답 본문을 로그로 출력
	    const responseText = await response.text();
	    console.log('Response Text:', responseText); // 여기서 응답 본문을 출력
		
	} catch (error) {
	    console.error('Error updating model checkpoint:', error.message);
	} finally {
        // 페이지 활성화
        document.getElementById('overlay').style.display = 'none';
    }
});

const form = document.getElementById('imageForm');
const resultDiv = document.getElementById('result');
const loadingDiv = document.getElementById('loading');  // 로딩 표시 div
const submitButton = document.getElementById('submitButton');  // 제출 버튼
let isRequestInProgress = false;  // 요청 중인지 확인하는 플래그

// 이미지 생성 요청
form.addEventListener('submit', async function(event) {
    event.preventDefault();

    // 중복 요청 방지
    if (isRequestInProgress) {
        alert("Request is already in progress. Please wait.");
        return;
    }

    isRequestInProgress = true;
    loadingDiv.style.display = 'block';  // 로딩 표시 보이기
    submitButton.disabled = true;  // 버튼 비활성화
    resultDiv.innerHTML = '';  // 결과 영역 초기화

    // 입력 파라미터를 json 형태로 변환
    let enableHR = document.getElementById('enable_hr').checked;
    let data = {
        sampler_index: document.getElementById("sampler_index").value,
        prompt: document.getElementById('prompt').value,
        negative_prompt: document.getElementById('negative_prompt').value,
        steps: document.getElementById('steps').value,
        width: document.getElementById('width').value,
        height: document.getElementById('height').value,
        cfg_scale: document.getElementById('cfg_scale').value,
        seed: document.getElementById('seed').value,
        enable_hr: enableHR,
    };

    if (enableHR) {
        // High resolution 관련 파라미터 추가
        data.denoising_strength = document.getElementById('denoising_strength').value;
        data.hr_scale = document.getElementById('hr_scale').value;
        data.hr_upscaler = document.getElementById('hr_upscaler').value;
    }

    try {
        const response = await fetch('/team3webnovel/create/image', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to generate image');
        }

        const result = await response.json();

        // 서버 응답에서 이미지 데이터 추출
        const imageData = result.image;
        const imageElement = document.createElement('img');
        imageElement.src = `data:image/png;base64,${imageData}`;
        imageElement.alt = 'Generated Image';

        // 결과 div에 이미지 추가
        resultDiv.innerHTML = '';  // 기존 내용 삭제
        resultDiv.appendChild(imageElement);
    } catch (error) {
        console.error('Error:', error.message);
        resultDiv.innerHTML = `<p style="color: red;">Error generating image: ${error.message}</p>`;
    } finally {
        // 로딩 상태 해제 및 중복 요청 방지 플래그 초기화
        loadingDiv.style.display = 'none';  // 로딩 숨기기
        submitButton.disabled = false;  // 버튼 활성화
        isRequestInProgress = false;
    }
});
