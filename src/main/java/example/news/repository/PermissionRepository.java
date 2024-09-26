package example.news.repository;

import example.news.entity.Permission;
import example.news.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository <Permission, Long> {
    Permission findByName(String name);
}
