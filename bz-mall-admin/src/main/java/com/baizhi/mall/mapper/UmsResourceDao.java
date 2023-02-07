package com.baizhi.mall.mapper;

import com.baizhi.mall.entity.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsResourceDao {
    /**
     * 获取用户所有可访问资源
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);
}
