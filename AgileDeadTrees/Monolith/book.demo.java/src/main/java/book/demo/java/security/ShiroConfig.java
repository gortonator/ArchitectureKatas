package book.demo.java.security;

import book.demo.java.security.realms.ReaderRealm;
import book.demo.java.security.realms.UserRealm;
import book.demo.java.security.realms.WriterRealm;

import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterMap = filterChainDefinitionMap();

//        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setLoginUrl("/api/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SimpleCredentialsMatcher CredentialsMatcher() {
        CustomizedCredentialsMatcher credentialsMatcher = new CustomizedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);

        return credentialsMatcher;
    }

    @Bean
    public ReaderRealm readerRealm() {
        ReaderRealm readerRealm = new ReaderRealm();
        readerRealm.setCredentialsMatcher(CredentialsMatcher());
        return readerRealm;
    }

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(CredentialsMatcher());
        return userRealm;
    }

    @Bean
    public WriterRealm writerRealm() {
        WriterRealm writerRealm = new WriterRealm();
        writerRealm.setCredentialsMatcher(CredentialsMatcher());
        return writerRealm;
    }

    @Bean
    public ModularRealmAuthenticator customizedModularRealmAuthenticator() {
        CustomizedModularRealmAuthenticator modularRealmAuthenticator = new CustomizedModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    @Bean
    public ModularRealmAuthorizer customizedModularRealmAuthorizer() {
        return new CustomizedModularRealmAuthorizer();
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        Set<Realm> realms = new HashSet<>();
        realms.add(userRealm());
        realms.add(readerRealm());
        realms.add(writerRealm());
        securityManager.setRealms(realms);

        ModularRealmAuthenticator modularRealmAuthenticator = customizedModularRealmAuthenticator();
        modularRealmAuthenticator.setRealms(realms);
        securityManager.setAuthenticator(modularRealmAuthenticator);

        ModularRealmAuthorizer modularRealmAuthorizer = customizedModularRealmAuthorizer();
        modularRealmAuthorizer.setRealms(realms);
        securityManager.setAuthorizer(modularRealmAuthorizer);

        return securityManager;
    }

    private Map<String, String> filterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

//        filterMap.put("/api/login/**", "anon");
//        filterMap.put("/api/books/all", "perms[user:all]");
//        filterMap.put("/api/books/all", "authc");
//        filterMap.put("/test2", "anon");
//        filterMap.put("/api/books/**", "anon");
//        filterMap.put("/**", "authcBasic");

        return filterMap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}