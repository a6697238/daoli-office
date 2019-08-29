package com.daoli.sheng.tai.mapper;

import com.daoli.sheng.tai.entity.ShengtaiExamRecordEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public interface ShengtaiExamRecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShengtaiExamRecordEntity record);

    int insertSelective(ShengtaiExamRecordEntity record);

    ShengtaiExamRecordEntity selectByPrimaryKey(Integer id);
    ArrayList<ShengtaiExamRecordEntity> selectByExamIndexIdAndDepartId(ShengtaiExamRecordEntity record);

    int updateByPrimaryKeySelective(ShengtaiExamRecordEntity record);

    int updateByPrimaryKey(ShengtaiExamRecordEntity record);
}