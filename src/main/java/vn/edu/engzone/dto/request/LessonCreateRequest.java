package vn.edu.engzone.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonCreateRequest {
    String title;
    String description;
    MultipartFile videoFile;
    String level;
    String type;
    boolean isPackageRequired;
//    String topics;
    String createdBy;
    String updatedBy;
}