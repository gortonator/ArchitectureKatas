package book.demo.java.security;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordLoginTypeToken extends UsernamePasswordToken {

    private final LoginType loginType;

    public UsernamePasswordLoginTypeToken(String username, String password, LoginType loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return loginType;
    }

}
