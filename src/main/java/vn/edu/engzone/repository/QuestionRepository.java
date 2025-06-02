package vn.edu.engzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.engzone.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    // Custom query methods can be defined here if needed
}
