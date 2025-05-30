package vn.edu.engzone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.edu.engzone.enums.CommentType;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_id", nullable = false, unique = true, length = 36)
    String commentId;

    @Lob
    @Column(nullable = false)
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "reference_id", nullable = false, length = 36)
    String referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", nullable = false)
    CommentType commentType;

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
}
