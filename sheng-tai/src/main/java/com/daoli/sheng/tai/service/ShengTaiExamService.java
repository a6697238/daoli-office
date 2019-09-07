package com.daoli.sheng.tai.service;

import static com.daoli.constant.DBconstant.IN_VALID;
import static com.daoli.constant.DBconstant.VALID;

import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.sheng.tai.entity.DepartmentEntity;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import com.daoli.sheng.tai.mapper.DepartmentExamEntityMapper;
import com.daoli.sheng.tai.mapper.ShengTaiExamEntityMapper;
import com.google.common.collect.Maps;
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

    // 考试状态单独类 done
    // 只有在 考核未开始的状况可以删除 ，需要在deleteExam 验证一下。 done
    // exam 在未开始的时候，可以修改的东西 done
    // 开始 、 停止 接口 done
    // 时间戳 <= >= done
    // query done

    // 模糊查询 done
    // exma 删除，要用事务，删除子节点
    // 部门的考核返回 树状结构，同时列出上传记录 json 怎么表示树状结构
    // 考核 树状结构呈现，列出答题单位及上传情况

    //public final static String kaoHeWeiKaiShi = "考核未开始";
    //public final static  String kaoHeZhongTuTingZhi = "考核中途停止";
    //public final static String kaoHeJinXingZhong = "考核进行中";
    //public final static String kaoHeZhengChangJieShu = "考核正常结束";
    //public final static String kaoHeYiChangJieShu = "考核异常结束";

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
            examEntity.setStartTime(new Date());
            examEntity.setEndTime(new Date());
//            String pattern = "yyyy-MM-dd HH:mm:ss";
//            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//            try {
//                Date startDate = sdf.parse("2999-01-01 08:00:00");
//                examEntity.setStartTime(startDate);
//                Date endtDate = sdf.parse("2999-12-30 08:00:00");
//                examEntity.setEndTime(endtDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            examMapper.insertSelective(examEntity);
            resMap.put(examEntity.getExamName(), true);
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
                deleteMap.put( entity.getDepartmentId(), true);
            }else {
                deleteMap.put( entity.getDepartmentId(), false);
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
        ShengTaiExamEntity filledExamEntity  = examMapper.queryByExamId(examId);
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
            if (examMapper.updateByPrimaryKeySelective(examEntity) > 0) {
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
                if (examMapper.updateByPrimaryKeySelective(examEntity) > 0) {
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
                resMap.put(examEntity.getId(), true);
            }
        }
        return resMap;
    }

    public List<DepartmentVo> queryAssignedDepartmentsByExamId(String examId) {
        List<DepartmentEntity> listDepartmentEntity =  departmentExamEntityMapper.queryAssignedDepartmentsByExamId(examId);
        List<DepartmentVo> listDepartmentVo = new ArrayList<>();
        for(DepartmentEntity entity:listDepartmentEntity){
            DepartmentVo oneDepartmentVo = new DepartmentVo();
            BeanUtils.copyProperties(entity,oneDepartmentVo);
            listDepartmentVo.add(oneDepartmentVo);
        }
        return listDepartmentVo;
    }

    public List<DepartmentVo> queryNotAssignedDepartsmensByExamPrimaryId(String examId) {
        List<DepartmentEntity> listDepartmentEntity =  departmentExamEntityMapper.queryNotAssignedDepartmentsByExamId(examId);
        List<DepartmentVo> listDepartmentVo = new ArrayList<>();
        for(DepartmentEntity entity:listDepartmentEntity){
            DepartmentVo oneDepartmentVo = new DepartmentVo();
            BeanUtils.copyProperties(entity,oneDepartmentVo);
            listDepartmentVo.add(oneDepartmentVo);
        }
        return listDepartmentVo;
    }

    public int updateExam(ShengtaiExamVo vo) {
        ShengTaiExamEntity examEntity = new ShengTaiExamEntity();
        // vo 不设置某个属性，属性就不会被拷贝到新对象，满足selective。
        BeanUtils.copyProperties(vo, examEntity);
        examEntity.setValid(VALID);
        examEntity.setModifyTime(new Date());
        return examMapper.updateByPrimaryKeySelective(examEntity);
    }

    public int deleteExam(ShengtaiExamVo vo) {
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo, examEntry);

        examEntry.setValid(VALID);
        return examMapper.updateByPrimaryKeySelective(examEntry);
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

    public ShengtaiExamVo queryExamByExamId(String examId) {
        ShengTaiExamEntity res = examMapper.queryByExamId(examId);
        ShengtaiExamVo vo = ShengtaiExamVo.builder().build();
        BeanUtils.copyProperties(res, vo);
        return vo;
    }


    public List<ShengtaiExamVo> queryExamsByCondition(ShengtaiExamVo vo) {
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo, examEntry);
        List<ShengTaiExamEntity> res = examMapper.queryExamsByCondition(examEntry);

        ArrayList<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i) {
            ShengtaiExamVo one_vo = new ShengtaiExamVo();
            BeanUtils.copyProperties(res.get(i), one_vo);
            vo_res.add(one_vo);
        }
        return vo_res;
    }

    public List<ShengtaiExamVo> queryExamsByFuzzyCondition(ShengtaiExamVo vo) {
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo, examEntry);
        List<ShengTaiExamEntity> res = examMapper.queryExamsByFuzzyCondition(examEntry);

        List<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i) {
            ShengtaiExamVo one_vo = new ShengtaiExamVo();
            BeanUtils.copyProperties(res.get(i), one_vo);
            vo_res.add(one_vo);
        }
        return vo_res;
    }

    public List<ShengtaiExamVo> queryAllExams() {
        List<ShengTaiExamEntity> res = examMapper.queryAllExams();

        List<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i) {
            ShengtaiExamVo one_vo = new ShengtaiExamVo();
            BeanUtils.copyProperties(res.get(i), one_vo);
            vo_res.add(one_vo);
        }
        return vo_res;
    }
}
