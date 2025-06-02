package vn.edu.engzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.engzone.entity.TestResult;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
