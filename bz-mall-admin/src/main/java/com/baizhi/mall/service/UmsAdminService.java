package com.baizhi.mall.service;

import com.baizhi.mall.dto.UmsAdminParam;
import com.baizhi.mall.entity.UmsAdmin;

public interface UmsAdminService {
    String login(String username,String password);

    UmsAdmin register(UmsAdminParam umsAdminParam);
}
