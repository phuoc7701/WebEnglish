package vn.edu.engzone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String testId;
    private String username;
    private int score;
    private LocalDateTime submitTime;
}
