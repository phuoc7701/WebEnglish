package vn.edu.engzone.repository;

import vn.edu.engzone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
