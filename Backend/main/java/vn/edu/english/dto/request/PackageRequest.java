package vn.edu.english.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageRequest {

    @NotBlank(message = "Không được để trống")
    private String name;

    private String description;
    @Min(value = 1, message = "Thời lượng phải lớn hơn 0")
    private int duration;
    @NotBlank(message = "Không được để trống")
    private String status;

}
