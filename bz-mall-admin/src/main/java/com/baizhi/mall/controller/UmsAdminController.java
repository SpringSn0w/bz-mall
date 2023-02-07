package com.baizhi.mall.controller;

import com.baizhi.mall.commons.api.annotation.ResultBody;
import com.baizhi.mall.commons.api.constant.ResponseStatusEnum;
import com.baizhi.mall.commons.api.vo.Result;
import com.baizhi.mall.dto.UmsAdminLoginParam;
import com.baizhi.mall.dto.UmsAdminParam;
import com.baizhi.mall.entity.UmsAdmin;
import com.baizhi.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 登录
 */
@RestController
@ResultBody
@RequestMapping("/admin")
public class UmsAdminController {

    @Resource
    private UmsAdminService adminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping("/login")
    public Result login(@RequestBody UmsAdminLoginParam loginParam) {
        try {
            String token = adminService.login(loginParam.getUsername(), loginParam.getPassword());

            HashMap map = new HashMap();
            map.put("token",token);
            map.put("tokenHead",tokenHead);
            return Result.ok(map);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.status(ResponseStatusEnum.UNAUTHORIZED).build("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody UmsAdminParam adminParam){
        UmsAdmin admin = adminService.register(adminParam);
        if (admin==null){
            return Result.error("注册失败,用户名被占用");
        }
        return Result.ok(admin);
    }
}
