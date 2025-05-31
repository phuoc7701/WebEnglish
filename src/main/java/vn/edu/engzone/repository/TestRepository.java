package vn.edu.engzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.engzone.entity.Role;
import vn.edu.engzone.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, String> {
}
