package vn.edu.engzone.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonCreateRequest {
    String title;
    String description;
    String videoUrl;
    String level;
    String type;
    String imageUrl;
    boolean isPackageRequired;
    String createdBy;
    String updatedBy;
}
