package vn.edu.engzone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String question;

    @ElementCollection
    private List<String> options;

    private String correctAnswer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "part_id")
    private TestPart part;
}
