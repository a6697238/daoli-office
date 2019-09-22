package com.daoli.sheng.tai.service;

import static com.daoli.constant.ShengTaiDBconstant.IN_VALID;
import static com.daoli.constant.ShengTaiDBconstant.VALID;

import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import com.daoli.sheng.tai.mapper.DepartmentExamEntityMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglining on 2019/8/22.
 */
@Service
public class ShengTaiDepartmentExamService {
    @Autowired
    private DepartmentExamEntityMapper departmentExamEntityMapper;

    @Autowired
    private  ShengTaiExamService shengTaiExamService;

    @Autowired
    private DepartmentService deparmentService;


    @Transactional(rollbackFor = Exception.class)
    public int deleteDeparmentExam(ShengtaiDepartmentExamVo vo){

        // 获得 将来 删除 deparment_exam 的 exam_id
        ShengtaiDepartmentExamVo detailVo = selectDeparmentExamById(vo);
//        ShengtaiDepartmentExamVo argVo= new ShengtaiDepartmentExamVo();
//        argVo.setExamId(detailVo.getExamId());

        // 删除
        DepartmentExamEntity examEntry = new DepartmentExamEntity();
        //BeanUtils.copyProperties(vo,examEntry);
        examEntry.setId(vo.getId());
        examEntry.setValid(IN_VALID);
        //删除
        int res = departmentExamEntityMapper.updateByPrimaryKeySelective(examEntry);

        // 修改 exam 的 assigned_num
        ShengtaiExamVo argShengtaiExamVo = new ShengtaiExamVo();
        argShengtaiExamVo.setExamId(detailVo.getExamId());
        ShengtaiExamVo detailShengtaiExamVo = shengTaiExamService.queryExamByExamId("aa");
        ShengtaiExamVo updateShengtaiExamVo = new ShengtaiExamVo();
        updateShengtaiExamVo.setId(detailShengtaiExamVo.getId());
        updateShengtaiExamVo.setAssignedNum(detailShengtaiExamVo.getAssignedNum()-1);
        res = shengTaiExamService.updateExam(updateShengtaiExamVo);

        return  res;
    }

    public  Map<Integer,Object> deleteBatchDepartmentExam(ShengtaiDepartmentExamVo[] vos){
        Map<Integer,Object> res = new HashMap<>();
        for(ShengtaiDepartmentExamVo oneDepartmentExamVo : vos){
          int effectRows = deleteDeparmentExam(oneDepartmentExamVo);
          if (effectRows == 0){
              res.put(oneDepartmentExamVo.getId(),false);
          } else {
              res.put(oneDepartmentExamVo.getId(),true);
          }
        }
        return res;
    }

    public List<DepartmentVo> queryDepartmentsByExamPrimaryId(ShengtaiExamVo vo){
//        ShengtaiExamVo detailExamVo = new ShengtaiExamVo();
//        detailExamVo.setExamId(vo.getExamId());
//        detailExamVo = shengTaiExamService.queryExamByExamId(detailExamVo);
//        ShengtaiDepartmentExamVo argAssingVo = new ShengtaiDepartmentExamVo();
//        argAssingVo.setExamId(detailExamVo.getExamId());
//        List<ShengtaiDepartmentExamVo> listAssign = selectDeparmentExamByField(argAssingVo);
//
//        List<DepartmentVo> res = new ArrayList<>();
//        for (ShengtaiDepartmentExamVo oneAssign :listAssign){
//            DepartmentVo argDepartmentVo = new DepartmentVo();
//            argDepartmentVo.setDepartmentId(oneAssign.getDepartmentId());
//          res.add(deparmentService.queryDepartmentByBusinessId(argDepartmentVo));
//        }
        return null ; // selectDeparmentExamByField(argAssingVo);
    }

    public List<ShengtaiExamVo>  queryExamsDetailByDepartmentPrimaryId(Integer departmentPid){
        DepartmentVo filledDepartmentVo = deparmentService.queryDepartmentById(departmentPid);

        ShengtaiDepartmentExamVo argAssingVo = new ShengtaiDepartmentExamVo();
        argAssingVo.setDepartmentId(filledDepartmentVo.getDepartmentId());
        List<ShengtaiDepartmentExamVo> listAssign = selectDeparmentExamByField(argAssingVo);

        List<ShengtaiExamVo> res = new ArrayList<>();
        for (ShengtaiDepartmentExamVo oneAssign :listAssign){
            //ShengtaiExamVo argExamVo = new ShengtaiExamVo();
            //argExamVo.setExamId(oneAssign.getExamId());
            res.add(shengTaiExamService.queryExamByExamId(oneAssign.getExamId()));
        }
       return res;
    }
    /////////--------------------///////////////------------------////////----------------//////

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
        ShengtaiDepartmentExamVo resVo =new ShengtaiDepartmentExamVo();
        BeanUtils.copyProperties(departmentExamEntityMapper.selectByPrimaryKey(vo.getId()), resVo);
        return resVo;
    }


    public ShengtaiDepartmentExamVo getIdVo(ShengtaiDepartmentExamVo vo){
        DepartmentExamEntity examEntry = new DepartmentExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ArrayList<DepartmentExamEntity> res = departmentExamEntityMapper.selectByField(examEntry);
        ShengtaiDepartmentExamVo voRes = new ShengtaiDepartmentExamVo();

        if(res.size() > 1 || res.size() <= 0) {
            return null;
        }
        BeanUtils.copyProperties(res.get(0),voRes);
        return voRes;
    }

    public List<ShengtaiDepartmentExamVo> selectDeparmentExamByField(ShengtaiDepartmentExamVo vo){
        DepartmentExamEntity examEntry = new DepartmentExamEntity();
        BeanUtils.copyProperties(vo,examEntry);
        ArrayList<DepartmentExamEntity> res = departmentExamEntityMapper.selectByField(examEntry);
        List<ShengtaiDepartmentExamVo> voRes = new ArrayList<>();
        for (int i = 0; i < res.size(); ++i){
            ShengtaiDepartmentExamVo oneVo = new ShengtaiDepartmentExamVo();
            BeanUtils.copyProperties(res.get(i), oneVo);
            voRes.add(oneVo);
        }
        return voRes;
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
        depExamEntry.setValid(VALID);
        return departmentExamEntityMapper.insertSelective(depExamEntry);
    }
}
