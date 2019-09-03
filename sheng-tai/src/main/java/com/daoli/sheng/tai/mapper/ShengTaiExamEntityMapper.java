package com.daoli.sheng.tai.mapper;

import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import java.util.List;
public interface ShengTaiExamEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShengTaiExamEntity record);

    int insertSelective(ShengTaiExamEntity record);

    ShengTaiExamEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShengTaiExamEntity record);

    int updateByPrimaryKey(ShengTaiExamEntity record);

    List<ShengTaiExamEntity> selectByField(ShengTaiExamEntity record);
    List<ShengTaiExamEntity> selectByFieldFuzzy(ShengTaiExamEntity record);
}