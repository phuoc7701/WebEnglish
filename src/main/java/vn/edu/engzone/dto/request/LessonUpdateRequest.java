package vn.edu.engzone.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonUpdateRequest {
    String title;
    String description;
    String videoUrl;
    MultipartFile videoFile;
    String level;
    String type;
    boolean isPackageRequired;
    String updatedBy;
}