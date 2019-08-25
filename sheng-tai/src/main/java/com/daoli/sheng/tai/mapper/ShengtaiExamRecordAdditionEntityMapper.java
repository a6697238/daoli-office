package com.daoli.sheng.tai.mapper;

import java.util.List;

import com.daoli.sheng.tai.entity.ShengtaiExamRecordAdditionEntity;
import org.springframework.stereotype.Component;

@Component
public interface ShengtaiExamRecordAdditionEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShengtaiExamRecordAdditionEntity record);

    int insertSelective(ShengtaiExamRecordAdditionEntity record);

    ShengtaiExamRecordAdditionEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShengtaiExamRecordAdditionEntity record);

    int updateByPrimaryKey(ShengtaiExamRecordAdditionEntity record);

    List<ShengtaiExamRecordAdditionEntity> queryAdditionByRecordId(String recordId);

}