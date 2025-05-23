package vn.edu.engzone.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    String imageUrl;
    boolean isPackageRequired;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String updatedBy;
}