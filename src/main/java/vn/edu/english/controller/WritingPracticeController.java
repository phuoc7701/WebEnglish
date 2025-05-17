package vn.edu.english.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.english.dto.request.WritingPracticeRequest;
import vn.edu.english.dto.response.WritingPracticeAIResponse;
import vn.edu.english.service.WritingAIService;

import java.util.Map;

@RestController
@RequestMapping("/writing")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class WritingPracticeController {

    WritingAIService writingAIService;

    @PostMapping("/practice")
    public ResponseEntity<WritingPracticeAIResponse> writingAI(@RequestBody Map<String, String> payload) {
        String writing = payload.get("writing");
        WritingPracticeAIResponse answer = writingAIService.getAnswer(writing);
        return ResponseEntity.ok(answer);
    }
}

