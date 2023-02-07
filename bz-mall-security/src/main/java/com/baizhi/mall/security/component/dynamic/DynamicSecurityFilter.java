package com.baizhi.mall.security.component.dynamic;

import com.baizhi.mall.security.component.IgnoreUrlsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 定义  白名单直接放行和针对跨域产生的OPTIONS请求放行
 */
public class DynamicSecurityFilter extends FilterSecurityInterceptor {

    @Override
    @Autowired
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    @Autowired
    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
        super.setSecurityMetadataSource(newSource);
    }

    @Autowired
    private IgnoreUrlsProperties ignoreUrlsProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /**定制化  白名单直接放行 跨域的问题
         * PathMatcher 路径匹配工具
         */
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String url : ignoreUrlsProperties.getUrls()) {
            // url白名单路径和请求路径（第二个参数）作对比
            if (pathMatcher.match(url, ((HttpServletRequest) request).getRequestURI())) {
                chain.doFilter(request, response);
                return;
            }
        }

        //针对跨域产生的OPTIONS请求，直接放行
        if (((HttpServletRequest) request).getMethod().equals(HttpMethod.OPTIONS)) {
            chain.doFilter(request,response);
            return;
        }


        //核心流程直接继承父类，不做修改
        super.doFilter(request, response, chain);
    }
}
