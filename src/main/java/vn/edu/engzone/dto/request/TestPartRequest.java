package vn.edu.engzone.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class TestPartRequest {
    private String id;

    @NotNull(message = "Số thứ tự phần không được null")
    private Integer partNumber;

    @NotBlank(message = "Tên phần không được để trống")
    private String partTitle;

    @Size(min = 1, message = "Phần phải có ít nhất 1 câu hỏi")
    private List<@Valid QuestionRequest> questions;

    private String testId;
}