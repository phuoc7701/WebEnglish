package vn.edu.engzone.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import vn.edu.engzone.dto.response.AIResponse;
import vn.edu.engzone.prompt.GeminiPrompt;
import vn.edu.engzone.service.WritingAIService;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WritingAIServiceImpl implements WritingAIService {
    static String geminiGenerateUrl;
    static String geminiApiKey;
    static WebClient webClient;
    static ObjectMapper mapper = new ObjectMapper();

    public WritingAIServiceImpl(WebClient.Builder webClientBuilder, Dotenv dotenv) {
        this.webClient = webClientBuilder.build();
        this.geminiGenerateUrl = dotenv.get("GEMINI_API_URL");
        this.geminiApiKey = dotenv.get("GEMINI_API_KEY");
    }

    @Override
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
        String prompt = """
                You are an experienced evaluator for TOEIC Writing Part 3 (Write an Opinion Essay – Task 8).
                In this part, the test taker is expected to write a minimum 300-word opinion essay
                that clearly states, explains, and supports their opinion on a given issue.
                
                Here is the prompt and the essay to evaluate:
                
                Topic: %s
                
                Essay: %s
                
                Please return your evaluation in the following JSON format:
                {
                    "score": 0-30,
                    "formatted_html": "<html with corrections and highlights>",
                    "review_comment_vi": "Brief comment in Vietnamese, pointing out strengths and areas for improvement."
                }
                
                Correction instructions:
                    - Highlight incorrect grammar, vocabulary, or structure using: \s
                        <span style=\\"color:#f00;background-color:#ffe6e6;text-decoration:line-through\\">
                    - Provide the corrected version beside it using: \s
                        <span style=\\"color:#008000;background-color:#e6ffe6;margin-left:2px\\">
                    - Do not include explanations or comments in the HTML.
                    - Do not return anything except the formatted JSON.
                """.formatted(topic, writing);

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

    static public String parseResponse(String response) {
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

    static public AIResponse parseAIResponse(String rawJson) {
        try {
            rawJson = rawJson.strip().replaceAll("^```json\\s*|```$", "");

            return mapper.readValue(rawJson, AIResponse.class);
        } catch (Exception e) {
            log.error("Error parsing Gemini response", e);
            throw new RuntimeException("Invalid Gemini response format", e);
        }
    }
}
