package vn.edu.English.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreate {

    private String fullName = "";
    private String email;
    private LocalDate dob;
    private String sex;
    private String phone;
    private String password;

}
