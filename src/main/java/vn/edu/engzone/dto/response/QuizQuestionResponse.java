package vn.edu.engzone.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizQuestionResponse {
    Integer id;
    String question;
    List<String> options;
    Integer correctAnswer;
}