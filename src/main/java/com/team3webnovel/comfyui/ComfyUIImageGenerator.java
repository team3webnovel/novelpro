package com.team3webnovel.comfyui;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

@ClientEndpoint
public class ComfyUIImageGenerator {

    private static final String SERVER_ADDRESS = "127.0.0.1:8188"; // ComfyUI 서버 주소
    private static final String WEBSOCKET_URL = "ws://" + SERVER_ADDRESS + "/ws?clientId=";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static String clientId = UUID.randomUUID().toString();
    private static Session userSession;

    public ComfyUIImageGenerator() {
        connectWebSocket(); // WebSocket 연결
    }

    // WebSocket 연결
    private void connectWebSocket() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(WEBSOCKET_URL + clientId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // WebSocket 연결 상태 확인
    public boolean isConnected() {
        return userSession != null && userSession.isOpen();
    }
    
    private void downloadAndSaveImage(String filename, String subfolder) {
        try {
            String imageUrl = "http://" + SERVER_ADDRESS + "/view?filename=" + filename + "&subfolder=" + subfolder + "&type=output";
            System.out.println(imageUrl);
            HttpRequest imageRequest = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .GET()
                    .build();

            HttpResponse<byte[]> imageResponse = httpClient.send(imageRequest, HttpResponse.BodyHandlers.ofByteArray());

            // 이미지 파일 저장
            Files.write(Paths.get("downloaded_" + filename), imageResponse.body());
            System.out.println("Image saved as: downloaded_" + filename);

        } catch (Exception e) {
            System.err.println("Failed to save image: " + e.getMessage());
        }
    }


    // 프롬프트 전송 메서드
 // 프롬프트 전송 메서드
    public String queuePrompt(String promptText) throws Exception {
        String url = "http://" + SERVER_ADDRESS + "/prompt";

        // 프롬프트 데이터를 "prompt"라는 필드에 포함시킴
        String jsonInputString = """
        {
            "prompt": {
                "3": {
                    "class_type": "KSampler",
                    "inputs": {
                        "cfg": 8,
                        "denoise": 1,
                        "latent_image": [
                            "5",
                            0
                        ],
                        "model": [
                            "4",
                            0
                        ],
                        "negative": [
                            "7",
                            0
                        ],
                        "positive": [
                            "6",
                            0
                        ],
                        "sampler_name": "euler",
                        "scheduler": "normal",
                        "seed": 8566257,
                        "steps": 30
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
                        "height": 832,
                        "width": 1216
                    }
                },
                "6": {
                    "class_type": "CLIPTextEncode",
                    "inputs": {
                        "clip": [
                            "4",
                            1
                        ],
                        "text": "%s"
                    }
                },
                "7": {
                    "class_type": "CLIPTextEncode",
                    "inputs": {
                        "clip": [
                            "4",
                            1
                        ],
                        "text": "negativeXL_D"
                    }
                },
                "8": {
                    "class_type": "VAEDecode",
                    "inputs": {
                        "samples": [
                            "3",
                            0
                        ],
                        "vae": [
                            "4",
                            2
                        ]
                    }
                },
                "9": {
                    "class_type": "SaveImage",
                    "inputs": {
                        "filename_prefix": "ComfyUI",
                        "images": [
                            "8",
                            0
                        ]
                    }
                }
            },
            "client_id": "%s"
        }
        """.formatted(promptText, clientId);  // promptText와 clientId도 포함시킴

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Prompt response: " + response.body());
        return response.body();
    }







    // WebSocket 메시지 처리 (이미지 생성 완료 시)
    @OnMessage
    public void onMessage(String message) {
        // 메시지 파싱 시도
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String messageType = jsonMessage.getString("type");

            // 메시지 타입이 'crystools.monitor'일 경우 로그 출력하지 않음
            if (!"crystools.monitor".equals(messageType)) {
                System.out.println("Received message: " + message);

                // "executed" 메시지 타입 처리 (이미지 생성 완료)
                if ("executed".equals(messageType)) {
                    JSONObject data = jsonMessage.getJSONObject("data");
                    String promptId = data.getString("prompt_id");
                    JSONObject output = data.getJSONObject("output");

                    // 이미지 정보 추출
                    JSONArray images = output.getJSONArray("images");
                    for (int i = 0; i < images.length(); i++) {
                        JSONObject image = images.getJSONObject(i);
                        String filename = image.getString("filename");
                        String subfolder = image.getString("subfolder");

                        System.out.println("Image generated: " + filename);

                        // 이미지 다운로드 메서드 호출
                        downloadAndSaveImage(filename, subfolder);
                    }
                }

                // "execution_success" 메시지 타입 처리 (전체 프로세스 완료)
                if ("execution_success".equals(messageType)) {
                    JSONObject data = jsonMessage.getJSONObject("data");
                    String promptId = data.getString("prompt_id");
                    System.out.println("Image generation process completed successfully for prompt_id: " + promptId);
                }
            }
        } catch (JSONException e) {
            System.err.println("Failed to parse WebSocket message: " + e.getMessage());
        }
    }



    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server");
        userSession = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Connection closed: " + reason);
        userSession = null;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error occurred: " + throwable.getMessage());
    }
}
