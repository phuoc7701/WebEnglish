package vn.edu.english.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.english.entity.Role;


public interface RoleRepository extends JpaRepository<Role,Long> {
}
