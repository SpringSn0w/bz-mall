package com.baizhi.mall.security.component.dynamic;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

public interface DynamicSecurityService {
    /**从数据库中加载资源路径和所需权限信息   key表示uri  value表示相应uri需要的权限*/
    Map<String, ConfigAttribute> loadDataSource(); //sys_menu   url perms
}
