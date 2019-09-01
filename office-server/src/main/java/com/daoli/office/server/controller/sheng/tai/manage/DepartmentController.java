package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import com.daoli.sheng.tai.service.DeparmentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * AUTO-GENERATED: wln @ 2019/8/20 下午8:52
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */



@RestController(value = "部门的增删改查")
@RequestMapping(value = "/api/web/manage/deparment")
@Slf4j
public class DepartmentController {

    @Autowired
    private DeparmentService deparmentService;

    @ResponseBody
    @ApiOperation(value = "增加部门")
    @RequestMapping(value = "/add_department", method = RequestMethod.POST)
    public JsonResponse addDepartment(@RequestBody DepartmentVo vo) {
        int res = 0;

        res = deparmentService.insertDeparment(vo);
        if (res != 0) {
            return new JsonResponse();
        } else {
            return new JsonResponse(false,"fail");
        }
    }

    @ResponseBody
    @ApiOperation(value = "删除 部门")
    @RequestMapping(value = "/delete_department", method = RequestMethod.POST)
    public JsonResponse deleteDepartmen(@RequestBody DepartmentVo vo) {
        int res = 0;

        res = deparmentService.deleteDeparment(vo);
        if (res != 0) {
            return new JsonResponse();
        } else {
            return new JsonResponse(false,"fail");
        }
    }

}
