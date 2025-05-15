package vn.edu.english.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import vn.edu.english.dto.response.WritingPracticeAIResponse;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WritingAIService {
    // Access to APIKey and URL [Gemini]
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public WritingAIService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public WritingPracticeAIResponse getAnswer(String writing) {
        // Prompt cho Gemini
        String prompt = """
                You are a TOEIC writing evaluator.
                
                Please evaluate: %s
                Return response in this JSON format:
                
                {
                  "score": 0-100,
                  "formatted_html": "<html with highlights>"
                }
                
                Instructions:
                - Use <span style=\\"color:#f00;background-color:#ffe6e6;text-decoration:line-through\\"> for wrong parts.
                - Use <span style=\\"color:#008000;background-color:#e6ffe6;margin-left:2px\\"> for corrections.
                - No explanation. Only the corrected HTML.
                """.formatted(writing);

        // Dữ liệu gửi đi
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        try {
            // Gửi yêu cầu tới Gemini
            String rawJson = webClient.post()
                    .uri(geminiApiUrl + "?key=" + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Gemini raw response: {}", rawJson);

            ObjectMapper mapper = new ObjectMapper();

            // Parse lần 1: Lấy JSON string từ candidates
            Map<?, ?> outer = mapper.readValue(rawJson, Map.class);
            List<?> candidates = (List<?>) outer.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                throw new RuntimeException("No candidates returned from Gemini.");
            }

            Map<?, ?> content = (Map<?, ?>) ((Map<?, ?>) candidates.get(0)).get("content");
            List<?> parts = (List<?>) content.get("parts");

            String jsonText = (String) ((Map<?, ?>) parts.get(0)).get("text");

            // Xử lý nếu text bị thừa dấu ` hoặc markdown
            jsonText = jsonText.strip().replaceAll("^```json\\s*|```$", "");

            // Parse lần 2: jsonText thành object
            return mapper.readValue(jsonText, WritingPracticeAIResponse.class);

        } catch (Exception e) {
            log.error("Error parsing AI response", e);
            throw new RuntimeException("Invalid AI response", e);
        }
    }
}

