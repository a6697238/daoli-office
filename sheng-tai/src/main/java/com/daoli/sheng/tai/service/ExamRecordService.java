package com.daoli.sheng.tai.service;

import static com.daoli.constant.ShengTaiDBconstant.DEFAULT_SCORE;
import static com.daoli.constant.ShengTaiDBconstant.VALID;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant.KAO_HE_ZHI_BIAO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.daoli.office.vo.sheng.tai.DepartmentScoreReportInfoVo;
import com.daoli.office.vo.sheng.tai.DepartmentScoreReportVo;
import com.daoli.office.vo.sheng.tai.ExamRecordAdditionVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.sheng.tai.entity.DepartmentEntity;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import com.daoli.sheng.tai.entity.ShengtaiExamRecordAdditionEntity;
import com.daoli.sheng.tai.entity.ShengtaiExamRecordEntity;
import com.daoli.sheng.tai.mapper.DepartmentEntityMapper;
import com.daoli.sheng.tai.mapper.ShengTaiExamEntityMapper;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordAdditionEntityMapper;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordEntityMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
    private DepartmentEntityMapper departmentEntityMapper;

    @Autowired
    private ShengTaiExamEntityMapper shengTaiExamEntityMapper;

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

    @Transactional(rollbackFor = Exception.class)
    public void scoreExamRecord(Float score, String detailId, String departmentId) {
        List<ShengtaiExamRecordEntity> recordEntityList = examRecordEntityMapper
                .queryExamRecordByDepartmentIdAndDetailId(departmentId,
                        detailId);
        for (ShengtaiExamRecordEntity entity : recordEntityList) {
            entity.setExamScore(score);
            examRecordEntityMapper.updateByPrimaryKeySelective(entity);
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
        List<DepartmentEntity> entityList = departmentEntityMapper.queryDepartmentByFields(entity);
        List<DepartmentScoreReportVo> reportVoList = Lists.newArrayList();

        for (DepartmentEntity departmentEntity : entityList) {
            List<ShengtaiExamRecordEntity> recordEntityList = examRecordEntityMapper
                    .queryExamRecordByDepartmentIdWithTime(departmentEntity.getDepartmentId(),
                            startTime, endTime);
            List<ShengTaiExamEntity> zhibiaoList = shengTaiExamEntityMapper
                    .queryExamByDepartmentIdWithTime(departmentEntity.getDepartmentId(),
                            startTime, endTime, KAO_HE_ZHI_BIAO);

            Map<String, DepartmentScoreReportInfoVo> info = Maps.newHashMap();
            int scoredCount = 0;
            int totalCount = 0;
            float score = 0;
            float totalScore = 0;
            Set<String> indexSet = Sets.newHashSet();
            for (ShengTaiExamEntity examEntity : zhibiaoList) {
                DepartmentScoreReportInfoVo vo = Optional
                        .ofNullable(info.get(examEntity.getExamName()))
                        .orElse(DepartmentScoreReportInfoVo.builder()
                                .indexName(examEntity.getExamName())
                                .indexScore(0)
                                .indexScoredCount(0)
                                .indexTotalCount(0)
                                .indexTotalScore(examEntity.getExamScore()).build());
                for (ShengtaiExamRecordEntity recordEntity : recordEntityList) {
                    if (recordEntity.getExamIndexId().equals(examEntity.getExamId())) {
                        if (recordEntity.getExamScore() > DEFAULT_SCORE) {
                            vo.setIndexScore(recordEntity.getExamScore());
                            vo.setIndexScoredCount(vo.getIndexScoredCount() + 1);
                            scoredCount++;
                            if (!indexSet.contains(recordEntity.getExamDetailId())) {
                                score = score + recordEntity.getExamScore();
                                indexSet.add(recordEntity.getExamDetailId());
                            }
                        }
                        totalCount++;
                        vo.setIndexTotalCount(vo.getIndexTotalCount() + 1);
                    }
                }
                totalScore = totalScore + examEntity.getExamScore();
                info.put(vo.getIndexName(), vo);
            }
            float completeRate = totalCount > 0 ? scoredCount / totalCount : 0;
            reportVoList.add(DepartmentScoreReportVo.builder()
                    .departmentName(departmentEntity.getDepartmentName()).scoredCount(scoredCount)
                    .totalCount(totalCount).score(score).totalScore(totalScore)
                    .completeRate(completeRate * 100).build());

        }

        return reportVoList;
    }


}
