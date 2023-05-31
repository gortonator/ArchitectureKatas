package book.demo.java.graphql_controller;


import java.util.List;
import book.demo.java.service.UserService;
import book.demo.java.entity.account.internal.Role;
import book.demo.java.entity.account.internal.User;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;


@Controller
public class UserControllers {

   private UserService userService;
  
   public UserControllers(UserService userService) {
       this.userService = userService;
   }

   @QueryMapping
   public List<User> getAllUsers() {
       return userService.getAllUsers();
   }


//    @QueryMapping
//    public User findById(@Argument Integer userId) {
//        return userService.findById(userId);
//    }

   @QueryMapping
   public User findByUsername(@Argument String username) {
       return userService.findByUsername(username);
   }


//    @MutationMapping
//    public User addRoleToUser(@Argument User user, @Argument Role role) {
//        return userService.addRoleToUser(user,role);
//    }
}
