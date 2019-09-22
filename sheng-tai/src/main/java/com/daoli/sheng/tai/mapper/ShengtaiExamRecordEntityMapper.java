package com.daoli.sheng.tai.mapper;

import java.util.Date;
import java.util.List;

import com.daoli.sheng.tai.entity.ShengtaiExamRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ShengtaiExamRecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShengtaiExamRecordEntity record);

    int insertSelective(ShengtaiExamRecordEntity record);

    ShengtaiExamRecordEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShengtaiExamRecordEntity record);

    int updateByPrimaryKey(ShengtaiExamRecordEntity record);

    List<ShengtaiExamRecordEntity> queryByExamIdAndDepartmentId(@Param("examId") String examId,
            @Param("departmentId") String departmentId);


    List<ShengtaiExamRecordEntity> queryExamRecordByDepartmentId(
            @Param("departmentId") String departmentId);

    List<ShengtaiExamRecordEntity> queryExamRecordByDepartmentIdWithTime(
            @Param("departmentId") String departmentId, @Param("startTime") Long startTime,
            @Param("endTime") Long endTime);

    List<ShengtaiExamRecordEntity> queryExamRecordByDepartmentIdAndDetailId(
            @Param("departmentId") String departmentId, @Param("detailId") String detailId);
}