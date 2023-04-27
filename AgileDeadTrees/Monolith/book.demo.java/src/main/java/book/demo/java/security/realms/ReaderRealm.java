package book.demo.java.security.realms;

import book.demo.java.entity.account.external.Reader;
import book.demo.java.service.ReaderService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class ReaderRealm extends AuthorizingRealm {

    @Autowired
    private ReaderService readerService;

    {
        super.setName("ReaderRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = principalCollection.getPrimaryPrincipal().toString();
        Reader reader = readerService.findReaderByUsername(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRole(reader.getRole());

        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String tokenUsername = token.getUsername();
        Reader reader = readerService.findReaderByUsername(tokenUsername);

        if (reader == null) {
            return null;
        }

        String password = reader.getPassword();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                tokenUsername, password, getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(tokenUsername));
        return simpleAuthenticationInfo;
    }
}

