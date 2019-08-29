package com.daoli.sheng.tai.service;

import static com.daoli.constant.DBconstant.VALID;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.daoli.office.vo.sheng.tai.ExamRecordAdditionVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.sheng.tai.entity.ShengtaiExamRecordAdditionEntity;
import com.daoli.sheng.tai.entity.ShengtaiExamRecordEntity;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordAdditionEntityMapper;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordEntityMapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ShengtaiExamRecordAdditionEntityMapper additionEntityMapper;


    @Transactional(rollbackFor = Exception.class)
    public void modifyRecord(ShengtaiExamRecordVo vo, List<Integer> additionId) {
        ShengtaiExamRecordEntity examRecordEntity = examRecordEntityMapper
                .selectByPrimaryKey(vo.getId());
        BeanUtils.copyProperties(vo, examRecordEntity);
        examRecordEntityMapper.updateByPrimaryKeySelective(examRecordEntity);
        List<ShengtaiExamRecordAdditionEntity> additionEntityList = additionEntityMapper
                .queryAdditionByRecordId(vo.getExamRecordId());
        for (ShengtaiExamRecordAdditionEntity entity : additionEntityList) {
            additionEntityMapper.deleteByPrimaryKey(entity.getId());
        }
        updateAddition(vo, additionId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void uploadRecord(ShengtaiExamRecordVo vo, List<Integer> additionId) {
        ShengtaiExamRecordEntity examRecordEntity = new ShengtaiExamRecordEntity();
        vo.setExamRecordId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(vo, examRecordEntity);
        examRecordEntity.setModifyTime(new Date());
        examRecordEntity.setCreateTime(new Date());
        examRecordEntity.setValid(VALID);
        examRecordEntityMapper.insertSelective(examRecordEntity);
        updateAddition(vo, additionId);
    }


    private void updateAddition(ShengtaiExamRecordVo vo, List<Integer> additionId) {
        if (CollectionUtils.isNotEmpty(additionId)) {
            for (Integer id : additionId) {
                ShengtaiExamRecordAdditionEntity entity = additionEntityMapper
                        .selectByPrimaryKey(id);
                entity.setExamRecordId(vo.getExamRecordId());
                additionEntityMapper.updateByPrimaryKeySelective(entity);
            }
        }
    }

    public ShengtaiExamRecordVo queryExamRecord(Integer id) {
        ShengtaiExamRecordEntity examRecordEntity = examRecordEntityMapper.selectByPrimaryKey(id);
        ShengtaiExamRecordVo vo = new ShengtaiExamRecordVo();
        BeanUtils.copyProperties(vo, examRecordEntity);
        return vo;
    }

    public List<ExamRecordAdditionVo> queryRecordAdditionList(String examRecordId) {
        List<ShengtaiExamRecordAdditionEntity> queryList = additionEntityMapper
                .queryAdditionByRecordId(examRecordId);
        List<ExamRecordAdditionVo> resList = Lists.newArrayList();
        for (ShengtaiExamRecordAdditionEntity entity : queryList) {
            ExamRecordAdditionVo vo = new ExamRecordAdditionVo();
            BeanUtils.copyProperties(entity, vo);
            resList.add(vo);
        }
        return resList;
    }

    public ExamRecordAdditionVo uploadAddition(ExamRecordAdditionVo vo) {
        ShengtaiExamRecordAdditionEntity entity = new ShengtaiExamRecordAdditionEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setValid(VALID);
        additionEntityMapper.insertSelective(entity);
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    /**
     *
     * @param id
     * @return
     */
    public ExamRecordAdditionVo queryAdditionById(Integer id) {
        ShengtaiExamRecordAdditionEntity entity = additionEntityMapper.selectByPrimaryKey(id);
        ExamRecordAdditionVo vo = new ExamRecordAdditionVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }


    /**
     * 根据条件
     * @param vo
     * @return
     */
    public ArrayList<ShengtaiExamRecordVo> queryRecordByCondition(ShengtaiExamRecordVo vo){

        ShengtaiExamRecordEntity examRecordEntity = new ShengtaiExamRecordEntity();
        BeanUtils.copyProperties(vo,examRecordEntity);
        ArrayList<ShengtaiExamRecordEntity>  res_entry =  examRecordEntityMapper.selectByExamIndexIdAndDepartId(examRecordEntity);

        ArrayList<ShengtaiExamRecordVo> res = new ArrayList<>();
        for(ShengtaiExamRecordEntity one_entry : res_entry){
            ShengtaiExamRecordVo one_vo = new ShengtaiExamRecordVo();
            BeanUtils.copyProperties(one_entry,one_vo);
            res.add(one_vo);
        }
        return res ;
    }



}
