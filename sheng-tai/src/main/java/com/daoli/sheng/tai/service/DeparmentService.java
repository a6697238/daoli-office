package com.daoli.sheng.tai.service;

import static com.daoli.constant.DBconstant.VALID;

import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.sheng.tai.entity.DepartmentEntity;
import com.daoli.sheng.tai.mapper.DepartmentEntityMapper;
import com.daoli.sheng.tai.mapper.DepartmentExamEntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by wanglining on 2019/8/22.
 */
@Service
public class DeparmentService {
    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;

    public int deleteDeparment(DepartmentVo vo){

        // 删除
        DepartmentEntity departmentEntity= new DepartmentEntity();
        BeanUtils.copyProperties(vo,departmentEntity);
        departmentEntity.setValid(VALID);

        //删除
        int res = departmentEntityMapper.updateByPrimaryKeySelective(departmentEntity);
        return  res;
    }

    public int updateDeparment(DepartmentVo vo){
        DepartmentEntity departmentEntity = new DepartmentEntity();
<<<<<<< HEAD
        BeanUtils.copyProperties(vo, departmentEntity); // vo 不设置某个属性，属性就不会被拷贝到新对象，满足selective。
=======
        BeanUtils.copyProperties(vo,departmentEntity);
>>>>>>> master
        return departmentEntityMapper.updateByPrimaryKeySelective(departmentEntity);
    }

    public int insertDeparment(DepartmentVo vo){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        BeanUtils.copyProperties(vo, departmentEntity);
        departmentEntity.setValid(new Byte((byte)1));
        return departmentEntityMapper.insertSelective(departmentEntity);
    }
}
