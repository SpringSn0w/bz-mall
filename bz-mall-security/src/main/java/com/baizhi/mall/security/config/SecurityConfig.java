package com.baizhi.mall.security.config;

import com.baizhi.mall.security.component.IgnoreUrlsProperties;
import com.baizhi.mall.security.component.JwtAuthenticationTokenFilter;
import com.baizhi.mall.security.component.RestAuthenticationEntryPoint;
import com.baizhi.mall.security.component.RestfulAccessDeniedHandler;
import com.baizhi.mall.security.component.dynamic.DynamicSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Autowired
    private IgnoreUrlsProperties ignoreUrlsProperties;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private DynamicSecurityFilter dynamicSecurityFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 全后端分离关闭csrf
        http.csrf().disable();
        // 针对两种异常处理
        http.exceptionHandling()
                // 处理未登录
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                // 鉴权
                .accessDeniedHandler(new RestfulAccessDeniedHandler());

        // 配置鉴权要求
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        // 白名单
        for (String url : ignoreUrlsProperties.getUrls()) {
            // permitAll()不需要鉴权直接放行
            registry.antMatchers(url).permitAll();
        }
        // 允许跨域请求OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        registry.anyRequest().authenticated();

        // 禁用Session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 添加filter到filterChain中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
    }
}
