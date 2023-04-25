package book.demo.java.security;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomizedModularRealmAuthorizer extends ModularRealmAuthorizer {

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();

        Set<String> requiredRealmName = getRequiredRealmNames(principals.getRealmNames());

        List<Authorizer> realms = getRequiredRealms(requiredRealmName);

        for (Authorizer realm : realms) {
            if (realm.isPermitted(principals, permission)) return true;
        }

        return false;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        assertRealmsConfigured();

        Set<String> requiredRealmName = getRequiredRealmNames(principals.getRealmNames());

        List<Authorizer> realms = getRequiredRealms(requiredRealmName);

        for (Authorizer realm : realms) {
            if (realm.isPermitted(principals, permission)) return true;
        }

        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();

        Set<String> requiredRealmName = getRequiredRealmNames(principals.getRealmNames());

        List<Authorizer> realms = getRequiredRealms(requiredRealmName);

        for (Authorizer realm : realms) {
            if (realm.hasRole(principals, roleIdentifier)) return true;
        }

        return false;
    }

    private Set<String> getRequiredRealmNames(Set<String> realmName) {
        return realmName.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
    }

    private List<Authorizer> getRequiredRealms(Set<String> requiredRealmName) {
        return getRealms().stream()
                .filter(Authorizer.class::isInstance)
                .filter(realm -> requiredRealmName.contains(realm.getName().toUpperCase()))
                .map(Authorizer.class::cast)
                .toList();
    }

}
