package book.demo.java.repository;

import book.demo.java.entity.account.internal.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
