package vn.edu.english.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 3, message = "Tên phải có ít nhất 3 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    private String email;

    private LocalDate dob;

    @NotBlank(message = "Giới tính không được để trống")
    private String sex;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;
    @NotNull
    private Long roleId;;
}
