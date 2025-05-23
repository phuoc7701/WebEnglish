package vn.edu.english.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.english.entity.Question;
import vn.edu.english.entity.Role;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    boolean existsByContent(String content);
}
