package vn.edu.engzone.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.engzone.entity.Comment;
import vn.edu.engzone.enums.CommentType;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {

    @EntityGraph(attributePaths = {"user"})
    Optional<Comment> findById(String commentId);

    @EntityGraph(attributePaths = {"user"})
    List<Comment> findByReferenceIdAndCommentType(String referenceId, CommentType commentType);

    void deleteByReferenceIdAndCommentType(String referenceId, CommentType commentType);
}