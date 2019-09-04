package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.sheng.tai.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private DepartmentService deparmentService;

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
