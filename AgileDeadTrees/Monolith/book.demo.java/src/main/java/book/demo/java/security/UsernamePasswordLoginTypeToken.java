package book.demo.java.security;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serial;

public class UsernamePasswordLoginTypeToken extends UsernamePasswordToken {

    @Serial
    private static final long serialVersionUID = -5632908061953681059L;
    private final LoginType loginType;

    public UsernamePasswordLoginTypeToken(String username, String password, LoginType loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return loginType;
    }

}
