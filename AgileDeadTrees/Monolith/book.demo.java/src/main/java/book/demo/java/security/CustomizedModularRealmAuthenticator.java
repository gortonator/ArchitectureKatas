package book.demo.java.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomizedModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        assertRealmsConfigured();
        UsernamePasswordLoginTypeToken loginToken = (UsernamePasswordLoginTypeToken) authenticationToken;

        String loginType = loginToken.getLoginType().name();

        Collection<Realm> realms = getRealms();

        Set<Realm> requiredRealms = new HashSet<>();
        for (Realm realm : realms) {
            if (realm.getName().toUpperCase().contains(loginType)) {
                requiredRealms.add(realm);
            }
        }

        if (requiredRealms.size() == 1) {
            return doSingleRealmAuthentication(requiredRealms.iterator().next(), loginToken);
        } else {
            return doMultiRealmAuthentication(requiredRealms, loginToken);
        }
    }

}
