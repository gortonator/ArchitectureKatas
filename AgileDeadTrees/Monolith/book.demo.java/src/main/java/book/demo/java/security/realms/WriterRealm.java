package book.demo.java.security.realms;

import book.demo.java.entity.account.external.Writer;
import book.demo.java.service.WriterService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class WriterRealm extends AuthorizingRealm {

    @Autowired
    private WriterService writerService;

    {
        super.setName("WriterRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = principalCollection.getPrimaryPrincipal().toString();

        Writer writer = writerService.findWriterByUsername(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRole(writer.getRole());

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String tokenUsername = token.getUsername();
        Writer writer = writerService.findWriterByUsername(tokenUsername);

        if (writer == null) {
            return null;
        }

        String password = writer.getPassword();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(tokenUsername, password, getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(tokenUsername));
        return simpleAuthenticationInfo;
    }
}
