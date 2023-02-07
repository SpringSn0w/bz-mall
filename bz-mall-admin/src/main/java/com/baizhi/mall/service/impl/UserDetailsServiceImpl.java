package com.baizhi.mall.service.impl;

import com.baizhi.mall.entity.UmsAdmin;
import com.baizhi.mall.entity.UmsAdminExample;
import com.baizhi.mall.entity.UmsResource;
import com.baizhi.mall.mapper.UmsAdminMapper;
import com.baizhi.mall.mapper.UmsResourceDao;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UmsAdminMapper umsAdminMapper;
    @Resource
    private UmsResourceDao resourceDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过umsAdminMapper查询
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(umsAdmins) || umsAdmins.size() > 1) {
            throw new UsernameNotFoundException("账号或密码错误");
        }
        UmsAdmin admin = umsAdmins.get(0);

        List<UmsResource> resourceList = resourceDao.getResourceList(admin.getId());
        // resourceList --> String[]
        String[] perms = new String[resourceList.size()];
        for (int i = 0; i < resourceList.size(); i++) {
            UmsResource umsResource = resourceList.get(i);
            perms[i] = umsResource.getId() + ":" + umsResource.getName();

        }

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                // 0禁用 1启用对应数据库表的status
                .disabled(admin.getStatus().equals(0))
                .authorities(perms).build();
    }
}
