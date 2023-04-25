package book.demo.java.security.realms;

import book.demo.java.entity.account.internal.Permission;
import book.demo.java.entity.account.internal.Role;
import book.demo.java.entity.account.internal.User;
import book.demo.java.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    {
        super.setName("UserRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = principalCollection.getPrimaryPrincipal().toString();

        User user = userService.findByUsername(username);

        Set<String> stringPerms = new HashSet<>();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        for (Role role : user.getRoles()) {
            info.addRole(role.getName());
            for (Permission permission : role.getPermissions()) {
                stringPerms.add(permission.getName());
            }
        }

        info.addStringPermissions(stringPerms);
        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String tokenUsername = token.getUsername();

        User user = userService.findByUsername(tokenUsername);
        if (user == null) {
            return null;
        }

        String password = user.getPassword();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(tokenUsername, password, getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(tokenUsername));
        return simpleAuthenticationInfo;
    }
}

