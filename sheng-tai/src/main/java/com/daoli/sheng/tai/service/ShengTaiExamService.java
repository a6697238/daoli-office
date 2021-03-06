package com.daoli.sheng.tai.service;

import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.DEFAULT_SCORE;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.IN_VALID;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.VALID;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant.KAO_HE_FEI_LEI;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant.KAO_HE_YAO_DIAN;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant.KAO_HE_ZHI_BIAO;

import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.office.vo.sheng.tai.ExamRecordUploadDetailVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant;
import com.daoli.sheng.tai.entity.DepartmentEntity;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import com.daoli.sheng.tai.entity.ShengtaiExamRecordEntity;
import com.daoli.sheng.tai.mapper.DepartmentEntityMapper;
import com.daoli.sheng.tai.mapper.DepartmentExamEntityMapper;
import com.daoli.sheng.tai.mapper.ShengTaiExamEntityMapper;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordEntityMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by wanglining on 2019/8/22.
 */
@Service
@Slf4j
public class ShengTaiExamService {

    @Autowired
    private ShengTaiExamEntityMapper examMapper;


    @Autowired
    private DepartmentExamEntityMapper departmentExamEntityMapper;

    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;

    @Autowired
    private ShengtaiExamRecordEntityMapper recordEntityMapper;


    private final static String[] EXAM_IGNORE_PROPERTIES = new String[]{"modifyTime",
            "createTime"};

