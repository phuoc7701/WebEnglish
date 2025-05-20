package vn.edu.engzone.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.engzone.dto.response.AIResponse;
import vn.edu.engzone.service.SpeakingAIService;

@RestController
@RequestMapping("/speaking")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class SpeakingAIController {

    SpeakingAIService speakingAIService;

    @PostMapping("/practice")
    public ResponseEntity<AIResponse> speakingAI(
            @RequestParam("file") MultipartFile file,
            @RequestParam("refText") String refText) {
        AIResponse response = speakingAIService.handleAudio(file, refText);
        return ResponseEntity.ok(response);
    }
}

