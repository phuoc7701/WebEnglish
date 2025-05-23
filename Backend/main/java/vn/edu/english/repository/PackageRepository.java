package vn.edu.english.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.english.entity.PackageEntity;

public interface PackageRepository extends JpaRepository<PackageEntity,Long> {
}
