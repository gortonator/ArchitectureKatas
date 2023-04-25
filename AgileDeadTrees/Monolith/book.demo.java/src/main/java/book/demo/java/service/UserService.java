package book.demo.java.service;

import book.demo.java.entity.account.internal.Role;
import book.demo.java.entity.account.internal.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User findById(int userId);

    User findByUsername(String username);

    User addRoleToUser(User user, Role role);
}
