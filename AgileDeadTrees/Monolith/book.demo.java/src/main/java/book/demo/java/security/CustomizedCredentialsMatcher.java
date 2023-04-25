package book.demo.java.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;

public class CustomizedCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String inputUsername = usernamePasswordToken.getUsername();
        String inputPassword = new String(usernamePasswordToken.getPassword());
        String dbPassword = (String) info.getCredentials();

        String encryptedPassword = new SimpleHash("MD5", inputPassword, inputUsername, 1024).toString();
        return equals(encryptedPassword, dbPassword);
    }

}
