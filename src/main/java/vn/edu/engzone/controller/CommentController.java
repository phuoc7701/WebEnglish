package vn.edu.engzone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.engzone.dto.request.CommentCreateRequest;
import vn.edu.engzone.dto.request.CommentUpdateRequest;
import vn.edu.engzone.dto.response.CommentResponse;
import vn.edu.engzone.enums.CommentType;
import vn.edu.engzone.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/reference/{referenceId}/{commentType}")
    public ResponseEntity<List<CommentResponse>> getCommentsByReference(
            @PathVariable String referenceId,
            @PathVariable CommentType commentType) {
        List<CommentResponse> responses = commentService.getCommentsByReference(referenceId, commentType);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentCreateRequest request) {
        CommentResponse response = commentService.createComment(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable String commentId,
            @Valid @RequestBody CommentUpdateRequest request) {
        CommentResponse response = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}