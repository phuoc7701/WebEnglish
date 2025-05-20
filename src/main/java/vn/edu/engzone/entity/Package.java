package vn.edu.engzone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import vn.edu.engzone.enums.PackageType;

import java.time.LocalDateTime;

@Entity
@Table(name = "package")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id", nullable = false, unique = true)
    Integer packageId;

    @Column(nullable = false, length = 50)
    String name;

    String description;

    @Column(nullable = false)
    Float price;

    @Column(nullable = false)
    Integer duration;

    @Column(nullable = false)
    PackageType packageType;

    //    @CreatedBy
//    @Column(nullable = true, length = 36)
//    String createdBy;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(nullable = true, length = 36)
    String updatedBy;
}
