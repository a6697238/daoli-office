package com.daoli.office.server.controller.shengtai;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.sheng.tai.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * AUTO-GENERATED: wln @ 2019/8/20 下午8:52
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */


@RestController(value = "部门的增删改查")
@RequestMapping(value = "/api/web/sheng_tai/manage/deparment")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService deparmentService;

    @ResponseBody
    @ApiOperation(value = "批量增加部门, departmentId、valid、create_time、modify_time 等后台自动填充")
    @RequestMapping(value = "/batch_add_department", method = RequestMethod.POST)
    public JsonResponse batchAddDepartment(@RequestBody String[] departmentNames) {
        Map<String, Boolean> resMap = new HashMap<>();
        for (String name : departmentNames) {
            deparmentService.batchInsertDeparment(name);
        }
        return new JsonResponse(resMap);
    }




    @ResponseBody
    @ApiOperation(value = "删除 部门")
    @RequestMapping(value = "/delete_department", method = RequestMethod.POST)
    public JsonResponse deleteDepartment(@RequestBody Integer departmentPId) {
        int res = 0;

        res = deparmentService.deleteDepartment(departmentPId);
        if (res != 0) {
            return new JsonResponse();
        } else {
            return new JsonResponse(false, "fail");
        }
    }


    @ResponseBody
    @ApiOperation(value = "查询所有部门")
    @RequestMapping(value = "/query_all_department", method = RequestMethod.GET)
    public JsonResponse queryAllDepartment() {
        return new JsonResponse(deparmentService.queryAllDepartment());
    }

}
