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
        String generalInstruction = "Based on the provided input, make sure to write the title, characters, and novel synopsis in Korean. Please divide the text into paragraphs based on the title, characters, and synopsis. Only one story should be created. Based on the input, the system will reference the genre and background of the example to set character names and settings. You also need to set up the characters. You do not need to directly use the provided keywords in the title or synopsis, but rather expand and interpret their meaning. The story must include 3 characters and the synopsis should be written within 1,000 characters, including twists, entertainment elements, and a coherent storyline. Please format the response with line breaks for easy reading.";
        
        // 장르별 지시문을 설정하는 switch 문
        switch (genre) {
        case "무협":
            genreInstruction = "genre: Wuxia. background: ancient China. Focus on martial arts, honor, and justice, following a protagonist's journey to master martial arts through challenges. The story involves loyalty, betrayal, and clan conflicts, often centered on revenge or justice. Legendary weapons and treasures are important in exploration and battles, with dynamic martial arts techniques and personal growth. 'Jeoljeong Gosu' refers to a martial artist who has achieved mastery. 'Naegong' is internal energy control (Qi), central to martial arts. 'Gyeonggong' is a technique for swift movement, resembling flying. 'Mugong' includes all martial arts techniques. 'Giyeon' is a special opportunity or fate, like finding powerful weapons or a master. 'Gangho' is the martial world, a separate society with its own code. 'Bigeup' refers to a secret martial arts manual with ancient techniques.";
            break;
        case "로맨스":
            genreInstruction = "genre : romance. back-ground : all \"Harlequin\" is a type of Cinderella story where a poor protagonist meets a rich protagonist. In a romance novel, the key elements are compelling characters and a strong first encounter to capture the reader’s attention, followed by relationship development that naturally shows emotional growth. Conflict and obstacles make the story engaging, while emotional climaxes immerse the reader. A happy ending or open conclusion provides emotional satisfaction. Additionally, romantic settings and dialogue are essential for expressing the characters' emotions and deepening their relationships. Love and the emotions and circumstances of the two main characters are more important.";
            break;
        case "판타지":
            genreInstruction = "genre : fantasy. background: Medieval Europe or Ancient civilizations Fantasy world or Other-dimensional world. In a medieval European or alternate dimension setting, magic and supernatural elements play a key role, with wizards and mystical creatures shaping the world. Various races like elves, dwarves, and orcs coexist alongside humans. Kingdoms and empires with different political systems engage in wars and intrigue. The world is often influenced by unique religions and mythology, where gods play a role in shaping events. Legendary artifacts and sacred locations are central to the plot. The protagonist follows a hero's journey, growing through adventures and challenges, often saving the world. Supernatural creatures like dragons and werewolves appear frequently, and ancient civilizations or lost cities serve as significant story backdrops. A central conflict between good and evil drives the narrative, with powerful heroes and villains. ";
            break;
        case "현판":
            genreInstruction = "genre : modern fantasy back-ground : modernFusion of Modern Setting and Supernatural Elements**: In modern society, superpowers, magic, and monsters appear, focusing on extraordinary events within everyday life. Multidimensional World: Dimensional gates and dungeons emerge, with the protagonist exploring or fighting within these settings.Espers and Supernatural Beings: Espers (superpowered individuals), guides, monsters, and mythical creatures lead the story. Hidden Power or Overpowered Characters: A protagonist who appears ordinary but hides great strength (힘숨찐) or an overpowered character (munchkin) that easily solves all problems.World-Building: Government agencies and secret organizations respond to superpowered individuals, and interconnected stories create a shared universe.Social Change and Conflict: Conflicts arise between superpowered individuals and ordinary people, with themes of tension between modern technology and magical abilities.";
            break;
        case "로판":
            genreInstruction = "genre : romance fantasy back-ground : Medieval European In a story set in medieval Europe, magic and supernatural beings play a central role, with political intrigue unfolding among kingdoms and noble societies. Wizards, knights, fairies, dragons, and mysterious creatures make up the world, often governed by supernatural rules. Romance and political schemes among royalty or the nobility drive the plot, with conflicts such as succession to the throne or marriage alliances serving as major points of tension. Protagonists are often bound by fate, with themes of reincarnation, fated love, and connections to mythology or legends frequently appearing.";
            break;
        case "일반":
            genreInstruction = generalInstruction;
            break;
        default:
            genreInstruction = "default instruction";  // 예상치 못한 장르일 경우 기본 값 설정
            break;
    }

        // 기본 지시문과 장르별 지시문을 합침
//        String finalInstruction = generalInstruction + " " + genreInstruction;
        String finalInstruction = generalInstruction + " " + genreInstruction + " " + 
                "Please return the response separated into three sections: Title, Characters, and Synopsis. Each section should be clearly labeled and formatted as a distinct paragraph.";


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