package com.daoli.sheng.tai.service;

import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import com.daoli.sheng.tai.mapper.ShengTaiExamEntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by wanglining on 2019/8/22.
 */
@Service
public class ShengTaiExamService {
    @Autowired
    private ShengTaiExamEntityMapper examMapper;

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

    public int insertExam(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        examEntry.setValid(new Byte((byte)1));
        examEntry.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_DAI_FA_BU);
        return examMapper.insertSelective(examEntry);
    }

    public int deleteExam(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);

        examEntry.setValid(new Byte((byte) 0));
       return examMapper.updateByPrimaryKeySelective(examEntry);
    }

    // 此 api 会直接删除数据库中的记录，不建议使用
    public int deleteExamByExamId(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        examEntry.setValid(new Byte((byte)0));
        //return examMapper.deleteByPrimaryKey(vo.getId());
        return  examMapper.updateByPrimaryKeySelective(examEntry);
    }

    public int updateExam(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        // vo 不设置某个属性，属性就不会被拷贝到新对象，满足selective。
        BeanUtils.copyProperties(vo,examEntry);
        return examMapper.updateByPrimaryKeySelective(examEntry);
    }

    public int startExam(ShengtaiExamVo arg_vo){
        arg_vo.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_JIN_XING_ZHONG);
        return updateExam(arg_vo);
    }

    public  int endExam(ShengtaiExamVo arg_vo){
        arg_vo.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_ZHENG_CHANG_JIE_SHU);
        return updateExam(arg_vo);
    }

    public ShengtaiExamVo selectExamById(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ShengtaiExamVo res_vo = new ShengtaiExamVo();
        BeanUtils.copyProperties(examMapper.selectByPrimaryKey(vo.getId()),res_vo);
        return res_vo;
    }


    public ShengtaiExamVo queryExamByExamId(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        examEntry.setExamId(vo.getExamId());
        List<ShengTaiExamEntity> res = examMapper.selectByField(examEntry);
        ShengtaiExamVo vo_res = new ShengtaiExamVo();
        if (res.size()>1 || res.size() <= 0) {
            return null;
        }
        BeanUtils.copyProperties( res.get(0), vo_res);
        return vo_res;
    }

    public List<ShengtaiExamVo> selectExamByField(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        List<ShengTaiExamEntity> res = examMapper.selectByField(examEntry);
        ArrayList<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i){
            ShengtaiExamVo one_vo = new ShengtaiExamVo();
            BeanUtils.copyProperties(res.get(i), one_vo);
            vo_res.add(one_vo);
        }
        return vo_res;
    }

    public List<ShengtaiExamVo> selectExamByFieldFuzzy(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        List<ShengTaiExamEntity> res = examMapper.selectByFieldFuzzy(examEntry);
        List<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i){
                ShengtaiExamVo one_vo = new ShengtaiExamVo();
                BeanUtils.copyProperties(res.get(i), one_vo);
                vo_res.add(one_vo);
        }
        return vo_res;
    }

    // 获得一个exam 的树状结构 , 参数 arg_exam_vo 必须能够查询到一个 entry 。
    // 因此仅限制 exam_id 和 id 两个字段
    public List<ShengtaiExamVo> query_exam_all_tree_by_exam_id_or_id(ShengtaiExamVo arg_exam_vo){
        ArrayList<ShengtaiExamVo> arr_res_vo = new ArrayList<>();

        List<ShengtaiExamVo>  res_by_exam_id =  selectExamByField(arg_exam_vo);
        if (res_by_exam_id.size() > 1 || res_by_exam_id.size() < 0){
            // 此处应该有异常抛出
            return null;
        }
        // 把 arg_exam_vo 的 detail 加入到 res
        arr_res_vo.add(res_by_exam_id.get(0));

        // 父亲
        if (res_by_exam_id.get(0).getParentExamId()!= null && res_by_exam_id.get(0).getParentExamId() != 0 )
        {
            ShengtaiExamVo parent_vo = new ShengtaiExamVo();
            parent_vo.setId( res_by_exam_id.get(0).getParentExamId());
            ShengtaiExamVo parent_vo_detail = query_exam_detail_by_exam_id(parent_vo);
            arr_res_vo.add(parent_vo_detail);

            if(parent_vo.getParentExamId() != null && parent_vo.getParentExamId() != 0){
                ShengtaiExamVo parent_vo_2 = new ShengtaiExamVo();
                parent_vo_2.setId( parent_vo.getParentExamId());
                arr_res_vo.add(query_exam_detail_by_exam_id(parent_vo_2));
            }
        }
        // 子树
        List<ShengtaiExamVo> sub_tree = query_exam_sub_tree_by_exam_id_or_id(res_by_exam_id.get(0));
        if(sub_tree != null && sub_tree.size()>0){

        }
       return  arr_res_vo;
    }

    // 获得一个 exam 的子树结构
    public  List<ShengtaiExamVo> query_exam_sub_tree_by_exam_id_or_id(ShengtaiExamVo arg_exam_vo){

        List<ShengtaiExamVo> res = null;

        if(arg_exam_vo.getExamId() != null){
            ShengtaiExamVo query_exam_vo = new ShengtaiExamVo();
            query_exam_vo.setParentExamId(arg_exam_vo.getId());
            res = selectExamByField(query_exam_vo);
        } else if(arg_exam_vo.getId() != null){
            ShengtaiExamVo arg_exam_vo_detail = queryExamByExamId(arg_exam_vo);
            ShengtaiExamVo query_exam_vo = new ShengtaiExamVo();
            query_exam_vo.setParentExamId(arg_exam_vo_detail.getId());
            res = selectExamByField(query_exam_vo);
        }
        if (res != null)
          for(ShengtaiExamVo iter_vo : res){
            ShengtaiExamVo query_exam_vo = new ShengtaiExamVo();
            query_exam_vo.setParentExamId(iter_vo.getId());
            res.addAll(selectExamByField(query_exam_vo));
          }
        return res;
    }

    // 获得 一个 exam 的详细信息, 通过 exam id
    public  ShengtaiExamVo query_exam_detail_by_exam_id(ShengtaiExamVo arg_exam_vo){
        List<ShengtaiExamVo>  res_by_exam_id =  selectExamByField(arg_exam_vo);
        if (res_by_exam_id.size() > 1 || res_by_exam_id.size() < 0){
            // 此处应该有异常抛出
            return null;
        }
        return res_by_exam_id.get(0);
    }
}
