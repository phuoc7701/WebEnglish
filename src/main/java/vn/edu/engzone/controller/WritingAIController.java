package vn.edu.engzone.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.engzone.dto.request.WritingAIRequest;
import vn.edu.engzone.dto.response.AIResponse;
import vn.edu.engzone.service.WritingAIService;

@RestController
@RequestMapping("/writing")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class WritingAIController {

    WritingAIService writingAIService;

    @PostMapping("/generate-topic")
    public ResponseEntity<String> generateEssayTopicAI(String topicInput) {
        String topic;
        if (topicInput == null || topicInput.isEmpty()) {
            topic = writingAIService.generateEssayTopicAI().replaceAll("\\s+", "");
        } else {
            topic = topicInput.replaceAll("\\s+", "");
        }
        return ResponseEntity.ok(topic);
    }

    @PostMapping("/evaluate")
    public ResponseEntity<AIResponse> evaluateEssayWritingAI(@RequestBody WritingAIRequest request) {
        String topic = request.getTopic();
        String writing = request.getWriting();
        AIResponse response = WritingAIService.evaluateEssayAI(topic, writing);
        return ResponseEntity.ok(response);
    }
}

