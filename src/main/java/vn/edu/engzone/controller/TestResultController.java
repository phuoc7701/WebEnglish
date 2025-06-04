package vn.edu.engzone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.engzone.service.TestResultService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@CrossOrigin
public class TestResultController {
    private final TestResultService testResultService;

    // DTO nhận dữ liệu từ frontend
    public static class SubmitRequest {
        public String testId;
        public Map<String, String> answers;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitTest(
            @RequestBody SubmitRequest request,
            Principal principal
    ) {
        String username = principal.getName(); // Lấy username từ token đăng nhập
        int score = testResultService.evaluateTest(request.testId, request.answers, username);
        return ResponseEntity.ok(Map.of("success", true, "score", score));
    }
}