package book.demo.java.service.impl;

import book.demo.java.entity.account.internal.Role;
import book.demo.java.entity.account.internal.User;
import book.demo.java.repository.UserRepository;
import book.demo.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findById(int userId) {
        return userRepo.findById(userId).orElseThrow(() ->
                new NoSuchElementException("User not found."));
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User addRoleToUser(User user, Role role) {
        user.addRole(role);
        return userRepo.save(user);
    }


}
