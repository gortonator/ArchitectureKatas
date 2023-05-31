/**
 * This is a controller class handling HTTP requests related to User entity manipulation.
 * <p>
 * Endpoints:
 * GET /api/users/all: Get all users.
 * GET /api/users/{userId}: Get a user by userId.
 * PUT /api/users/add-role: Add a role to user.
 * <p>
 * Notes:
 * Additional dto could be used when returning User information to exclude fields like password.
 *
 * @author Tong
 */

package book.demo.java.controller;

import book.demo.java.entity.account.internal.Role;
import book.demo.java.entity.account.internal.User;
import book.demo.java.service.RoleService;
import book.demo.java.service.UserService;
import book.demo.java.util.PredefinedRole;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Get all users.")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get a user by userId.")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        User user = userService.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Add a role to user.")
    @PutMapping("/add-role")
    @RequiresRoles(PredefinedRole.ADMIN_ROLE)
    public ResponseEntity<User> addRoleToUser(int userId, int roleId) {
        User user = userService.findById(userId);
        Role role = roleService.findById(roleId);
        User updatedUser = userService.addRoleToUser(user, role);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


}
