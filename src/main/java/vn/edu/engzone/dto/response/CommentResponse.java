package vn.edu.engzone.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.engzone.enums.CommentType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {

    String commentId;
    String content;
    String userId;
    String username;
    String referenceId;
    CommentType commentType;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
}