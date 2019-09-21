package com.daoli.sheng.tai.service;

import static com.daoli.constant.DBconstant.DEFAULT_SCORE;
import static com.daoli.constant.DBconstant.VALID;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.daoli.office.vo.sheng.tai.DepartmentScoreReportVo;
import com.daoli.office.vo.sheng.tai.ExamRecordAdditionVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.sheng.tai.entity.DepartmentEntity;
import com.daoli.sheng.tai.entity.ShengtaiExamRecordAdditionEntity;
import com.daoli.sheng.tai.entity.ShengtaiExamRecordEntity;
import com.daoli.sheng.tai.mapper.DepartmentEntityMapper;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordAdditionEntityMapper;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordEntityMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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
    private DepartmentEntityMapper departmentEntityMapper;

    @Autowired
    private ShengtaiExamRecordAdditionEntityMapper additionEntityMapper;

    private final static String[] EXAM_RECORD_IGNORE_PROPERTIES = new String[]{"modifyTime",
            "createTime", "recordStatus", "examScore", "startTime", "endTime"};


    @Transactional(rollbackFor = Exception.class)
    public void modifyRecord(ShengtaiExamRecordVo vo, List<Integer> additionId) {
        ShengtaiExamRecordEntity examRecordEntity = examRecordEntityMapper
                .selectByPrimaryKey(vo.getId());
        BeanUtils.copyProperties(vo, examRecordEntity, EXAM_RECORD_IGNORE_PROPERTIES);
        examRecordEntityMapper.updateByPrimaryKeySelective(examRecordEntity);

        additionEntityMapper.queryAdditionByExamRecordPid(vo.getId()).forEach(entity -> {
            entity.setExamRecordPid(-1);
            additionEntityMapper.updateByPrimaryKeySelective(entity);
        });
        assignAddition(examRecordEntity.getId(), additionId);
    }

    public void scoreExamRecord(Float score, Integer examRecordPid, String departmentId) {
        ShengtaiExamRecordEntity examRecordEntity = examRecordEntityMapper
                .selectByPrimaryKey(examRecordPid);
        examRecordEntity.setExamScore(score);
        List<ShengtaiExamRecordEntity> recordEntityList = examRecordEntityMapper
                .queryExamRecordByDepartmentIdAndDetailId(departmentId,
                        examRecordEntity.getExamDetailId());
        boolean noScore = true;
        for (ShengtaiExamRecordEntity entity : recordEntityList) {
            if (entity.getExamScore() != DEFAULT_SCORE && !entity.getId().equals(examRecordPid)) {
                noScore = false;
                break;
            }
        }
        if (noScore) {
            examRecordEntityMapper.updateByPrimaryKeySelective(examRecordEntity);
        }
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
        assignAddition(examRecordEntity.getId(), additionId);
    }


    private void assignAddition(Integer examRecordId,
            List<Integer> additionId) {
        if (CollectionUtils.isNotEmpty(additionId)) {
            for (Integer id : additionId) {
                ShengtaiExamRecordAdditionEntity entity = additionEntityMapper
                        .selectByPrimaryKey(id);
                entity.setExamRecordPid(examRecordId);
                additionEntityMapper.updateByPrimaryKeySelective(entity);
            }
        }
    }


    public ShengtaiExamRecordVo queryExamRecord(Integer id) {
        ShengtaiExamRecordEntity examRecordEntity = examRecordEntityMapper.selectByPrimaryKey(id);
        ShengtaiExamRecordVo vo = new ShengtaiExamRecordVo();
        BeanUtils.copyProperties(examRecordEntity, vo);
        return vo;
    }

    public List<ExamRecordAdditionVo> queryRecordAdditionList(Integer examRecordPid) {
        List<ShengtaiExamRecordAdditionEntity> queryList = additionEntityMapper
                .queryAdditionByExamRecordPid(examRecordPid);
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
     * 根据部门id查询考核记录
     */
    public List<ShengtaiExamRecordVo> queryExamRecordByDepartmentId(String departmentId,
            long startTime, long endTime) {
        List<ShengtaiExamRecordVo> voList = Lists.newArrayList();
        for (ShengtaiExamRecordEntity entity : examRecordEntityMapper
                .queryExamRecordByDepartmentId(departmentId)) {
            ShengtaiExamRecordVo vo = new ShengtaiExamRecordVo();
            BeanUtils.copyProperties(entity, vo);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 根据部门id,要点id查询考核记录
     */
    public List<ShengtaiExamRecordVo> queryExamRecordByDepartmentIdAndDetailId(String departmentId,
            String detailId) {
        List<ShengtaiExamRecordVo> voList = Lists.newArrayList();
        for (ShengtaiExamRecordEntity entity : examRecordEntityMapper
                .queryExamRecordByDepartmentIdAndDetailId(departmentId, detailId)) {
            ShengtaiExamRecordVo vo = new ShengtaiExamRecordVo();
            BeanUtils.copyProperties(entity, vo);
            voList.add(vo);
        }
        return voList;
    }


    /**
     * 根据部门id,要点id查询考核记录
     */
    public List<DepartmentScoreReportVo> queryDepartmentScoreReport(String departmentName,
            String departmentType, long startTime,
            long endTime) {
        DepartmentEntity entity = DepartmentEntity.builder().departmentName(departmentName)
                .departmentType(departmentType).build();
        List<DepartmentEntity> entityList = departmentEntityMapper.selectByFields(entity);
        List<DepartmentScoreReportVo> reportVoList = Lists.newArrayList();
        for (DepartmentEntity departmentEntity : entityList) {
            List<ShengtaiExamRecordEntity> recordEntityList = examRecordEntityMapper
                    .queryExamRecordByDepartmentIdWithTime(departmentEntity.getDepartmentId(),
                            new Date(startTime), new Date(endTime));
            int scoredRecord = 0;
            int totalRecord = 0;
            float totalScore = 0;
            Set<String> countDetail = Sets.newHashSet();
            for (ShengtaiExamRecordEntity recordEntity : recordEntityList) {
                if (!countDetail.contains(recordEntity.getExamDetailId())) {
                    totalRecord++;
                    if (recordEntity.getExamScore() > -1) {
                        scoredRecord++;
                        totalScore = totalScore + recordEntity.getExamScore();
                    }
                }
            }
            reportVoList
                    .add(DepartmentScoreReportVo.builder().departmentId(entity.getDepartmentId())
                            .departmentName(entity.getDepartmentName()).scoredRecord(scoredRecord)
                            .totalRecord(totalRecord).totalScore(totalScore).build());
        }

        return reportVoList;
    }


}
