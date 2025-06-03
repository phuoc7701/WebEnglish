package vn.edu.engzone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id //định nghĩa cho id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;
    String password;
    String email;
    String fullname;
    LocalDate dob;
    @Column(columnDefinition = "INT DEFAULT 1")
    Integer accountStatus = 1 ;
    String avatarUrl;
    String avatarFileId;
    String gender;
    LocalDate premiumExpiry;

    @ManyToMany
    Set<Role> roles;
}
