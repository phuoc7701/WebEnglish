package vn.edu.engzone.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponse {
    String lessonId;
    String title;
    String description;
    String videoUrl;
    String level;
    String type;
    boolean isPackageRequired;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
    List<QuizQuestionResponse> questions;
}