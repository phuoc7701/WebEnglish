package vn.edu.engzone.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String fullname;
    String email;
    String avatarUrl;
    String gender;
    LocalDate dob;
    LocalDate premiumExpiry;
}
