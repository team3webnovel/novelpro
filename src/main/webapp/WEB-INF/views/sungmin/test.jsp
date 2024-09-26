<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ComfyUI 요청</title>
    <script>
        // WebSocket 연결 함수
        function connectWebSocket(clientId) {
            console.log(`WebSocket을 ${clientId}로 연결 시도 중...`);
            const ws = new WebSocket(`ws://127.0.0.1:8188/ws?clientId=${clientId}`);

            ws.onopen = function() {
                console.log("WebSocket 연결 성공");
            };

            ws.onmessage = function(event) {
                const message = JSON.parse(event.data);
                console.log("서버로부터 메시지 수신: ", message);

                if (message.type === 'executing') {
                    if (message.data.node === null) {
                        console.log("작업이 완료되었습니다!");
                        // 이미지 다운로드 및 표시
                        fetchImage();
                    } else {
                        console.log("현재 실행 중인 노드 ID: ", message.data.node);
                    }
                } else if (message.type === 'status') {
                    console.log("현재 작업 큐 상태: ", message.data.status.exec_info.queue_remaining);
                } else {
                    console.log("예상치 못한 메시지 수신: ", message);
                }
            };

            ws.onerror = function(error) {
                console.error("WebSocket 에러 발생:", error);
            };

            ws.onclose = function() {
                console.log("WebSocket 연결 종료");
            };
        }

        // HTTP 요청으로 prompt 전송
        async function sendPrompt() {
            const clientId = generateUUID(); // WebSocket 연결에 사용될 clientId
            console.log(`프롬프트 전송을 시작합니다. clientId: ${clientId}`);

            const promptText = {
                "3": {
                    "class_type": "KSampler",
                    "inputs": {
                        "cfg": 8,
                        "denoise": 1,
                        "latent_image": ["5", 0],
                        "model": ["4", 0],
                        "negative": ["7", 0],
                        "positive": ["6", 0],
                        "sampler_name": "euler",
                        "scheduler": "normal",
                        "seed": 8566257,
                        "steps": 20
                    }
                },
                "4": {
                    "class_type": "CheckpointLoaderSimple",
                    "inputs": {
                        "ckpt_name": "aamXLAnimeMix_v10HalfturboEulera.safetensors"
                    }
                },
                "5": {
                    "class_type": "EmptyLatentImage",
                    "inputs": {
                        "batch_size": 1,
                        "height": 512,
                        "width": 512
                    }
                },
                "6": {
                    "class_type": "CLIPTextEncode",
                    "inputs": {
                        "clip": ["4", 1],
                        "text": "1girl, 1man"
                    }
                },
                "7": {
                    "class_type": "CLIPTextEncode",
                    "inputs": {
                        "clip": ["4", 1],
                        "text": "bad hands"
                    }
                },
                "8": {
                    "class_type": "VAEDecode",
                    "inputs": {
                        "samples": ["3", 0],
                        "vae": ["4", 2]
                    }
                },
                "9": {
                    "class_type": "SaveImage",
                    "inputs": {
                        "filename_prefix": "ComfyUI",
                        "images": ["8", 0]
                    }
                }
            };

            try {
                console.log("프롬프트 전송 중...");
                const response = await fetch('http://127.0.0.1:8188/prompt', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        prompt: promptText,
                        client_id: clientId
                    })
                });

                if (!response.ok) {
                    throw new Error(`HTTP 요청 실패: ${response.statusText}`);
                }

                const data = await response.json();
                console.log("프롬프트 전송 응답:", data);

                // WebSocket 연결
                console.log("WebSocket 연결을 시작합니다...");
                connectWebSocket(clientId);

            } catch (error) {
                console.error("프롬프트 전송 중 에러 발생:", error);
            }
        }

        // UUID 생성 함수
        function generateUUID() {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                const r = Math.random() * 16 | 0,
                      v = c === 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        }

        // 이미지 다운로드 및 화면에 표시
        async function fetchImage() {
            try {
                console.log("이미지 다운로드 시작...");
                const response = await fetch('http://127.0.0.1:8188/view?filename=ComfyUI.png');
                if (!response.ok) {
                    throw new Error(`이미지 다운로드 실패: ${response.statusText}`);
                }
                const blob = await response.blob();
                const imageUrl = URL.createObjectURL(blob);

                // 이미지 태그에 표시
                const imgElement = document.getElementById('resultImage');
                imgElement.src = imageUrl;
                imgElement.style.display = 'block'; // 이미지 표시
                console.log("이미지 다운로드 및 표시 완료");
            } catch (error) {
                console.error("이미지 가져오기 중 에러 발생:", error);
            }
        }
    </script>
</head>
<body>
    <h1>ComfyUI 요청</h1>
    <button onclick="sendPrompt()">프롬프트 전송</button>
    
    <!-- 결과 이미지를 표시할 태그 -->
    <img id="resultImage" style="display:none;" alt="결과 이미지" />
</body>
</html>
