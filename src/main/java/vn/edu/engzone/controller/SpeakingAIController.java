//package vn.edu.engzone.controller;
//
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import vn.edu.engzone.dto.response.AIResponse;
//import vn.edu.engzone.service.SpeakingAIService;
//
//@RestController
//@RequestMapping("/speaking")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//
//public class SpeakingAIController {
//
//    SpeakingAIService speakingAIService;
//
//    /**
//     * Endpoint to handle audio file and reference text for speaking practice.
//     *
//     * @param file     The audio file uploaded by the user.
//     * @param refText  The reference text for the speaking practice.
//     * @return AIResponse containing the result of the speaking practice.
//     */
//
////    @PostMapping("/practice")
////    public ResponseEntity<AIResponse> speakingAI(
////            @RequestParam("file") MultipartFile file,
////            @RequestParam("refText") String refText) {
////        AIResponse response = speakingAIService.handleAudio(file, refText);
////        return ResponseEntity.ok(response);
////    }
////}
//
