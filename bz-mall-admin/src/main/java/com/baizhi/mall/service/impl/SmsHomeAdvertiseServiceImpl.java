package com.baizhi.mall.service.impl;


import com.baizhi.mall.entity.SmsHomeAdvertise;
import com.baizhi.mall.mapper.SmsHomeAdvertiseMapper;
import com.baizhi.mall.service.SmsHomeAdvertiseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* @author 15241
* @description 针对表【sms_home_advertise(首页轮播广告表)】的数据库操作Service实现
* @createDate 2022-12-28 10:51:17
*/
@Service
@Transactional
public class SmsHomeAdvertiseServiceImpl extends ServiceImpl<SmsHomeAdvertiseMapper, SmsHomeAdvertise>
    implements SmsHomeAdvertiseService {

    @Resource
    private SmsHomeAdvertiseMapper mapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageInfo<SmsHomeAdvertise> likeQuery(String name, Integer type, String endTime, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<SmsHomeAdvertise> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(name)){
            wrapper.like("name",name);
        }
        if (type!=null){
            wrapper.eq("type",type);
        }
        if (StringUtils.hasText(endTime)){
            String strDate = endTime + " 00:00:00";
            String endDate = endTime + " 23:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date start = sdf.parse(strDate);
                Date end = sdf.parse(endDate);
                wrapper.ge("end_time",start).le("end_time",end);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        List<SmsHomeAdvertise> list = mapper.selectList(wrapper);
        return new PageInfo<>(list, mapper.selectCount(wrapper));
    }
}




