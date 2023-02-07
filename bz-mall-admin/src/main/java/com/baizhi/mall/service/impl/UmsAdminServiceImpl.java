package com.baizhi.mall.service.impl;

import com.baizhi.mall.dto.UmsAdminParam;
import com.baizhi.mall.entity.UmsAdmin;
import com.baizhi.mall.entity.UmsAdminExample;
import com.baizhi.mall.mapper.UmsAdminMapper;
import com.baizhi.mall.security.util.JwtTokenUtil;
import com.baizhi.mall.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    @Resource
    private UmsAdminMapper adminMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //保存在上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // 生成jwt
        String token = jwtTokenUtil.generateToken(authenticate);

        return token;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UmsAdmin register(UmsAdminParam adminParam) {
        //dto->entity
        UmsAdmin admin = new UmsAdmin();
        BeanUtils.copyProperties(adminParam,admin);

        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(admin.getUsername());
        List<UmsAdmin> admins = adminMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(admins)){
            return null;
        }

        admin.setCreateTime(new Date());
        admin.setStatus(1);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminMapper.insertSelective(admin);
        return admin;
    }
}
