package vn.edu.engzone.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestPart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int partNumber;
    private String partTitle;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
}
