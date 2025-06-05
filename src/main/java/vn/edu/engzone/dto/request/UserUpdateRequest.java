package vn.edu.engzone.dto.request;

import vn.edu.engzone.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String email;
    String fullname;

    @DobConstraint(min = 2, message = "INVALID_DOB")
    LocalDate dob;
    Boolean accountStatus;
    List<String> roles;


}
