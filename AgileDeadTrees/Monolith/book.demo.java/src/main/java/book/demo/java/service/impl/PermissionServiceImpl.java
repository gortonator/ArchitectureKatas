package book.demo.java.service.impl;

import book.demo.java.entity.account.internal.Permission;
import book.demo.java.repository.PermissionRepository;
import book.demo.java.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permRepo;

    @Override
    public Set<String> getPermissionNamesByRoleId(int roleId) {

        return permRepo.findByRolesId(roleId).stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }
}
