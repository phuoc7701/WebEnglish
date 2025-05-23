package vn.edu.engzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.engzone.entity.Lesson;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {
//    boolean existsByTitle(String title);
//
//    Optional<Lesson> findByTitle(String title);
}