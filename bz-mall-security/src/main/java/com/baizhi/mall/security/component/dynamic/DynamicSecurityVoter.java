package com.baizhi.mall.security.component.dynamic;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class DynamicSecurityVoter implements AccessDecisionVoter<Object>{

    @Override// 判断当前Voter是否支持指定类型的ConfigAttribute    string--->SecurityConfig
    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute() != null;
    }

    @Override //判断当前Voter是否支持特定的安全对象类型  FilterInvocation.class
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     *
     * @param authentication 用于认证后的认证信息，包含着用户拥有的权限
     * @param object 安全对象FilterInvocation对象
     * @param attributes 本次所需要的权限列表
     * @return  0表示弃权 1 通过 -1 未通过
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        //如果传递的所需要权限列表为空,直接弃权
        if (CollectionUtils.isEmpty(attributes)) {
            return AccessDecisionVoter.ACCESS_ABSTAIN;
        }

        //1 从认证信息中获取用户拥有的权限列表
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //2 判断所需要的权限是否是用户拥有的  所需要的权限中任意一个只要是用户拥有，则通过
        for (ConfigAttribute needAttribute : attributes) {
            for (GrantedAuthority authority : authorities) {

                // needAttribute.getAttribute() 获取本次请求所需权限的字符串形式
                // authority.getAuthority() 获取用户拥有的权限的字符串形式
                if (needAttribute.getAttribute().equalsIgnoreCase(authority.getAuthority())) {
                    return AccessDecisionVoter.ACCESS_GRANTED;
                }
            }
        }

        return AccessDecisionVoter.ACCESS_DENIED;
    }
}
