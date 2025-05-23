package vn.edu.english.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.english.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String Email);
}
