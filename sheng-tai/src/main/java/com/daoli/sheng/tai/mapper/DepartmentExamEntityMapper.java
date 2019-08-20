package com.daoli.sheng.tai.mapper;

import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import org.springframework.stereotype.Component;

@Component
public interface DepartmentExamEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentExamEntity record);

    int insertSelective(DepartmentExamEntity record);

    DepartmentExamEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentExamEntity record);

    int updateByPrimaryKey(DepartmentExamEntity record);
}