package com.daoli.sheng.tai.service;

import static com.daoli.constant.DBconstant.VALID;
import static com.daoli.constant.DBconstant.IN_VALID ;

import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.sheng.tai.entity.DepartmentEntity;
import com.daoli.sheng.tai.mapper.DepartmentEntityMapper;
import com.daoli.sheng.tai.mapper.DepartmentExamEntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by wanglining on 2019/8/22.
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;

    public int deleteDepartment(Integer departmentPId){

        // 删除
        DepartmentEntity departmentEntity= new DepartmentEntity();
        departmentEntity.setId(departmentPId);
        departmentEntity.setValid(IN_VALID);

        //删除
        int res = departmentEntityMapper.updateByPrimaryKeySelective(departmentEntity);
        return  res;
    }

    public int updateDeparment(DepartmentVo vo){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        BeanUtils.copyProperties(vo, departmentEntity); // vo 不设置某个属性，属性就不会被拷贝到新对象，满足selective。
        return departmentEntityMapper.updateByPrimaryKeySelective(departmentEntity);
    }

    public int insertDeparment(DepartmentVo vo){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        BeanUtils.copyProperties(vo, departmentEntity);
        departmentEntity.setDepartmentId(UUID.randomUUID().toString());
        departmentEntity.setValid(VALID);
        departmentEntity.setCreateTime(new Date());
        departmentEntity.setModifyTime(new Date());
        return departmentEntityMapper.insertSelective(departmentEntity);
    }

    public DepartmentVo queryDepartmentById(Integer id){

        DepartmentEntity departmentEntity  =  departmentEntityMapper.selectByPrimaryKey(id);

        DepartmentVo resVo = new DepartmentVo();
        BeanUtils.copyProperties(departmentEntity, resVo);
        return resVo;
    }

    public DepartmentVo queryDepartmentByBusinessId(DepartmentVo vo){

        DepartmentEntity argDepartmentEntity = new DepartmentEntity();
        argDepartmentEntity.setDepartmentId(vo.getDepartmentId());
        DepartmentEntity departmentEntity  =  departmentEntityMapper.selectByBusinessKey(argDepartmentEntity);
        DepartmentVo resVo = new DepartmentVo();
        BeanUtils.copyProperties(departmentEntity, resVo);
        return resVo;
    }

    public List<DepartmentVo> queryAllDepartment(){
        DepartmentEntity argEntry = new DepartmentEntity();
        argEntry.setDepartmentName("");
        List<DepartmentEntity> resFromSql = departmentEntityMapper.selectByFields(argEntry);
        List<DepartmentVo> res = new ArrayList<>();
        for(DepartmentEntity iterEntry : resFromSql){
            DepartmentVo desVo =  new DepartmentVo();
            BeanUtils.copyProperties(iterEntry, desVo);
            res.add(desVo);
        }
        return res;
    }

}
