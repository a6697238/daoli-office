package com.daoli.sheng.tai.service;

import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.DEFAULT_SCORE;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.IN_VALID;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.VALID;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant.KAO_HE_FEI_LEI;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant.KAO_HE_YAO_DIAN;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant.KAO_HE_ZHI_BIAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

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
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
                .queryExamRecordByDepartmentIdWithTime(departmentId, new Date(startTime),
                        new Date(endTime))) {
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
        List<DepartmentEntity> departmentEntityList = departmentEntityMapper
                .queryDepartmentByFields(entity);
        List<DepartmentScoreReportVo> reportVoList = Lists.newArrayList();

        for (DepartmentEntity departmentEntity : departmentEntityList) {
            int scoredCount = 0;
            int uploadCount = 0;
            float scoredCompleteRate = 0.0F;

            float realScore = 0.0F;
            float uploadTargetScore = 0.0F;
            float uploadTargetScoreRate = 0.0F;

            float examTargetScore = 0.0F;
            float examTargetScoreRate = 0.0F;

            int uploadExamCount = 0;
            int examTargetCount = 0;
            float uploadTargetCompleteRate = 0.0F;

            List<ShengtaiExamRecordEntity> recordEntityList = examRecordEntityMapper
                    .queryExamRecordByDepartmentIdWithTime(departmentEntity.getDepartmentId(),
                            new Date(startTime), new Date(endTime));

            List<ShengTaiExamEntity> examEntityList = shengTaiExamEntityMapper
                    .queryExamByDepartmentIdWithTime(departmentEntity.getDepartmentId(),
                            new Date(startTime), new Date(endTime), KAO_HE_YAO_DIAN);

            Map<String, ShengTaiExamEntity> examEntityMap = Maps.newHashMap();
            //1.求出 考核应上传记录数，应上传得分
            for (ShengTaiExamEntity examEntity : examEntityList) {
                examTargetCount = examTargetCount + 1;
                examTargetScore = examTargetScore + examEntity.getExamScore();
                examEntityMap.put(examEntity.getExamId(), examEntity);
            }

            //2.求出 上传记录数得分
            Set<String> examSet = Sets.newHashSet();
            for (ShengtaiExamRecordEntity examRecordEntity : recordEntityList) {
                if (!examSet.contains(examRecordEntity.getExamDetailId())) {
                    examSet.add(examRecordEntity.getExamDetailId());
                    if (examRecordEntity.getExamScore() > DEFAULT_SCORE) {
                        scoredCount = scoredCount + 1;
                        realScore = realScore + examRecordEntity.getExamScore();
                    }
                    uploadCount = uploadCount + 1;
                    uploadExamCount  = uploadExamCount + 1;
                    uploadTargetScore = uploadTargetScore + examEntityMap
                            .get(examRecordEntity.getExamDetailId()).getExamScore();
                }
            }

            scoredCompleteRate = uploadCount > 0 ? scoredCount / uploadCount * 100 : 0;
            uploadTargetScoreRate = uploadTargetScore > 0 ? realScore / uploadTargetScore * 100 : 0;
            examTargetScoreRate = examTargetScore > 0 ? realScore / examTargetScore * 100 : 0;
            uploadTargetCompleteRate =
                    examTargetCount > 0 ? uploadExamCount / examTargetCount * 100 : 0;

            reportVoList.add(DepartmentScoreReportVo.builder()
                    .departmentId(departmentEntity.getDepartmentId())
                    .departmentPid(departmentEntity.getId())
                    .departmentName(departmentEntity.getDepartmentName())
                    .scoredCount(scoredCount)
                    .uploadCount(uploadCount)
                    .scoredCompleteRate(scoredCompleteRate)
                    .realScore(realScore)
                    .uploadTargetScore(uploadTargetScore)
                    .uploadTargetScoreRate(uploadTargetScoreRate)
                    .examTargetScore(examTargetScore)
                    .examTargetScoreRate(examTargetScoreRate)
                    .uploadExamCount(uploadExamCount)
                    .examTargetCount(examTargetCount)
                    .uploadTargetCompleteRate(uploadTargetCompleteRate).build());

        }

        return reportVoList;
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteExamRecord(Integer examRecordPid) {
        ShengtaiExamRecordEntity examRecordEntity = examRecordEntityMapper
                .selectByPrimaryKey(examRecordPid);
        examRecordEntity.setValid(IN_VALID);
        examRecordEntityMapper.updateByPrimaryKeySelective(examRecordEntity);

        List<ShengtaiExamRecordEntity> recordEntityList = examRecordEntityMapper
                .queryExamRecordByDepartmentIdAndDetailId(
                        examRecordEntity.getDepartmentId(), examRecordEntity.getExamDetailId());
        for (ShengtaiExamRecordEntity recordEntity : recordEntityList) {
            recordEntity.setAssignedNum(recordEntityList.size());
            examRecordEntityMapper.updateByPrimaryKeySelective(examRecordEntity);
        }

    }


}
