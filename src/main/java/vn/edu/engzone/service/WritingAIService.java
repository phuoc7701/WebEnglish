package vn.edu.engzone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import vn.edu.engzone.dto.response.AIResponse;
import vn.edu.engzone.prompt.GeminiPrompt;

import java.util.List;
import java.util.Map;

import static vn.edu.engzone.prompt.GeminiPrompt.generateEssayAnswerPrompt;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WritingAIService {
    static String geminiGenerateUrl;
    static String geminiApiKey;
    static WebClient webClient;
    static ObjectMapper mapper = new ObjectMapper();

    public WritingAIService(WebClient.Builder webClientBuilder, Dotenv dotenv) {
        this.webClient = webClientBuilder.build();
        this.geminiGenerateUrl = dotenv.get("GEMINI_API_URL");
        this.geminiApiKey = dotenv.get("GEMINI_API_KEY");
    }

    public String generateEssayTopicAI() {
        String prompt = GeminiPrompt.generateEssayTopicPrompt();

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        try {
            String response = webClient.post()
                    .uri(geminiGenerateUrl + "?key=" + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse topic from response
            return parseResponse(response);

        } catch (Exception e) {
            log.error("Failed to generate topic", e);
            throw new RuntimeException("Cannot generate TOEIC topic", e);
        }
    }

    public static AIResponse evaluateEssayAI(String topic, String writing) {
        String prompt = generateEssayAnswerPrompt(topic, writing);

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        try {
            String response = webClient.post()
                    .uri(geminiGenerateUrl + "?key=" + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse answer from response
            return parseAIResponse(parseResponse(response));
        } catch (Exception e) {
            log.error("Failed to get Gemini response", e);
            throw new RuntimeException("Failed to connect to Gemini API", e);
        }
    }

     public static String parseResponse(String response) {
        try {
            Map<?, ?> outer = mapper.readValue(response, Map.class);
            List<?> candidates = (List<?>) outer.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                throw new RuntimeException("No candidates returned from Gemini.");
            }

            Map<?, ?> content = (Map<?, ?>) ((Map<?, ?>) candidates.get(0)).get("content");
            List<?> parts = (List<?>) content.get("parts");

            return (String) ((Map<?, ?>) parts.get(0)).get("text").toString().strip();
        } catch (Exception e) {
            log.error("Error parsing Gemini response", e);
            throw new RuntimeException("Invalid Gemini response format", e);
        }
    }

     public static AIResponse parseAIResponse(String rawJson) {
        try {
            rawJson = rawJson.strip().replaceAll("^```json\\s*|```$", "");

            return mapper.readValue(rawJson, AIResponse.class);
        } catch (Exception e) {
            log.error("Error parsing Gemini response", e);
            throw new RuntimeException("Invalid Gemini response format", e);
        }
    }
}

