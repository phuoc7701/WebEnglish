package vn.edu.engzone.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizQuestionRequest {
    Integer id;
    String question;
    List<String> options;
    Integer correctAnswer;
}