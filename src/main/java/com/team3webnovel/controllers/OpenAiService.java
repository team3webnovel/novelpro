package com.team3webnovel.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3webnovel.dao.PwDao;
import com.team3webnovel.vo.PwVo;

@Service
public class OpenAiService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static String API_KEY;
    private static final String SECRET_KEY = "1234567812345678"; // 16-byte key
    private static final String INIT_VECTOR = "RandomInitVector"; // 16-byte IV
    
    @Autowired
    private PwDao pwDao;

    // AES 복호화 메서드
    private String decrypt(String encryptedText, String key, String initVector) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        return new String(original);
    }

    // DB에서 암호화된 API 키를 불러와 복호화하는 메서드
    private String getDecryptedApiKey() {
        try {
            PwVo pwVo = pwDao.getPasswordByName("openAiAPI");
            System.out.println(pwVo);
            if (pwVo != null) {
               System.out.println(decrypt(pwVo.getPwPw(), SECRET_KEY, INIT_VECTOR));
                return decrypt(pwVo.getPwPw(), SECRET_KEY, INIT_VECTOR);
            } else {
                throw new Exception("API 키를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "API 키 복호화 실패";
        }
    }

    // 대화 기록 저장을 위한 리스트 (연속성을 유지하기 위해 추가된 부분)
    private List<String> chatHistory = new ArrayList<>();

    // 대화 기록을 추가하는 메서드
    public void addChatHistory(String role, String message) {
        chatHistory.add("{ \"role\": \"" + role + "\", \"content\": \"" + message + "\" }");
    }

    // 장르별 system instruction
    public String generateIntroFromApi(String userMessage, String genre) throws JsonProcessingException {
        String response = "";
        String genreInstruction = "";  // 장르별 지시문을 담는 변수

        // 기본 지시문
        String generalInstruction = "Based on the provided input, make sure to write the title, characters, and novel synopsis in Korean. Do not use unnecessary labeling. It must include the title, characters, and plot when outputting in HTML format. Please create a title that does not directly include the provided keywords. Instead, expand on the meaning or interpret the essence of the keywords to form a creative and fitting title. Avoid using the exact words from the input, but ensure the title reflects the overall theme or message. Only one story should be created. Based on the input, the system will reference the genre and background of the example to set character names and settings. You also need to set up the characters. You do not need to directly use the provided keywords in the title or synopsis, but rather expand and interpret their meaning. The story must include 3 characters and the synopsis should be written within 1,000 characters, including twists, entertainment elements, and a coherent storyline. Please format the response with line breaks for easy reading. use concise and intuitive expressions. There should be dramatic situation portrayals. While themes such as regression, reincarnation, possession, survival, growth, sports, entertainment industry, war, and dimensional travel are available, it is not necessary to use all of them. You need to assess whether the user-provided keywords and genres are suitable, then expand and interpret them accordingly for appropriate use.";
        
        // 장르별 지시문을 설정하는 switch 문
        switch (genre) {
        case "무협":
            genreInstruction = "genre: Wuxia. background: ancient China. Focus on martial arts, honor, and justice, following a protagonist's journey to master martial arts through challenges. The story involves loyalty, betrayal, and clan conflicts, often centered on revenge or justice. Legendary weapons and treasures are important in exploration and battles, with dynamic martial arts techniques and personal growth.";
            break;
        case "로맨스":
            genreInstruction = "genre : romance. back-ground : all \"Harlequin\" is a type of Cinderella story where a poor protagonist meets a rich protagonist. In a romance novel, the key elements are compelling characters and a strong first encounter to capture the reader’s attention, followed by relationship development that naturally shows emotional growth.";
            break;
        case "판타지":
            genreInstruction = "genre : fantasy. background: Medieval Europe or Ancient civilizations Fantasy world. Other-dimensional worldIn a medieval European or alternate dimension setting, magic and supernatural elements play a key role, with wizards and mystical creatures shaping the world.";
            break;
        case "현판":
            genreInstruction = "genre : modern fantasy back-ground : modernFusion of Modern Setting and Supernatural Elements**: In modern society, superpowers, magic, and monsters appear, focusing on extraordinary events within everyday life.";
            break;
        case "로판":
            genreInstruction = "genre : romance fantasy back-ground : Medieval European In a story set in medieval Europe, magic and supernatural beings play a central role, with political intrigue unfolding among kingdoms and noble societies. Wizards, knights, fairies, dragons, and mysterious creatures make up the world, often governed by supernatural rules.";
            break;
        case "일반":
            genreInstruction = generalInstruction;
            break;
        default:
            genreInstruction = "default instruction";  // 예상치 못한 장르일 경우 기본 값 설정
            break;
    }

        // 기본 지시문과 장르별 지시문을 합침
        String finalInstruction = generalInstruction + " " + genreInstruction;

        // 새로운 시스템 지시문 설정
        String newinstruction = "Accurately assess the content of the user's question during the conversation, and respond conversationally with only the requested changes or additional questions. There is no need to repeat the keywords or phrases used by the user. When providing additional answers, it is not necessary to generate the title, characters, and synopsis altogether. Just respond concisely and intuitively with only what the user wants, while referencing the entire context. Do not use unnecessary labeling. It must include the title, characters, and plot when outputting in HTML format.";

        ////////////////////////
        ObjectMapper objectMapper = new ObjectMapper();

     // chatHistory는 대화 기록을 JSON 형식으로 저장하는 리스트
     List<Map<String, String>> chatHistory = new ArrayList<>();

     // 시스템 지시문 추가
     Map<String, String> systemMessage = new HashMap<>();
     systemMessage.put("role", "system");
     systemMessage.put("content", finalInstruction);
     chatHistory.add(systemMessage);

     // 사용자 메시지 추가
     Map<String, String> userMessageMap = new HashMap<>();
     userMessageMap.put("role", "user");
     userMessageMap.put("content", userMessage);
     chatHistory.add(userMessageMap);

     // 새로운 시스템 지시문 추가
     Map<String, String> newSystemMessage = new HashMap<>();
     newSystemMessage.put("role", "system");
     newSystemMessage.put("content", newinstruction);
     chatHistory.add(newSystemMessage);

     // ObjectMapper를 사용하여 chatHistory 리스트를 JSON 배열로 변환
     String messagesJson = objectMapper.writeValueAsString(chatHistory);

     // JSON 요청 본문 생성
     String requestBody = "{"
         + "\"model\": \"gpt-4o\","
         + "\"messages\": " + messagesJson + ","
         + "\"max_tokens\": 2048,"
         + "\"temperature\": 1,"
         + "\"top_p\": 1,"
         + "\"frequency_penalty\": 0,"
         + "\"presence_penalty\": 0"
         + "}";
        
        ////////////////////////
        // API 키 복호화
        API_KEY = getDecryptedApiKey();

        // API 요청을 보내는 부분
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(java.nio.charset.StandardCharsets.UTF_8)
            );

            int statusCode = httpResponse.statusCode();
            if (statusCode == 200) {
                String jsonResponse = httpResponse.body();

                // ObjectMapper로 JSON 파싱
                ObjectMapper objectMapper1 = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResponse);

                // 응답 중 content 필드만 추출
                String content = rootNode.path("choices").get(0).path("message").path("content").asText();

                // 제어 문자 제거 (혹은 적절한 변환)
                content = content.replace("\n", " ").replace("\r", " ").replace("\"", "\\\"");
                
                return content;  // 최종적으로 content만 반환
            } else {
                System.err.println("API 호출 실패: 상태 코드 " + statusCode + httpResponse.body());
                response = "API 호출에 실패했습니다. 상태 코드: " + statusCode;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = "오류가 발생했습니다. 다시 시도해주세요.";
        }

        return response;
    }
}