package com.daoli.sheng.tai.mapper;

import java.util.List;

import com.daoli.sheng.tai.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface DepartmentEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentEntity record);

    int insertSelective(DepartmentEntity record);

    DepartmentEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentEntity record);

    int updateByPrimaryKey(DepartmentEntity record);

    List<DepartmentEntity> queryDepartmentByFields(DepartmentEntity record);

    DepartmentEntity queryDepartmentByDepartmentId(@Param("departmentId")String departmentId);



}