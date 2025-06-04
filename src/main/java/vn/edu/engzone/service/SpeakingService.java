package vn.edu.engzone.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vn.edu.engzone.dto.response.AIResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpeakingService {
    WebClient webClient;
    String geminiUploadUrl;
    String geminiGenerateUrl;
    String geminiApiKey;

    public  SpeakingService(
            WebClient.Builder webClientBuilder,
            @Value("${gemini.upload.url}") String geminiUploadUrl,
            @Value("${gemini.api.url}") String geminiGenerateUrl,
            @Value("${gemini.api.key}") String geminiApiKey
    ) {
        this.webClient = webClientBuilder.build();
        this.geminiUploadUrl = geminiUploadUrl;
        this.geminiGenerateUrl = geminiGenerateUrl;
        this.geminiApiKey = geminiApiKey;
    }
    public String callGemini(String promptText, MultipartFile optionalFile) throws IOException {
        String mimeType = "text/plain";
        String fileUri = null;

        if (optionalFile != null) {
            mimeType = optionalFile.getContentType();
            fileUri = uploadFile(optionalFile);
        }

        List<Map<String, Object>> parts = new ArrayList<>();
        parts.add(Map.of("text", promptText));
        if (fileUri != null) {
            parts.add(Map.of("file_data", Map.of(
                    "mime_type", mimeType,
                    "file_uri", fileUri
            )));
        }

        Map<String, Object> body = Map.of(
                "contents", List.of(Map.of("parts", parts))
        );

        JsonNode response = webClient.post()
                .uri(geminiGenerateUrl + "?key=" + geminiApiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        System.out.println(response.toPrettyString());
        if (response == null || !response.has("candidates")) {
            throw new RuntimeException("Invalid Gemini response");
        }

        String text = response.get("candidates")
                .get(0).get("content").get("parts")
                .get(0).get("text").asText();
        log.info("Parsed content: {}", text);
        return text;
    }
    private String uploadFile(MultipartFile file) throws IOException {
        String mimeType = Optional.ofNullable(file.getContentType()).orElse("application/octet-stream");
        long contentLength = file.getSize();

        // 1. Start resumable upload
        String uploadUrl = webClient.post()
                .uri(geminiUploadUrl + "?key=" + geminiApiKey)
                .header("X-Goog-Upload-Protocol", "resumable")
                .header("X-Goog-Upload-Command", "start")
                .header("X-Goog-Upload-Header-Content-Length", String.valueOf(contentLength))
                .header("X-Goog-Upload-Header-Content-Type", mimeType)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(Map.of("file", Map.of("display_name", "audio")))
                .exchangeToMono(response -> {
                    List<String> urls = response.headers().header("X-Goog-Upload-Url");
                    return urls.isEmpty() ? Mono.error(new RuntimeException("No upload URL")) : Mono.just(urls.get(0));
                })
                .block();

        // 2. Upload and finalize
        JsonNode uploadResponse = webClient.post()
                .uri(uploadUrl)
                .header("X-Goog-Upload-Offset", "0")
                .header("X-Goog-Upload-Command", "upload, finalize")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                .bodyValue(file.getBytes())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (uploadResponse == null || !uploadResponse.has("file")) {
            throw new RuntimeException("Upload failed");
        }

        return uploadResponse.get("file").get("uri").asText();
    }

}
