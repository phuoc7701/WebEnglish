package vn.edu.engzone.controller;


import jakarta.validation.Valid;
import vn.edu.engzone.dto.request.TestPartRequest;
import vn.edu.engzone.dto.response.TestPartResponse;
import vn.edu.engzone.entity.TestPart;
import vn.edu.engzone.mapper.TestPartMapper;
import vn.edu.engzone.service.TestPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-parts")
public class TestPartController {

    @Autowired
    private TestPartService testPartService;
    @Autowired
    private TestPartMapper testPartMapper;
    // Lấy tất cả part theo testId
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<TestPart>> getPartsByTest(@PathVariable String testId) {
        return ResponseEntity.ok(testPartService.getPartsByTest(testId));
    }

    // Lấy chi tiết một part
    @GetMapping("/{id}")
    public ResponseEntity<TestPartResponse> getPartById(@PathVariable String id) {
        return testPartService.getPartById(id)
                .map(testPartMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Tạo mới một part
    @PostMapping
    public ResponseEntity<TestPartResponse> createPart(@Valid @RequestBody TestPartRequest request) {
        // Convert DTO sang entity
        TestPart created = testPartService.createPart(request); // Service này phải nhận TestPartRequest
        // Convert trả về DTO response
        return ResponseEntity.ok(testPartMapper.toDto(created));
    }

    // Cập nhật một part
    @PutMapping("/{id}")
    public ResponseEntity<TestPartResponse> updatePart(@PathVariable String id, @Valid @RequestBody TestPart testPart) {
        return testPartService.updatePart(id, testPart)
                .map(updated -> ResponseEntity.ok(testPartMapper.toDto(updated)))
                .orElse(ResponseEntity.notFound().build());
    }
    // Xóa một part
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable String id) {
        if (testPartService.deletePart(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}