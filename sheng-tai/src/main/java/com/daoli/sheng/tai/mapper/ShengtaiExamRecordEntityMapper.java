package com.daoli.sheng.tai.mapper;

import com.daoli.sheng.tai.entity.ShengtaiExamRecordEntity;
import org.springframework.stereotype.Component;

@Component
public interface ShengtaiExamRecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShengtaiExamRecordEntity record);

    int insertSelective(ShengtaiExamRecordEntity record);

    ShengtaiExamRecordEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShengtaiExamRecordEntity record);

    int updateByPrimaryKey(ShengtaiExamRecordEntity record);
}