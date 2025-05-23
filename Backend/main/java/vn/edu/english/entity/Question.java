package vn.edu.english.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @Column(name = "content", nullable = false, unique = true)
    private String content;
    @Column(name="questionType")
    private String questionType;
    @Column(name="answer")
    private String answer;

    private LocalDateTime createdAt;
}
