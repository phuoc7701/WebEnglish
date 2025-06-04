package vn.edu.engzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.engzone.entity.TestPart;

import java.util.List;

@Repository
public interface TestPartRepository  extends JpaRepository<TestPart, String> {
    List<TestPart> findByTestId(String testId);
}
