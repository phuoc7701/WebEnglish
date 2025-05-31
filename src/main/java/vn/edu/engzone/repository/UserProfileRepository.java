package vn.edu.engzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.engzone.entity.User;

public interface UserProfileRepository extends JpaRepository<User, String> {
}
