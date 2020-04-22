package com.daoli.sheng.tai.service;

import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.VALID;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.IN_VALID;

import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.sheng.tai.entity.DepartmentEntity;
import com.daoli.sheng.tai.mapper.DepartmentEntityMapper;
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

    public int deleteDepartment(Integer departmentPId) {

        // 删除
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(departmentPId);
        departmentEntity.setValid(IN_VALID);

        //删除
        int res = departmentEntityMapper.updateByPrimaryKeySelective(departmentEntity);
        return res;
    }


    public int batchInsertDeparment(String name) {
        return departmentEntityMapper
                .insertSelective(DepartmentEntity.builder().departmentName(name)
                        .departmentId(UUID.randomUUID().toString())
                        .valid(VALID)
                        .createTime(new Date())
                        .modifyTime(new Date())
                        .build());
    }

    public DepartmentVo queryDepartmentById(Integer id) {

        DepartmentEntity departmentEntity = departmentEntityMapper.selectByPrimaryKey(id);

        DepartmentVo resVo = new DepartmentVo();
        BeanUtils.copyProperties(departmentEntity, resVo);
        return resVo;
    }

    public List<DepartmentVo> queryAllDepartment() {
        DepartmentEntity argEntry = new DepartmentEntity();
        argEntry.setDepartmentName("");
        List<DepartmentEntity> resFromSql = departmentEntityMapper
                .queryDepartmentByFields(argEntry);
        List<DepartmentVo> res = new ArrayList<>();
        for (DepartmentEntity iterEntry : resFromSql) {
            DepartmentVo desVo = new DepartmentVo();
            BeanUtils.copyProperties(iterEntry, desVo);
            res.add(desVo);
        }
        return res;
    }

}
