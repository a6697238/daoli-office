package com.daoli.sheng.tai.mapper;

import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public interface ShengTaiExamEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShengTaiExamEntity record);

    int insertSelective(ShengTaiExamEntity record);

    ShengTaiExamEntity selectByPrimaryKey(Integer id);

    ArrayList<ShengTaiExamEntity> selectByField(ShengTaiExamEntity record);
    ArrayList<ShengTaiExamEntity> selectByFieldFuzzy(ShengTaiExamEntity record);

    int updateByPrimaryKeySelective(ShengTaiExamEntity record);

    int updateByPrimaryKey(ShengTaiExamEntity record);
}