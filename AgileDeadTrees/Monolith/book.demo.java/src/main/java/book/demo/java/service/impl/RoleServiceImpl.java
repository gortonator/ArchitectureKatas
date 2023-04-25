package book.demo.java.service.impl;

import book.demo.java.entity.account.internal.Role;
import book.demo.java.repository.RoleRepository;
import book.demo.java.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public Role findById(int roleId) {
        return roleRepo.findById(roleId).orElseThrow(() ->
                new NoSuchElementException("Role not found with given id."));
    }
}
