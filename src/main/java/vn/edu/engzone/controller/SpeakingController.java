package vn.edu.engzone.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.engzone.dto.response.AIResponse;
import vn.edu.engzone.service.SpeakingService;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/speaking")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5000")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpeakingController {
    SpeakingService speakingService;
//    @GetMapping("/")
//    public ResponseEntity<?> getSpeakingText() {
//        String prompt = "You are an English test preparation assistant for TOEIC Speaking. " +
//                "Generate one short paragraph (40–60 words) that is appropriate for TOEIC Speaking Question 1 – Read Aloud. " +
//                "The text should reflect a realistic business or daily-life context and contain a variety of vocabulary and sentence structures. " +
//                "Output only the text the user should read aloud.";
//
//        String text = speakingService.callGemini(prompt,null);
//        return ResponseEntity.ok(Map.of("questionText", text));
//    }

    @GetMapping()
    public ResponseEntity<?> getSpeakingText() {
        String prompt = "You are an English test preparation assistant for TOEIC Speaking. " +
                "Generate one short paragraph (50 words) that is appropriate for TOEIC Speaking Question 1 – Read Aloud. " +
                "The text should reflect a realistic business or daily-life context and contain a variety of vocabulary and sentence structures. " +
                "Output only the text the user should read aloud.";

        try {
            String aiResponse = speakingService.callGemini(prompt, null);
//            String text = aiResponse.get;  // hoặc method phù hợp để lấy chuỗi text

            return ResponseEntity.ok(Map.of("questionText", aiResponse));
        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Failed to get speaking text", "details", e.getMessage()));
        }
    }


    @PostMapping("/evaluate")
    public ResponseEntity<?> evaluate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("originalText") String originalText
    ) {
        try {
            String transcribedText = speakingService.sendAudioToPython(file);
            System.out.println(transcribedText);
            String pronunciationPrompt = String.format(
                    "Đánh giá phát âm trong đoạn audio với câu: \"%s\". " +
                            "Đoạn transcript người học đọc lại là: \"%s\". " +
                            "Dùng HTML với <span style='color:red'> đánh dấu từ phát âm sai, " +
                            "<span style='color:green'> đánh dấu từ đúng. " +
                            "Sau đó, liệt kê các lỗi phát âm chính, giải thích chi tiết bằng tiếng Việt cách khắc phục từng lỗi. " +
                            "Trả về kết quả dưới dạng JSON với 2 trường: " +
                            "\"score\" (điểm phát âm từ 0 đến 100 hãy cho điểm chính xác nha nếu đúng hết 100 còn sai hết là 0) và \"feedback\" (đoạn HTML có highlight + phần giải thích lỗi). " +
                            "Chỉ trả về JSON, KHÔNG chèn markdown hay code block.",
                    originalText, transcribedText
            );

            String pronunciationResult = speakingService.callGemini(pronunciationPrompt);
            String jsonText = pronunciationResult.trim();

            // Clean up possible markdown/code block

            jsonText = jsonText.replaceAll("(?s)```json(.*?)```", "$1").trim();
            jsonText = jsonText.replaceAll("(?s)```(.*?)```", "$1").trim();
            jsonText = jsonText.replaceAll("^`+|`+$", "").trim();
            // Parse thành Map trả ra FE
//            ObjectMapper mapper = new ObjectMapper();
//            Map<String, Object> evalObj = mapper.readValue(jsonText, Map.class);
//
//            return ResponseEntity.ok(Collections.singletonMap("pronunciationEvaluation", evalObj));
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> evalObj = mapper.readValue(jsonText, Map.class);
                return ResponseEntity.ok(Collections.singletonMap("pronunciationEvaluation", evalObj));
            } catch (JsonParseException je) {
                return ResponseEntity.status(500)
                        .body(Map.of("error", "Lỗi định dạng JSON từ AI", "rawResponse", jsonText));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Failed to evaluate speaking", "details", e.getMessage()));
        }
    }
}
