package example.news.repository;

import example.news.entity.Role;
import example.news.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Role findByName(String name);
}
