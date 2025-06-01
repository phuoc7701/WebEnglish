package vn.edu.engzone.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.engzone.dto.request.CommentCreateRequest;
import vn.edu.engzone.dto.request.CommentUpdateRequest;
import vn.edu.engzone.dto.response.CommentResponse;
import vn.edu.engzone.entity.Comment;
import vn.edu.engzone.entity.User;
import vn.edu.engzone.enums.CommentType;
import vn.edu.engzone.mapper.CommentMapper;
import vn.edu.engzone.repository.CommentRepository;
import vn.edu.engzone.repository.LessonRepository;
import vn.edu.engzone.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final CommentMapper commentMapper;

    public CommentResponse createComment(CommentCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getCommentType() == CommentType.LESSON) {
            lessonRepository.findById(request.getReferenceId())
                    .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        } else {
            throw new IllegalArgumentException("Invalid comment type");
        }

        Comment comment = commentMapper.toComment(request);
        comment.setUser(user);
        System.out.println("User ID before save: " + (user != null ? user.getId() : "null"));
        comment = commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment);
    }

    public List<CommentResponse> getCommentsByReference(String referenceId, CommentType commentType) {
        List<Comment> comments = commentRepository.findByReferenceIdAndCommentType(
                referenceId,
                commentType,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return comments.stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }

    public CommentResponse updateComment(String commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        commentMapper.updateComment(request, comment);
        comment = commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment);
    }

    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        commentRepository.delete(comment);
    }
}