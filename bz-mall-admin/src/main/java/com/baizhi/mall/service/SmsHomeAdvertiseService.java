package com.baizhi.mall.service;

import com.baizhi.mall.entity.SmsHomeAdvertise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * @author 15241
 * @description 针对表【sms_home_advertise(首页轮播广告表)】的数据库操作Service
 * @createDate 2022-12-28 10:51:17
 */
public interface SmsHomeAdvertiseService extends IService<SmsHomeAdvertise> {

    PageInfo<SmsHomeAdvertise> likeQuery(String name, Integer type, String endTime, Integer pageSize, Integer pageNum);
}
