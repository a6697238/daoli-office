package com.daoli.sheng.tai.service;

import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.sheng.tai.entity.ShengtaiExamRecordEntity;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordEntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AUTO-GENERATED: houlu @ 2019/8/20 下午9:04
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ExamRecordService {

    @Autowired
    private ShengtaiExamRecordEntityMapper examRecordEntityMapper;


    public void uploadRecord(ShengtaiExamRecordVo vo){
        ShengtaiExamRecordEntity examRecordEntity = new ShengtaiExamRecordEntity();
        BeanUtils.copyProperties(vo,examRecordEntity);
        examRecordEntityMapper.insertSelective(examRecordEntity);
    }

}
