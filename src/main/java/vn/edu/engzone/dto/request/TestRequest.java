package vn.edu.engzone.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class TestRequest {
    @NotBlank(message = "Tiêu đề bài thi không được để trống")
    private String title;
    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotNull(message = "Thời lượng không được null")
    private Integer duration;

    @Size(min = 1, message = "Bài thi phải có ít nhất một phần")
    private List<@Valid TestPartRequest> parts;

    @AssertTrue(message = "Các partNumber không được trùng lặp")
    public boolean isPartNumbersUnique() {
        if (parts == null) return true;
        Set<Integer> seen = new HashSet<>();
        for (TestPartRequest part : parts) {
            if (!seen.add(part.getPartNumber())) {
                return false;
            }
        }
        return true;
    }
}
