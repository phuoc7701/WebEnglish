package vn.edu.engzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.engzone.entity.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
}