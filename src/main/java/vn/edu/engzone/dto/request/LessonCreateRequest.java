package vn.edu.engzone.dto.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    List<QuizQuestionRequest> questions;

    public void setQuestions(String questionsJson) {
        try {
            if (questionsJson != null && !questionsJson.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                this.questions = mapper.readValue(questionsJson, new TypeReference<List<QuizQuestionRequest>>() {});
            }
        } catch (Exception e) {
            this.questions = null;
        }
    }
}