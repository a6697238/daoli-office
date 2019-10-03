package com.daoli.sheng.tai.service;

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
import com.daoli.sheng.tai.mapper.DepartmentExamEntityMapper;
import com.daoli.sheng.tai.mapper.ShengTaiExamEntityMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
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
    public Map<String, Object> assignBatchExam(String examId,
            List<String> departmentIds) {
        Map<String, Object> resMap = Maps.newHashMap();
        Map<String, Object> deleteMap = Maps.newHashMap();
        Map<String, Object> addMap = Maps.newHashMap();

        List<DepartmentExamEntity> examEntityList = departmentExamEntityMapper
                .queryDepartmentExamByExamId(examId);
        for (DepartmentExamEntity entity : examEntityList) {
            entity.setValid(IN_VALID);
            if (departmentExamEntityMapper.updateByPrimaryKeySelective(entity) > 0) {
                deleteMap.put(entity.getDepartmentId(), true);
            } else {
                deleteMap.put(entity.getDepartmentId(), false);
            }
        }

        for (String departmentId : departmentIds) {
            DepartmentExamEntity entity = DepartmentExamEntity.builder().createTime(new Date())
                    .examId(examId).departmentId(departmentId).valid(VALID).build();
            int rt = departmentExamEntityMapper.insertSelective(entity);
            if (rt != 0) {
                addMap.put(departmentId, true);
            } else {
                addMap.put(departmentId, false);
            }
        }
        ShengTaiExamEntity filledExamEntity = examMapper.queryByExamId(examId);
        ShengTaiExamEntity argExamEntity = new ShengTaiExamEntity();
        argExamEntity.setId(filledExamEntity.getId());
        argExamEntity.setAssignedNum(departmentIds.size());
        examMapper.updateByPrimaryKeySelective(argExamEntity);

        //filledExamEntity.setAssignedNum(departmentIds.size());
        //examMapper.updateByPrimaryKeySelective(filledExamEntity);

        resMap.put("addMap", addMap);
        resMap.put("deleteMap", deleteMap);
        return resMap;
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
                    .queryExamsByParentExamId(modifyVo.getId());
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
            zhibiaoEntity.setStartTime(modifyVo.getStartTime());
            zhibiaoEntity.setEndTime(modifyVo.getEndTime());
            examMapper.updateByPrimaryKeySelective(yaodianEntity);
        }
        examMapper.updateByPrimaryKeySelective(zhibiaoEntity);
    }


    private void updateYaodianNode(ShengtaiExamVo modifyVo) {
        ShengTaiExamEntity yaodianEntity = examMapper.selectByPrimaryKey(modifyVo.getId());
        BeanUtils.copyProperties(modifyVo, yaodianEntity, EXAM_IGNORE_PROPERTIES);
        examMapper.updateByPrimaryKeySelective(yaodianEntity);
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

//    public int updateExam(ShengtaiExamVo vo) {
//        ShengTaiExamEntity examEntity = new ShengTaiExamEntity();
//        // vo 不设置某个属性，属性就不会被拷贝到新对象，满足selective。
//        BeanUtils.copyProperties(vo, examEntity);
//        examEntity.setValid(VALID);
//        examEntity.setModifyTime(new Date());
//
//        int res = examMapper.updateByPrimaryKeySelective(examEntity);
//
//        ShengTaiExamEntity detailEntry = examMapper.selectByPrimaryKey(vo.getId());
//        if (KAO_HE_YAO_DIAN.equals(detailEntry.getExamType())) {
//            examMapper.updateZhiBiaoScoreByYaoDianParentId(detailEntry.getParentExamId());
//            examMapper.updateFenLeiScoreByYaoDianParentId(detailEntry.getParentExamId());
//        }
//        return res;
//    }


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
                BeanUtils.copyProperties(entity, vo);
                vo.setIndexExamId(zhibiaoMap.get(vo.getParentExamId()).getExamId());
                vo.setIndexName(zhibiaoMap.get(vo.getParentExamId()).getExamName());
                vo.setIndexDesc(zhibiaoMap.get(vo.getParentExamId()).getExamDesc());
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
        List<ShengTaiExamEntity> queryExam = examMapper.queryExamsByFuzzyCondition(vo);
        Set<Integer> queryExamSet = Sets.newHashSet();
        queryExam.forEach(entity -> queryExamSet.add(entity.getId()));
        List<ShengtaiExamVo> resList = Lists.newArrayList();
        allExam.forEach(entity -> {
            ShengtaiExamVo resVo = new ShengtaiExamVo();
            BeanUtils.copyProperties(entity, resVo);
            if (queryExamSet.contains(entity.getId())) {
                resVo.setSearched(true);
            }
            resList.add(resVo);
        });
        return resList;
    }


    public List<ShengtaiExamVo> queryAllExams() {
        List<ShengTaiExamEntity> res = examMapper.queryAllExams();

        List<ShengtaiExamVo> voRes = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i) {
            ShengtaiExamVo oneVo = new ShengtaiExamVo();
            BeanUtils.copyProperties(res.get(i), oneVo);
            voRes.add(oneVo);
        }
        return voRes;
    }
}
