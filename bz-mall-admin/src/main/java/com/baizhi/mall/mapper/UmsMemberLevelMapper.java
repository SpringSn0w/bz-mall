package com.baizhi.mall.mapper;

import com.baizhi.mall.entity.UmsMemberLevel;
import com.baizhi.mall.entity.UmsMemberLevelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsMemberLevelMapper {
    long countByExample(UmsMemberLevelExample example);

    int deleteByExample(UmsMemberLevelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsMemberLevel row);

    int insertSelective(UmsMemberLevel row);

    List<UmsMemberLevel> selectByExample(UmsMemberLevelExample example);

    UmsMemberLevel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") UmsMemberLevel row, @Param("example") UmsMemberLevelExample example);

    int updateByExample(@Param("row") UmsMemberLevel row, @Param("example") UmsMemberLevelExample example);

    int updateByPrimaryKeySelective(UmsMemberLevel row);

    int updateByPrimaryKey(UmsMemberLevel row);
}