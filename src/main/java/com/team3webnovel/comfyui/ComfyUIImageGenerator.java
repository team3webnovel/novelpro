package com.team3webnovel.comfyui;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.team3webnovel.vo.resultVo;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import oracle.jdbc.proxy.annotation.OnError;

@ClientEndpoint
public class ComfyUIImageGenerator {

    private static final String SERVER_ADDRESS = "192.168.0.237:8188"; // ComfyUI 서버 주소
    private static final String WEBSOCKET_URL = "ws://" + SERVER_ADDRESS + "/ws?clientId=";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
//    private static String clientId = UUID.randomUUID().toString();
    private static Session userSession;

    // 비동기로 처리할 CompletableFuture
    private CompletableFuture<resultVo> resultVoFuture;

//    public ComfyUIImageGenerator() {
//        connectWebSocket(); // WebSocket 연결
//    }
    
    // clientId 반환 메서드
//    public String getClientId() {
//        return clientId;
//    }

    // WebSocket 연결
    public void connectWebSocket(int clientId) {
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

    // 비동기적으로 이미지 URL과 파일명을 반환
    public CompletableFuture<resultVo> getGeneratedImageResult() {
        return resultVoFuture;
    }

    // 프롬프트 전송 메서드
    public CompletableFuture<resultVo> queuePrompt(
            String promptText,
            String negativePrompt,
            String samplerIndex,
            int steps,
            int width,
            int height,
            int cfgScale,  // CFG Scale만 double 타입으로 유지
            int seed,
            String checkpoint,
            int clientId) throws Exception {
        resultVoFuture = new CompletableFuture<>();  // 새 CompletableFuture 생성

        String url = "http://" + SERVER_ADDRESS + "/prompt";

        String jsonInputString = """
        {
            "prompt": {
                "3": {
                    "class_type": "KSampler",
                    "inputs": {
                        "cfg": %d,
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
                        "sampler_name": "%s",
                        "scheduler": "karras",
                        "seed": %d,
                        "steps": %d
                    }
                },
                "4": {
                    "class_type": "CheckpointLoaderSimple",
                    "inputs": {
                        "ckpt_name": "%s"
                    }
                },
                "5": {
                    "class_type": "EmptyLatentImage",
                    "inputs": {
                        "batch_size": 1,
                        "height": %d,
                        "width": %d
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
                        "text": "%s"
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
        """.formatted(cfgScale, samplerIndex, seed, steps, checkpoint, height, width, promptText, negativePrompt, clientId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                .build();

        // 프롬프트 전송
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Prompt response: " + response.body());

        // WebSocket으로부터 이미지 URL을 받을 때까지 무제한 대기
        return resultVoFuture;  // 타임아웃 없이 무제한 대기
    }

    // WebSocket 메시지 처리 (이미지 생성 완료 시)
    @OnMessage
    public void onMessage(String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String messageType = jsonMessage.getString("type");

            
                System.out.println("Received message: " + message);

                if ("executed".equals(messageType)) {
                    JSONObject data = jsonMessage.getJSONObject("data");
                    JSONObject output = data.getJSONObject("output");

                    // 이미지 정보 추출
                    JSONArray images = output.getJSONArray("images");
                    for (int i = 0; i < images.length(); i++) {
                        JSONObject image = images.getJSONObject(i);
                        String filename = image.getString("filename");
                        String subfolder = image.getString("subfolder");

                        // 이미지 URL 생성
                        String generatedImageUrl = "http://" + SERVER_ADDRESS + "/view?filename=" + filename + "&subfolder=" + subfolder + "&type=output&nocache=" + System.currentTimeMillis();

                        // resultVo 객체 생성하여 URL과 파일명 저장 후 CompletableFuture에 완료 신호 전달
                        resultVo result = new resultVo(generatedImageUrl, filename);
                        resultVoFuture.complete(result);

                        System.out.println("Image URL: " + generatedImageUrl);
                        System.out.println("Filename: " + filename);
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
        resultVoFuture.completeExceptionally(throwable);  // 오류 발생 시 CompletableFuture에 예외 전달
    }
}