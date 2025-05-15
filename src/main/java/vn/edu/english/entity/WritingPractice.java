package vn.edu.english.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
//@Table(name = "speaking_practice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingPractice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "writing_id")
    String writingId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    User user;

    String title;
    String description;
    String level;
    Integer duration;
    LocalDateTime created_at;
    LocalDateTime updated_at;
    String updated_by;
}
