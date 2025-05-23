//package vn.edu.english.controller.user;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import vn.edu.english.service.user.WritingAIService;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/writing")
//@RequiredArgsConstructor
//public class WritingPracticeController {
//
//    private final WritingAIService writingAIService;
//
//    @PostMapping("/practice")
//    public ResponseEntity<String> writingAI(@RequestBody Map<String, String> payload) {
//        String writing = payload.get("writing");
//        String answer = writingAIService.getAnswer(writing);
//        return ResponseEntity.ok(answer);
//    }
//}
//
