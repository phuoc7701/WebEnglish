
package vn.edu.engzone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.edu.engzone.enums.LessonType;
import vn.edu.engzone.enums.Level;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lesson")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Lesson {

    @Id
    @Column(name = "lesson_id", nullable = false, unique = true, length = 36)
    String lessonId;

    @Column(nullable = false, length = 100)
    String title;

    @Lob
    String description;

    @Column(nullable = false)
    String videoUrl;

    @Column(nullable = false)
    String videoFileId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Level level;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    LessonType type;

    @Column(nullable = false)
    boolean isPackageRequired;

    @CreationTimestamp
    @Column(nullable = false)
    LocalDateTime createdAt;

    @CreatedBy
    @Column(nullable = true, length = 36)
    String createdBy;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(nullable = true, length = 36)
    String updatedBy;

//    @ManyToMany
//    @JoinTable(
//            name = "lesson_topic",
//            joinColumns = @JoinColumn(name = "lesson_id"),
//            inverseJoinColumns = @JoinColumn(name = "topic_id")
//    )
//    List<Topic> topics;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizQuestion> questions;

}
