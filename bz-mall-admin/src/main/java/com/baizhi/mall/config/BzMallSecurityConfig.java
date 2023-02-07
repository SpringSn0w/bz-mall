package com.baizhi.mall.config;

import com.baizhi.mall.entity.UmsResource;
import com.baizhi.mall.entity.UmsResourceExample;
import com.baizhi.mall.mapper.UmsResourceMapper;
import com.baizhi.mall.security.component.dynamic.DynamicSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BzMallSecurityConfig {

    @Resource
    private UmsResourceMapper resourceMapper;

    @Bean
    public DynamicSecurityService dynamicSecurityService(){
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                // 查询所有的url和权限关系
                // select * from ums_resource
                List<UmsResource> resources = resourceMapper.selectByExample(new UmsResourceExample());
                // url -> 权限(id+":"+name)
                HashMap<String, ConfigAttribute> configAttributeMap = new HashMap<>();
                for (UmsResource resource : resources) {
                    String value = resource.getId() + ":" + resource.getName();
                    configAttributeMap.put(resource.getUrl(), new SecurityConfig(value));
                }
                return configAttributeMap;
            }
        };
    }
}
