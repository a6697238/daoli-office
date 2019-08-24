package com.daoli.sheng.tai.service;

import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import com.daoli.sheng.tai.mapper.ShengTaiExamEntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wanglining on 2019/8/22.
 */
@Service
public class ShengTaiEaxmService {
    @Autowired
    private ShengTaiExamEntityMapper examMapper;

    public final  String kaoHeWeiKaiShi = "考核未开始";
    public final  String kaoHeZhongTuTingZhi = "考核中途停止";
    public final  String kaoHeJinXingZhong = "考核进行中";
    public final  String kaoHeZhengChangJieShu = "考核正常结束";
    public final  String kaoHeYiChangJieShu = "考核异常结束";
//    public int insertExam(ShengtaiExamVo vo){
//        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
//        BeanUtils.copyProperties(vo,examEntry);
//        examEntry.setValid(new Byte((byte)1));
//        return examMapper.insertSelective(examEntry);
//    }

    public int deleteExam(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        examEntry.setValid(new Byte((byte)0));
        //return examMapper.deleteByPrimaryKey(vo.getId());
        return  examMapper.updateByPrimaryKeySelective(examEntry);
    }

    public int deleteExamByExamId(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        examEntry.setValid(new Byte((byte)0));
        //return examMapper.deleteByPrimaryKey(vo.getId());
        return  examMapper.updateByPrimaryKeySelective(examEntry);
    }

    public int updateExam(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry); // vo 不设置某个属性，属性就不会被拷贝到新对象，满足selective。
        return examMapper.updateByPrimaryKeySelective(examEntry);
    }

    public ShengtaiExamVo selectExamById(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ShengtaiExamVo res_vo = new ShengtaiExamVo();
        BeanUtils.copyProperties(examMapper.selectByPrimaryKey(vo.getId()),res_vo);
        return res_vo;
    }

    public ShengtaiExamVo getIdVo(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ArrayList<ShengTaiExamEntity> res = examMapper.selectByField(examEntry);
        ShengtaiExamVo vo_res = new ShengtaiExamVo();
        if (res.size()>1 || res.size() <= 0)
            return null;
        BeanUtils.copyProperties( res.get(0), vo_res);
        return vo_res;
    }

    public ArrayList<ShengtaiExamVo> selectExamByField(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ArrayList<ShengTaiExamEntity> res = examMapper.selectByField(examEntry);
        ArrayList<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i){
            ShengtaiExamVo one_vo = new ShengtaiExamVo();
            BeanUtils.copyProperties(res.get(i), one_vo);
            vo_res.add(one_vo);
        }
        return vo_res;
    }

    public ArrayList<ShengtaiExamVo> selectExamByFieldFuzzy(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ArrayList<ShengTaiExamEntity> res = examMapper.selectByFieldFuzzy(examEntry);
        ArrayList<ShengtaiExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i){
                ShengtaiExamVo one_vo = new ShengtaiExamVo();
                BeanUtils.copyProperties(res.get(i), one_vo);
                vo_res.add(one_vo);
        }
        return vo_res;
    }

    public int insertExam(ShengtaiExamVo vo){
        ShengTaiExamEntity examEntry = new ShengTaiExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        examEntry.setValid(new Byte((byte)1));
        return examMapper.insertSelective(examEntry);
    }
}
