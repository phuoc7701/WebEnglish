package vn.edu.engzone.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String id;

    @NotBlank(message = "Câu hỏi không được để trống")
    private String question;

    @Size(min = 2, max = 4, message = "Mỗi câu hỏi phải có từ 2 đến 4 phương án")
    private List<@NotBlank(message = "Phương án không được rỗng") String> options;

    @NotBlank(message = "Đáp án đúng không được để trống")
    private String correctAnswer;
}
