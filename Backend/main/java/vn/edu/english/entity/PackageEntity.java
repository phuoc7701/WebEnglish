package vn.edu.english.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Package")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Packages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "name_package", nullable = false, unique = true)
    private String name;

    private String description;

    @Column(name = "duration", nullable = false, unique = true)
    private int duration;

    private String status;

}
