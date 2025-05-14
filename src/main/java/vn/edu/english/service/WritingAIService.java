package vn.edu.english.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
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

    public String getAnswer(String writing) {
        // Construct the request payload
//        {"contents":[{"parts":[{"text": "QUESTION"]}]}
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text",  "You are a TOEIC writing evaluator.\n" +
                                        "\n" +
                                        "Please evaluate " + writing + " and return your response in this exact JSON format:\n" +
                                        "\n" +
                                        "{\n" +
                                        "  \"score\": [a number from 0 to 100],\n" +
                                        "  \"formatted_html\": \"[the original text corrected with red strikethrough for errors and green text for corrections, in HTML format]\"\n" +
                                        "}\n" +
                                        "\n" +
                                        "Instructions:\n" +
                                        "- Use `<span style=\"color:#f00;background-color:#ffe6e6;text-decoration:line-through\">` for incorrect words or phrases.\n" +
                                        "- Use `<span style=\"color:#008000;background-color:#e6ffe6\"\">` for corrected or newly added text.\n" +
                                        "- Return the full corrected version in HTML, ready to be rendered on a webpage.\n" +
                                        "- Do not include any explanation or comments.\n")
                        })
                }
        );

        // Make API Call
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Return response
        return response;
    }
}

