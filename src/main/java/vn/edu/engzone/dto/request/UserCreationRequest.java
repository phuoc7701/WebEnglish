package vn.edu.engzone.dto.request;

import vn.edu.engzone.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // mọi field trong class nếu không có xác định thì nó là private
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD") //validate cho password
    String password;
    String email;
    String fullname;
    Integer status = 1;
    String role;
    @DobConstraint(min = 2, message = "INVALID_DOB")
    LocalDate dob;


}
