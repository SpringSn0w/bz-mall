package com.baizhi.mall.security.config;

import com.baizhi.mall.security.component.IgnoreUrlsProperties;
import com.baizhi.mall.security.component.JwtAuthenticationTokenFilter;
import com.baizhi.mall.security.component.dynamic.DynamicSecurityFilter;
import com.baizhi.mall.security.component.dynamic.DynamicSecurityMetadataSource;
import com.baizhi.mall.security.component.dynamic.DynamicSecurityService;
import com.baizhi.mall.security.component.dynamic.DynamicSecurityVoter;
import com.baizhi.mall.security.util.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class CommonSecurityConfig {
    @Bean
    public IgnoreUrlsProperties ignoreUrlsProperties() {
        return new IgnoreUrlsProperties();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    /**只有当 工厂有DynamicSecurityService实现，才配置当前 Bean*/
    @ConditionalOnBean(DynamicSecurityService.class)
    public AccessDecisionVoter dynamicSecurityVoter() {
        return new DynamicSecurityVoter();
    }

    @Bean
    @ConditionalOnBean(DynamicSecurityService.class)
    public AccessDecisionManager accessDecisionManager() {
        return new AffirmativeBased(Arrays.asList(dynamicSecurityVoter()));
    }

    @Bean
    @ConditionalOnBean(DynamicSecurityService.class)
    public DynamicSecurityMetadataSource securityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    @Bean
    @ConditionalOnBean(DynamicSecurityService.class)
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }
}
