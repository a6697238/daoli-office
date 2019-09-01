package com.daoli.sheng.tai.service;

import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import com.daoli.sheng.tai.mapper.DepartmentExamEntityMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by wanglining on 2019/8/22.
 */
@Service
public class ShengTaiDepartmentEaxmService {
    @Autowired
    private DepartmentExamEntityMapper departmentExamEntityMapper;

    public int deleteDeparmentExam(ShengtaiDepartmentExamVo vo){

        // 删除
        DepartmentExamEntity examEntry = new DepartmentExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        examEntry.setValid(new Byte((byte)0));

        //删除
        int res = departmentExamEntityMapper.updateByPrimaryKeySelective(examEntry);


        return  res;
    }

    public int updateDeparmentExam(ShengtaiDepartmentExamVo vo){
        DepartmentExamEntity examEntry = new DepartmentExamEntity();
        BeanUtils.copyProperties(vo,examEntry); // vo 不设置某个属性，属性就不会被拷贝到新对象，满足selective。
        return departmentExamEntityMapper.updateByPrimaryKeySelective(examEntry);
    }

    public int updateDeparmentExam(DepartmentExamEntity entity){
        return departmentExamEntityMapper.updateByPrimaryKeySelective(entity);
    }

    public ShengtaiDepartmentExamVo selectDeparmentExamById(ShengtaiDepartmentExamVo vo){
        DepartmentExamEntity examEntry = new DepartmentExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ShengtaiDepartmentExamVo res_vo =new ShengtaiDepartmentExamVo();
        BeanUtils.copyProperties(departmentExamEntityMapper.selectByPrimaryKey(vo.getId()), res_vo);
        return res_vo;
    }


    public ShengtaiDepartmentExamVo getIdVo(ShengtaiDepartmentExamVo vo){
        DepartmentExamEntity examEntry = new DepartmentExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ArrayList<DepartmentExamEntity> res = departmentExamEntityMapper.selectByField(examEntry);
        ShengtaiDepartmentExamVo vo_res = new ShengtaiDepartmentExamVo();

        if(res.size() > 1 || res.size() <= 0)
            return null;
        BeanUtils.copyProperties(res.get(0),vo_res);
        return vo_res;
    }

    public ArrayList<ShengtaiDepartmentExamVo> selectDeparmentExamByField(ShengtaiDepartmentExamVo vo){
        DepartmentExamEntity examEntry = new DepartmentExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ArrayList<DepartmentExamEntity> res = departmentExamEntityMapper.selectByField(examEntry);
        ArrayList<ShengtaiDepartmentExamVo> vo_res = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i){
            ShengtaiDepartmentExamVo one_vo = new ShengtaiDepartmentExamVo();
            BeanUtils.copyProperties(res.get(i), one_vo);
            vo_res.add(one_vo);
        }
        return vo_res;
    }

//    public ArrayList<ShengtaiDepartmentExamVo> selectExamFenLeiByFieldFuzzy(ShengtaiDepartmentExamVo vo){
//        DepartmentExamEntity examEntry = new DepartmentExamEntity();
//        BeanUtils.copyProperties(vo,examEntry);
//        ArrayList<DepartmentExamEntity> res = departmentExamEntityMapper.selectByFieldFuzzy(examEntry);
//        ArrayList<ShengtaiDepartmentExamVo> vo_res = new ArrayList<>();
//        for (int i = 0; i < res.size(); ++i){
//            ShengtaiDepartmentExamVo one_vo = new ShengtaiDepartmentExamVo();
//                BeanUtils.copyProperties(res.get(i), one_vo);
//                vo_res.add(one_vo);
//        }
//        return vo_res;
//    }

    public int insertDeparmentExam(ShengtaiDepartmentExamVo vo){
        DepartmentExamEntity depExamEntry = new DepartmentExamEntity();
        BeanUtils.copyProperties(vo,depExamEntry);
        depExamEntry.setValid(new Byte((byte)1));
        return departmentExamEntityMapper.insertSelective(depExamEntry);
    }
}
