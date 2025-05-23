package vn.edu.english.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    @NotBlank(message = "Không được để trống")
    private String content;
    @NotBlank(message = "Không được để trống")
    private String questionType;
    @NotBlank(message = "Không được để trống")
    private String answer;

    private LocalDateTime created_at;
}
