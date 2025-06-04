package vn.edu.engzone.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String id;
    private String question;
    private List<String> options;
    private String correctAnswer;
}
