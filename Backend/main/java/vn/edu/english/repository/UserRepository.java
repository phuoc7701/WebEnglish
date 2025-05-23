package vn.edu.english.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.english.Entity.User;

public interface UserReponsitory extends JpaRepository<User,String> {
}
