package vn.edu.engzone.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String fullname;
    String avatarUrl;
    LocalDate dob;
    Integer accountStatus;
    Set<RoleResponse> roles;
}
