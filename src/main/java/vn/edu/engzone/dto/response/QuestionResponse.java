package vn.edu.engzone.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class QuestionResponse {
    private String id;
    private String question;
    private List<String> options;
    private String correctAnswer;
}
