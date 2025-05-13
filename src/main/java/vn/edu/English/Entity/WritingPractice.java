package vn.edu.English.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "speaking_practice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WritingPractice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writing_id")
    private Long writingId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    private String content;
    private String systemFeedback;
    private String teacherFeedback;
    private String status;
}
