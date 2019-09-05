package com.daoli.sheng.tai.service;

import static com.daoli.constant.DBconstant.IN_VALID;
import static com.daoli.constant.DBconstant.VALID;

import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import com.daoli.sheng.tai.mapper.DepartmentExamEntityMapper;
import com.daoli.sheng.tai.mapper.ShengTaiExamEntityMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Map<Integer, Object> addBatchExam(List<ShengtaiExamVo> vos) {
        Map<Integer, Object> resMap = Maps.newHashMap();
        for (ShengtaiExamVo vo : vos) {
            ShengTaiExamEntity examEntity = new ShengTaiExamEntity();
            BeanUtils.copyProperties(vo, examEntity);
            examEntity.setValid(VALID);
            examEntity.setExamId(UUID.randomUUID().toString());
            examEntity.setStartTime(new Date());
            examEntity.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_DAI_FA_BU);
            examMapper.insertSelective(examEntity);
            resMap.put(examEntity.getId(), true);
        }
        return resMap;
    }


    /**
     * 1.删除现有的关系
     * 2.新增新的关系
     */
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
                deleteMap.put("delete", entity.getDepartmentId());
            }
        }

        for (String departmentId : departmentIds) {
            DepartmentExamEntity entity = DepartmentExamEntity.builder().createTime(new Date())
                    .examId(examId).departmentId(departmentId).valid(VALID).build();
            departmentExamEntityMapper.insertSelective(entity);
            deleteMap.put("add", departmentId);
        }
        resMap.put("addMap", addMap);
        resMap.put("deleteMap", deleteMap);
        return resMap;
    }

    public Map<Integer, Object> publishBatchExam(Integer[] examPIds) {
        Map<Integer, Object> resMap = Maps.newHashMap();
        for (Integer id : examPIds) {
            ShengTaiExamEntity examEntity = examMapper.selectByPrimaryKey(id);
            examEntity.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI);
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
            if (ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI.equals(examEntity.getExamStatus())) {
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
        return departmentExamEntityMapper.queryAssignedDepartmentsByExamId(examId);
    }

    public List<DepartmentVo> queryNotAssignedDepartsmensByExamPrimaryId(String examId) {
        return departmentExamEntityMapper.queryNotAssignedDepartmentsByExamId(examId);
    }

    public int updateExam(ShengtaiExamVo vo) {
        ShengTaiExamEntity examEntity = new ShengTaiExamEntity();
        // vo 不设置某个属性，属性就不会被拷贝到新对象，满足selective。
        BeanUtils.copyProperties(vo, examEntity);
        examEntity.setValid(VALID);
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


    public List<ShengtaiExamVo> selectExamByField(ShengtaiExamVo vo) {
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo, examEntry);
        List<ShengTaiExamEntity> res = examMapper.selectByField(examEntry);
        ArrayList<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i) {
            ShengtaiExamVo one_vo = new ShengtaiExamVo();
            BeanUtils.copyProperties(res.get(i), one_vo);
            vo_res.add(one_vo);
        }
        return vo_res;
    }

    public List<ShengtaiExamVo> selectExamByFieldFuzzy(ShengtaiExamVo vo) {
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo, examEntry);
        List<ShengTaiExamEntity> res = examMapper.selectByFieldFuzzy(examEntry);
        List<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i) {
            ShengtaiExamVo one_vo = new ShengtaiExamVo();
            BeanUtils.copyProperties(res.get(i), one_vo);
            vo_res.add(one_vo);
        }
        return vo_res;
    }

    // 获得一个exam 的树状结构 , 参数 arg_exam_vo 必须能够查询到一个 entry 。
    // 因此仅限制 exam_id 和 id 两个字段
    public List<ShengtaiExamVo> queryExamAllTreeByExamIdOrId(ShengtaiExamVo arg_exam_vo) {
        ArrayList<ShengtaiExamVo> arr_res_vo = new ArrayList<>();

        List<ShengtaiExamVo> res_by_exam_id = selectExamByField(arg_exam_vo);
        if (res_by_exam_id.size() > 1 || res_by_exam_id.size() < 0) {
            // 此处应该有异常抛出
            return null;
        }
        // 把 arg_exam_vo 的 detail 加入到 res
        arr_res_vo.add(res_by_exam_id.get(0));

        // 父亲
        if (res_by_exam_id.get(0).getParentExamId() != null
                && res_by_exam_id.get(0).getParentExamId() != 0) {
            ShengtaiExamVo parent_vo = new ShengtaiExamVo();
            parent_vo.setId(res_by_exam_id.get(0).getParentExamId());
            ShengtaiExamVo parent_vo_detail = queryExamDetailByExamBusinessId(parent_vo);
            arr_res_vo.add(parent_vo_detail);

            if (parent_vo.getParentExamId() != null && parent_vo.getParentExamId() != 0) {
                ShengtaiExamVo parent_vo_2 = new ShengtaiExamVo();
                parent_vo_2.setId(parent_vo.getParentExamId());
                arr_res_vo.add(queryExamDetailByExamBusinessId(parent_vo_2));
            }
        }
        // 子树
//        List<ShengtaiExamVo> sub_tree = queryExamSubTreeByExamIdOrId(res_by_exam_id.get(0));
//        if (sub_tree != null && sub_tree.size() > 0) {
//
//        }
        return arr_res_vo;
    }

    // 获得一个 exam 的子树结构
    public List<ShengtaiExamVo> queryExamSubTreeByExamIdOrId(Integer examPid) {


        List<ShengTaiExamEntity> zhibiaoList = Lists.newArrayList();

        for(ShengTaiExamEntity examEntity : zhibiaoList){
            zhibiaoList.addAll(Lists.newArrayList());
        }

        return null;

    }

    // 获得 一个 exam 的详细信息, 通过 exam id
    public ShengtaiExamVo queryExamDetailByExamBusinessId(ShengtaiExamVo arg_exam_vo) {
        List<ShengtaiExamVo> res_by_exam_id = selectExamByField(arg_exam_vo);
        if (res_by_exam_id.size() > 1 || res_by_exam_id.size() < 0) {
            // 此处应该有异常抛出
            return null;
        }
        return res_by_exam_id.get(0);
    }
}
