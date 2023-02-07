package com.baizhi.mall.security.component.dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 获取权限perms
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    private Map<String,ConfigAttribute> configAttributeMap;

    /**希望在当前对象创建后，执行1次加载配置的操作避免重复操作*/
    @PostConstruct
    public void loadDataSource(){
       this.configAttributeMap =  this.dynamicSecurityService.loadDataSource();
    }

    /**形参表示安全对象 FilterInvocation对象  获取本次请求所需要的权限列表*/
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取本次所需要的权限列表
        //1 获取到所有的权限配置信息  从数据库中读取
        if(this.configAttributeMap == null){
            this.configAttributeMap = this.dynamicSecurityService.loadDataSource();
        }
        List<ConfigAttribute> configAttributeList = new ArrayList<>();
        if(CollectionUtils.isEmpty(configAttributeMap)){
            return configAttributeList;
        }

        //2 根据本次请求的地址 从全部的权限配置信息中获取所需
        //获取本次请求的地址
        String url = ((FilterInvocation) object).getRequestUrl();
        String uriPath = null;
        try {
            URI uri = new URI(url);
            uriPath = uri.getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

        //遍历configAttributeMap获取所需
        PathMatcher pathMatcher = new AntPathMatcher();
        for (Map.Entry<String, ConfigAttribute> entry : configAttributeMap.entrySet()) {
            //配置的url
            String pattern = entry.getKey();
            if(pathMatcher.match(pattern,uriPath)){
                //  对应权限
                ConfigAttribute configAttribute = entry.getValue();
                configAttributeList.add(configAttribute);
            }
        }

        return configAttributeList;
    }

    /**返回所有的权限信息  通常不需要实现*/
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    /** 判断 安全对象的类型是否被当前的MetadataSource支持 */
    public boolean supports(Class<?> clazz) { //FilterInvocation类型
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
