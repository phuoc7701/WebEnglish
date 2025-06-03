//package vn.edu.engzone.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.github.cdimascio.dotenv.Dotenv;
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//import vn.edu.engzone.dto.response.AIResponse;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@Slf4j
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class SpeakingAIService {
//
//    String geminiUploadUrl;
//    String geminiApiKey;
//    String geminiGenerateUrl;
//    WebClient webClient;
//
//    public SpeakingAIService(WebClient.Builder webClientBuilder, Dotenv dotenv) {
//        this.webClient = webClientBuilder.build();
//        this.geminiUploadUrl=dotenv.get("GEMINI_UPLOAD_URL");
//        this.geminiGenerateUrl = dotenv.get("GEMINI_API_URL");
//        this.geminiApiKey = dotenv.get("GEMINI_API_KEY");
//    }
//
//    public AIResponse handleAudio(MultipartFile file, String refText) {
//        try {
//            String mimeType = Optional.ofNullable(file.getContentType()).orElse("application/octet-stream");
//            long contentLength = file.getSize();
//
//            // Step 1: Start resumable upload => lấy upload URL
//            String uploadUrl = webClient.post()
//                    .uri(geminiUploadUrl + "?key=" + geminiApiKey)
//                    .header("X-Goog-Upload-Protocol", "resumable")
//                    .header("X-Goog-Upload-Command", "start")
//                    .header("X-Goog-Upload-Header-Content-Length", String.valueOf(contentLength))
//                    .header("X-Goog-Upload-Header-Content-Type", mimeType)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .bodyValue(Map.of("file", Map.of("display_name", "audio")))
//                    .exchangeToMono(response -> {
//                        List<String> urls = response.headers().header("X-Goog-Upload-Url");
//                        if (urls.isEmpty()) {
//                            return Mono.error(new RuntimeException("No upload URL in response headers"));
//                        }
//                        return Mono.just(urls.get(0));
//                    })
//                    .block();
//
//            if (uploadUrl == null) throw new RuntimeException("Upload URL is null");
//
//            // Step 2: Upload file binary + finalize upload
//            JsonNode uploadResponse = webClient.post()
//                    .uri(uploadUrl)
//                    .header("X-Goog-Upload-Offset", "0")
//                    .header("X-Goog-Upload-Command", "upload, finalize")
//                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
//                    .bodyValue(file.getBytes())
//                    .retrieve()
//                    .bodyToMono(JsonNode.class)
//                    .block();
//
//            if (uploadResponse == null || !uploadResponse.has("file")) {
//                throw new RuntimeException("Upload failed, no file info");
//            }
//
//            String fileUri = uploadResponse.get("file").get("uri").asText();
//
//            // Step 3: Prompt Gemini để đánh giá
//            String prompt = String.format(
//                    "Evaluate the pronunciation in this audio for the sentence: \"%s\". " +
//                            "Return HTML with <span style='color:red'> for mispronounced words and " +
//                            "<span style='color:green'> for correct ones. Also, give a score from 0-100 as JSON: {\"score\": X, \"formatted_html\": \"...\"}",
//                    refText
//            );
//
//            // Step 4: Gọi Gemini generateContent API để lấy mô tả
//            Map<String, Object> body = Map.of(
//                    "contents", List.of(
//                            Map.of("parts", List.of(
//                                    Map.of("text", prompt),
//                                    Map.of("file_data", Map.of(
//                                            "mime_type", mimeType,
//                                            "file_uri", fileUri
//                                    ))
//                            ))
//                    )
//            );
//
//            JsonNode response = webClient.post()
//                    .uri(geminiGenerateUrl + "?key=" + geminiApiKey)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .bodyValue(body)
//                    .retrieve()
//                    .bodyToMono(JsonNode.class)
//                    .block();
//
//            if (response == null || !response.has("candidates")) {
//                throw new RuntimeException("Invalid response from Gemini API");
//            }
//
//            String jsonText = response.get("candidates")
//                    .get(0).get("content").get("parts")
//                    .get(0).get("text").asText();
//
//            // Parse JSON response từ Gemini
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(jsonText, AIResponse.class);
//
//        } catch (Exception e) {
//            log.error("Failed to process audio in SpeakingAIService", e);
//            throw new RuntimeException("Failed to describe audio", e);
//        }
//    }
//}
