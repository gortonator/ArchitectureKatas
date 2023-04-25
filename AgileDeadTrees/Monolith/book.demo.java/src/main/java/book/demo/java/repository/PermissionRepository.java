package book.demo.java.repository;

import book.demo.java.entity.account.internal.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    List<Permission> findByRolesId(int roleId);
}
