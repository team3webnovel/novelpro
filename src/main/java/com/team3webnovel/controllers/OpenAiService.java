package com.team3webnovel.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    
    // 장르별 system instruction
    public String generateIntroFromApi(String userMessage, String genre) {
        String response = "";
        String genreInstruction = "";  // 장르별 지시문을 담는 변수

        // 기본 지시문
        String generalInstruction = "Based on the provided input, make sure to write the title, characters, and novel synopsis in Korean. Only one story should be created. Based on the input, the system will reference the genre and background of the example to set character names and settings. You also need to set up the characters. You do not need to directly use the provided keywords in the title or synopsis, but rather expand and interpret their meaning. The story must include 3 characters and the synopsis should be written within 1,000 characters, including twists, entertainment elements, and a coherent storyline. Please format the response with line breaks for easy reading.";
        
        // 장르별 지시문을 설정하는 switch 문
        switch (genre) {
        case "무협":
            genreInstruction = "genre: wuxia back-ground: Ancient China Wuxia is a Chinese genre focused on martial artists in ancient China, emphasizing honor, swordsmanship, and justice. The story follows a protagonist mastering martial arts through trials, with themes of loyalty, betrayal, and clan conflicts. Legendary weapons and treasures play key roles, and dynamic battles showcase martial arts techniques and personal growth.";
            break;
        case "로맨스":
            genreInstruction = "genre: romance back-ground: all, Harlequin is a type of Cinderella story where a poor protagonist meets a rich protagonist. In a romance novel, the key elements are compelling characters and a strong first encounter to capture the reader’s attention, followed by relationship development that naturally shows emotional growth. Conflict and obstacles make the story engaging, while emotional climaxes immerse the reader. A happy ending or open conclusion provides emotional satisfaction.";
            break;
        case "판타지":
            genreInstruction = "genre: fantasy back-ground: medieval Europe or another dimension, Magic and Supernatural Elements: Wizards and mystical creatures are key, with magic influencing the world. Fantasy Races: Elves, dwarves, orcs, and humans coexist. Kingdoms and Empires: Feudal systems and empires engage in wars and intrigue. Religion and Mythology: Myths, gods, and unique religions shape the story.";
            break;
        case "현판":
            genreInstruction = "genre: modern-fantasy back-ground: modern Fusion of Modern Setting and Supernatural Elements: In modern society, superpowers, magic, and monsters appear, focusing on extraordinary events within everyday life. Multidimensional World: Dimensional gates and dungeons emerge, with the protagonist exploring or fighting within these settings. Espers and Supernatural Beings: Espers (superpowered individuals), guides, monsters, and mythical creatures lead the story. Hidden Power or Overpowered Characters: A protagonist who appears ordinary but hides great strength (힘숨찐) or an overpowered character (munchkin) that easily solves all problems. World-Building: Government agencies and secret organizations respond to superpowered individuals, and interconnected stories create a shared universe. Social Change and Conflict: Conflicts arise between superpowered individuals and ordinary people, with themes of tension between modern technology and magical abilities.";
            break;
        case "로판":
            genreInstruction = "genre: romance fantasy background: medieval Europe, Medieval European Setting: Castles, kingdoms, nobles, and knights are central, with magic and political intrigue. Magic and Supernatural Beings: Fairies, dragons, and mysterious creatures play key roles. Royalty and Noble Society: Romance and politics among royalty or nobility drive the story. Reincarnation/Another World: Protagonists may reincarnate or cross worlds. Fated Love: Characters are often bound by destiny.";
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

        // JSON 요청 본문 생성
        String requestBody = "{"
        	    + "\"model\": \"gpt-4\","
        	    + "\"messages\": ["
        	    + "{"
        	    + "\"role\": \"system\","
        	    + "\"content\": \"" + finalInstruction + "\""
        	    + "},"
        	    + "{"
        	    + "\"role\": \"user\","
        	    + "\"content\": \"" + userMessage + "\""
        	    + "}"
        	    + "],"
        	    + "\"max_tokens\": 2048,"
        	    + "\"temperature\": 1,"
        	    + "\"top_p\": 1,"
        	    + "\"frequency_penalty\": 0,"
        	    + "\"presence_penalty\": 0"
        	    + "}";


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
                ObjectMapper objectMapper = new ObjectMapper();
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