package com.baizhi.mall.mapper;

import com.baizhi.mall.entity.CmsSubject;
import com.baizhi.mall.entity.CmsSubjectExample;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsSubjectMapper extends BaseMapper<CmsSubject> {
    long countByExample(CmsSubjectExample example);

    int deleteByExample(CmsSubjectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsSubject row);

    int insertSelective(CmsSubject row);

    List<CmsSubject> selectByExampleWithBLOBs(CmsSubjectExample example);

    List<CmsSubject> selectByExample(CmsSubjectExample example);

    CmsSubject selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") CmsSubject row, @Param("example") CmsSubjectExample example);

    int updateByExampleWithBLOBs(@Param("row") CmsSubject row, @Param("example") CmsSubjectExample example);

    int updateByExample(@Param("row") CmsSubject row, @Param("example") CmsSubjectExample example);

    int updateByPrimaryKeySelective(CmsSubject row);

    int updateByPrimaryKeyWithBLOBs(CmsSubject row);

    int updateByPrimaryKey(CmsSubject row);
}