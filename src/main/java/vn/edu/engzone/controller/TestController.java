package vn.edu.engzone.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.engzone.dto.request.TestRequest;
import vn.edu.engzone.dto.response.TestResponse;
import vn.edu.engzone.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@CrossOrigin
public class TestController {
    private final TestService testService;

    @GetMapping
    public ResponseEntity<List<TestResponse>> getAll() {
        List<TestResponse> tests = testService.getAllTests();
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getOne(@PathVariable String id) {
        TestResponse test = testService.getTest(id);
        if (test == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(test);
    }

    @PostMapping
    public ResponseEntity<TestResponse> create(@RequestBody TestRequest request) {
        TestResponse created = testService.createTest(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestResponse> update(@PathVariable String id, @RequestBody TestRequest request) {
        TestResponse updated = testService.updateTest(id, request);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = testService.deleteTest(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}