    /**
     * 批量添加 考试信息
     */
    public Map<String, Object> addBatchExam(List<ShengtaiExamVo> vos) {
        Map<String, Object> resMap = Maps.newHashMap();
        for (ShengtaiExamVo vo : vos) {
            ShengTaiExamEntity examEntity = new ShengTaiExamEntity();
            BeanUtils.copyProperties(vo, examEntity);
            examEntity.setValid(VALID);
            examEntity.setExamId(UUID.randomUUID().toString());
            examEntity.setCreateTime(new Date());
            examEntity.setModifyTime(new Date());
            examEntity.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_DAI_FA_BU);

            int res = examMapper.insertSelective(examEntity);
            if (res != 0) {
                if (KAO_HE_YAO_DIAN.equals(vo.getExamType())) {
                    examMapper.updateZhiBiaoScoreByYaoDianParentId(vo.getParentExamId());
                    examMapper.updateFenLeiScoreByYaoDianParentId(vo.getParentExamId());
                }
                resMap.put(examEntity.getExamName(), true);
            } else {
                resMap.put(examEntity.getExamName(), false);
            }
        }
        return resMap;
    }


    /**
     * 1.删除现有的关系
     * 2.新增新的关系
     */
    @Transactional(rollbackFor = Exception.class)
    public void assignBatchExam(String examId,
            List<String> departmentIds) {
        ShengTaiExamEntity examEntity = examMapper.queryByExamId(examId);
        if (KAO_HE_FEI_LEI.equals(examEntity.getExamType())) {
            assignFenleiExam(examId, departmentIds);
        } else if (KAO_HE_ZHI_BIAO.equals(examEntity.getExamType())) {
            assignZhibiaoExam(examId, departmentIds);
        } else if (KAO_HE_YAO_DIAN.equals(examEntity.getExamType())) {
            assignYaodianExam(examId, departmentIds);
        }
    }

    private void assignFenleiExam(String examId, List<String> departmentIds) {
        ShengTaiExamEntity fenleiEntity = examMapper.queryByExamId(examId);
        assignExam(examId, departmentIds);
        List<ShengTaiExamEntity> zhibiaoList = examMapper
                .queryExamsByParentExamId(fenleiEntity.getId());
        for (ShengTaiExamEntity zhibiaoEntity : zhibiaoList) {
            assignExam(zhibiaoEntity.getExamId(), departmentIds);
            List<ShengTaiExamEntity> yaodianList = examMapper
                    .queryExamsByParentExamId(zhibiaoEntity.getId());
            for (ShengTaiExamEntity yaodianEntity : yaodianList) {
                assignExam(yaodianEntity.getExamId(), departmentIds);
            }
        }

    }

    private void assignZhibiaoExam(String examId, List<String> departmentIds) {
        ShengTaiExamEntity zhibiaoEntity = examMapper.queryByExamId(examId);
        assignExam(zhibiaoEntity.getExamId(), departmentIds);
        List<ShengTaiExamEntity> yaodianList = examMapper
                .queryExamsByParentExamId(zhibiaoEntity.getId());
        for (ShengTaiExamEntity yaodianEntity : yaodianList) {
            assignExam(yaodianEntity.getExamId(), departmentIds);
        }
    }


    private void assignYaodianExam(String examId, List<String> departmentIds) {
        ShengTaiExamEntity yaodianEntity = examMapper.queryByExamId(examId);
        assignExam(yaodianEntity.getExamId(), departmentIds);
    }

    private void assignExam(String examId, List<String> departmentIds) {
        ShengTaiExamEntity examEntity = examMapper.queryByExamId(examId);
        departmentExamEntityMapper.deleteDepartmentExamByExamId(examEntity.getExamId());
        for (String departmentId : departmentIds) {
            departmentExamEntityMapper.insertSelective(
                    DepartmentExamEntity.builder().examId(examId).departmentId(departmentId)
                            .createTime(new Date()).valid(VALID).build());
        }
    }

    public Map<Integer, Object> publishBatchExam(Integer[] examPIds) {
        Map<Integer, Object> resMap = Maps.newHashMap();
        for (Integer id : examPIds) {
            ShengTaiExamEntity examEntity = examMapper.selectByPrimaryKey(id);
            examEntity.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_YI_FA_BU);
            examEntity.setModifyTime(new Date());
            if (examMapper.updateByPrimaryKeySelective(examEntity) > 0) {
                examMapper.updateZhiBiaoStatusByFenLeiPid(examEntity);
                examMapper.updateYaoDianStatusByFenLeiPid(examEntity);
                resMap.put(examEntity.getId(), true);
            }
        }
        return resMap;
    }


    public Map<Integer, Object> rollbackPublishBatchExam(Integer[] examPIds) {
        Map<Integer, Object> resMap = Maps.newHashMap();
        for (Integer id : examPIds) {
            ShengTaiExamEntity examEntity = examMapper.selectByPrimaryKey(id);
            if (ShengTaiExamStatusConstant.KAO_HE_YI_FA_BU.equals(examEntity.getExamStatus())) {
                examEntity.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_DAI_FA_BU);
                examEntity.setModifyTime(new Date());
                if (examMapper.updateByPrimaryKeySelective(examEntity) > 0) {
                    examMapper.updateZhiBiaoStatusByFenLeiPid(examEntity);
                    examMapper.updateYaoDianStatusByFenLeiPid(examEntity);
                    resMap.put(examEntity.getId(), true);
                }
            }
        }
        return resMap;
    }

    public Map<Integer, Object> deleteBatchExam(Integer[] examPIds) {
        Map<Integer, Object> resMap = Maps.newHashMap();
        for (Integer id : examPIds) {
            ShengTaiExamEntity examEntity = examMapper.selectByPrimaryKey(id);
            examEntity.setValid(IN_VALID);
            if (examMapper.updateByPrimaryKeySelective(examEntity) > 0) {
                if (ShengTaiExamTypeConstant.KAO_HE_YAO_DIAN.equals(examEntity.getExamType())) {
                    examMapper.updateZhiBiaoScoreByYaoDianParentId(examEntity.getParentExamId());
                    examMapper.updateFenLeiScoreByYaoDianParentId(examEntity.getParentExamId());
                }
                if (ShengTaiExamTypeConstant.KAO_HE_FEI_LEI.equals(examEntity.getExamType())) {
                    examMapper.delectYaoDianByFenLeiPid(examEntity.getId());
                    examMapper.delectZhiBiaoByFenLeiPid(examEntity.getId());

                }
                if (ShengTaiExamTypeConstant.KAO_HE_ZHI_BIAO.equals(examEntity.getExamType())) {
                    examMapper.delectYaoDianByZhiBiaoPid(examEntity.getId());
                }
                resMap.put(examEntity.getId(), true);
            }
        }
        return resMap;
    }


    @Transactional(rollbackFor = Exception.class)
    public void modifyExam(ShengtaiExamVo vo) {
        if (KAO_HE_FEI_LEI.equals(vo.getExamType())) {
            updateFenleiNode(vo);
        } else if (KAO_HE_ZHI_BIAO.equals(vo.getExamType())) {
            updateZhiBiaoNode(vo);
        } else if (KAO_HE_YAO_DIAN.equals(vo.getExamType())) {
            updateYaodianNode(vo);
        }
    }

    private void updateFenleiNode(ShengtaiExamVo modifyVo) {
        ShengTaiExamEntity fenleiEntity = examMapper.selectByPrimaryKey(modifyVo.getId());
        BeanUtils.copyProperties(modifyVo, fenleiEntity, EXAM_IGNORE_PROPERTIES);
        List<ShengTaiExamEntity> zhibiaoList = examMapper
                .queryExamsByParentExamId(modifyVo.getId());
        for (ShengTaiExamEntity zhibiaoEntity : zhibiaoList) {
            zhibiaoEntity.setStartTime(modifyVo.getStartTime());
            zhibiaoEntity.setEndTime(modifyVo.getEndTime());
            List<ShengTaiExamEntity> yaodianList = examMapper
                    .queryExamsByParentExamId(zhibiaoEntity.getId());
            for (ShengTaiExamEntity yaodianEntity : yaodianList) {
                yaodianEntity.setStartTime(modifyVo.getStartTime());
                yaodianEntity.setEndTime(modifyVo.getEndTime());
                examMapper.updateByPrimaryKeySelective(yaodianEntity);
            }
            examMapper.updateByPrimaryKeySelective(zhibiaoEntity);
        }
        examMapper.updateByPrimaryKeySelective(fenleiEntity);
    }

    private void updateZhiBiaoNode(ShengtaiExamVo modifyVo) {
        ShengTaiExamEntity zhibiaoEntity = examMapper.selectByPrimaryKey(modifyVo.getId());
        BeanUtils.copyProperties(modifyVo, zhibiaoEntity, EXAM_IGNORE_PROPERTIES);
        List<ShengTaiExamEntity> yaodianList = examMapper
                .queryExamsByParentExamId(modifyVo.getId());
        for (ShengTaiExamEntity yaodianEntity : yaodianList) {
            yaodianEntity.setStartTime(modifyVo.getStartTime());
            yaodianEntity.setEndTime(modifyVo.getEndTime());
            examMapper.updateByPrimaryKeySelective(yaodianEntity);
        }
        examMapper.updateByPrimaryKeySelective(zhibiaoEntity);
    }


    private void updateYaodianNode(ShengtaiExamVo modifyVo) {
        ShengTaiExamEntity yaodianEntity = examMapper.selectByPrimaryKey(modifyVo.getId());
        float oldYaoDianScore = yaodianEntity.getExamScore();
        BeanUtils.copyProperties(modifyVo, yaodianEntity, EXAM_IGNORE_PROPERTIES);
        examMapper.updateByPrimaryKeySelective(yaodianEntity);

        ShengTaiExamEntity zhibiaoEntity = examMapper
                .selectByPrimaryKey(modifyVo.getParentExamId());
        zhibiaoEntity.setExamScore(
                zhibiaoEntity.getExamScore() - oldYaoDianScore + modifyVo.getExamScore());
        examMapper.updateByPrimaryKeySelective(zhibiaoEntity);

        ShengTaiExamEntity fenlei = examMapper.selectByPrimaryKey(zhibiaoEntity.getParentExamId());
        fenlei.setExamScore(fenlei.getExamScore() - oldYaoDianScore + modifyVo.getExamScore());
        examMapper.updateByPrimaryKeySelective(fenlei);

    }

    public List<DepartmentVo> queryAssignedDepartmentsByExamId(String examId) {
        List<DepartmentEntity> listDepartmentEntity = departmentExamEntityMapper
                .queryAssignedDepartmentsByExamId(examId);
        List<DepartmentVo> listDepartmentVo = new ArrayList<>();
        for (DepartmentEntity entity : listDepartmentEntity) {
            DepartmentVo oneDepartmentVo = new DepartmentVo();
            BeanUtils.copyProperties(entity, oneDepartmentVo);
            listDepartmentVo.add(oneDepartmentVo);
        }
        return listDepartmentVo;
    }

    public List<DepartmentVo> queryNotAssignedDepartsmensByExamPrimaryId(String examId) {
        List<DepartmentEntity> listDepartmentEntity = departmentExamEntityMapper
                .queryNotAssignedDepartmentsByExamId(examId);
        List<DepartmentVo> listDepartmentVo = new ArrayList<>();
        for (DepartmentEntity entity : listDepartmentEntity) {
            DepartmentVo oneDepartmentVo = new DepartmentVo();
            BeanUtils.copyProperties(entity, oneDepartmentVo);
            listDepartmentVo.add(oneDepartmentVo);
        }
        return listDepartmentVo;
    }


    // 此 api 会直接删除数据库中的记录，不建议使用
    public int deleteExamByExamId(ShengtaiExamVo vo) {
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo, examEntry);
        examEntry.setValid(VALID);
        //return examMapper.deleteByPrimaryKey(vo.getId());
        return examMapper.updateByPrimaryKeySelective(examEntry);
    }


    public int startExam(Integer examPid) {
        ShengTaiExamEntity examEntity = examMapper.selectByPrimaryKey(examPid);
        examEntity.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_JIN_XING_ZHONG);
        return examMapper.updateByPrimaryKeySelective(examEntity);
    }

    public int endExam(Integer examPid) {
        ShengTaiExamEntity examEntity = examMapper.selectByPrimaryKey(examPid);
        examEntity.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_ZHENG_CHANG_JIE_SHU);
        return examMapper.updateByPrimaryKeySelective(examEntity);
    }


    public ShengtaiExamVo queryExamByPId(Integer examPid) {
        ShengTaiExamEntity res = examMapper.selectByPrimaryKey(examPid);
        ShengtaiExamVo vo = ShengtaiExamVo.builder().build();
        BeanUtils.copyProperties(res, vo);
        return vo;
    }

    public List<ExamRecordUploadDetailVo> queryJinXingZhongExamByDepartmentId(String departmentId) {
        List<ShengTaiExamEntity> shengTaiExamEntityList = examMapper
                .queryJinXingZhongExamByDepartmentId(departmentId);

        Map<Integer, ShengTaiExamEntity> zhibiaoMap = Maps.newHashMap();
        List<ExamRecordUploadDetailVo> resList = Lists.newArrayList();
        shengTaiExamEntityList.forEach(entity -> {
            if (entity.getExamType().equals(KAO_HE_ZHI_BIAO)) {
                zhibiaoMap.put(entity.getId(), entity);
            }
        });

        shengTaiExamEntityList.forEach(entity -> {
            if (entity.getExamType().equals(KAO_HE_YAO_DIAN)) {
                ExamRecordUploadDetailVo vo = ExamRecordUploadDetailVo.builder().build();

                List<ShengtaiExamRecordEntity> recordEntityList = recordEntityMapper
                        .queryExamRecordByDepartmentIdAndDetailId(
                                departmentId, entity.getExamId());
                BeanUtils.copyProperties(entity, vo);
                vo.setIndexExamId(zhibiaoMap.get(vo.getParentExamId()).getExamId());
                vo.setIndexName(zhibiaoMap.get(vo.getParentExamId()).getExamName());
                vo.setIndexDesc(zhibiaoMap.get(vo.getParentExamId()).getExamDesc());
                vo.setAssignedNum(recordEntityList.size());
                vo.setExamScore(0F);
                for (ShengtaiExamRecordEntity examRecordEntity : recordEntityList) {
                    if (examRecordEntity.getExamScore() > DEFAULT_SCORE) {
                        vo.setExamScore(examRecordEntity.getExamScore());
                        break;
                    }
                }
                vo.setFullScore(entity.getExamScore());
                resList.add(vo);
            }
        });
        return resList;
    }

    public ShengtaiExamVo queryExamByExamId(String examId) {
        ShengTaiExamEntity res = examMapper.queryByExamId(examId);
        ShengtaiExamVo vo = ShengtaiExamVo.builder().build();
        BeanUtils.copyProperties(res, vo);
        return vo;
    }


    public List<ShengtaiExamVo> queryExamsByCondition(ShengtaiExamVo vo) {

        List<ShengTaiExamEntity> allExam = examMapper.queryExamsByFuzzyCondition(
                ShengtaiExamVo.builder().startTime(vo.getStartTime()).endTime(vo.getEndTime())
                        .build());

        List<DepartmentEntity> allDepartment = departmentEntityMapper
                .queryDepartmentByFields(new DepartmentEntity());
        List<ShengtaiExamVo> resList = Lists.newArrayList();

        List<ShengTaiExamEntity> queryExam = examMapper.queryExamsByFuzzyCondition(vo);

        Set<Integer> queryExamSet = Sets.newHashSet();
        queryExam.forEach(entity -> queryExamSet.add(entity.getId()));
        allExam.forEach(entity -> {
            ShengtaiExamVo resVo = new ShengtaiExamVo();
            BeanUtils.copyProperties(entity, resVo);

            Set<String> assignSet = Sets.newHashSet();
            List<DepartmentVo> assignedDepartment = Lists.newArrayList();
            Set<String> assignedDepartmentName = Sets.newHashSet();
            List<DepartmentVo> unsignedDepartment = Lists.newArrayList();
            departmentExamEntityMapper
                    .queryAssignedDepartmentsByExamId(resVo.getExamId())
                    .forEach(departmentEntity -> assignSet
                            .add(departmentEntity.getDepartmentId()));
            allDepartment.forEach(departmentEntity -> {
                if (assignSet.contains(departmentEntity.getDepartmentId())) {
                    assignedDepartment.add(DepartmentVo.builder()
                            .departmentId(departmentEntity.getDepartmentId())
                            .departmentName(departmentEntity.getDepartmentName()).build());
                    assignedDepartmentName.add(departmentEntity.getDepartmentName());
                } else {
                    unsignedDepartment.add(DepartmentVo.builder()
                            .departmentId(departmentEntity.getDepartmentId())
                            .departmentName(departmentEntity.getDepartmentName()).build());
                }
            });

            if (queryExamSet.contains(entity.getId()) && (allExam.size() != queryExam.size())) {
                resVo.setSearched(true);
            }

            if (assignedDepartmentName.contains(vo.getDepartmentName())) {
                resVo.setSearched(true);
            }

            resVo.setAssignedDepartment(assignedDepartment);
            resVo.setUnsignedDepartment(unsignedDepartment);

            resList.add(resVo);
        });
        return resList;
    }
}
