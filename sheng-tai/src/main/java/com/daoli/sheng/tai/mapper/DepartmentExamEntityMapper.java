package com.daoli.sheng.tai.mapper;

import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.sheng.tai.entity.DepartmentEntity;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public interface DepartmentExamEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentExamEntity record);

    int insertSelective(DepartmentExamEntity record);

    DepartmentExamEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentExamEntity record);

    int updateByPrimaryKey(DepartmentExamEntity record);

    List<DepartmentExamEntity> queryDepartmentExamByExamId(@Param("examId")String examId);

    List<DepartmentExamEntity> queryDepartmentExamByDepartmentId(@Param("departmentIds")String departmentIds);

    ArrayList<DepartmentExamEntity> selectByField(DepartmentExamEntity record);

    List<DepartmentEntity> queryAssignedDepartmentsByExamId(@Param("examId")String examId);

    List<DepartmentEntity> queryNotAssignedDepartmentsByExamId(@Param("examId")String examId);

    void deleteDepartmentExamByExamId(@Param("examId")String examId);

}