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
    @Column(unique = true)
    String email;
    String fullname;
    LocalDate dob;
    @Column(name = "account_status", nullable = false)
    Boolean accountStatus = true ;
    String avatarUrl;
    String avatarFileId;
    String gender;
    LocalDate premiumExpiry;

    @ManyToMany
    Set<Role> roles;
}
