package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import com.daoli.sheng.tai.service.ShengTaiDepartmentEaxmService;
import com.daoli.sheng.tai.service.ShengTaiEaxmService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * AUTO-GENERATED: houlu @ 2019/8/20 下午8:52
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController(value = "试卷的增删改查")
@RequestMapping(value = "/api/web/manage/deparment_exam")
@Slf4j
public class DepartmentExamController {

    @Autowired
    private ShengTaiDepartmentEaxmService shengTaiDepartmentEaxmService;
    @Autowired
    private ShengTaiEaxmService shengTaiEaxmService;

    @ResponseBody
    @ApiOperation(
            value = "给某个部门发配考核要点"
    )
    @RequestMapping(value = "/send_exam_to_department", method = RequestMethod.POST)
    public JsonResponse sendExamToDepartment(@RequestBody ShengtaiDepartmentExamVo vo){

        int res = 0;

        ShengtaiExamVo exam_vo = new ShengtaiExamVo();
        exam_vo.setExamId(vo.getExamId());
        ShengtaiExamVo id_vo = shengTaiEaxmService.getIdVo(exam_vo);
        if (id_vo != null) {
            id_vo.setExamStatus(shengTaiEaxmService.kaoHeJinXingZhong);
            res = shengTaiEaxmService.updateExam(id_vo);
            res = shengTaiDepartmentEaxmService.insertDeparmentExam(vo);
        } else {
            res = 0;
        }

        if (res != 0)
            return new JsonResponse();
        else
            return new JsonResponse(false,"fail");
    }

    @ResponseBody
    @ApiOperation(value = "删除一条部门考核")
    @RequestMapping(value = "/delete_department_exam", method = RequestMethod.POST)
    public JsonResponse deleteDepartmentExam(@RequestBody ShengtaiDepartmentExamVo vo) {

        // 获得 将来 删除 deparment_exam 的 exam_id
        ShengtaiDepartmentExamVo detail_vo = shengTaiDepartmentEaxmService.selectDeparmentExamById(vo);
        ShengtaiDepartmentExamVo arg_vo= new ShengtaiDepartmentExamVo();
        arg_vo.setExamId(detail_vo.getExamId());

        int res = shengTaiDepartmentEaxmService.deleteDeparmentExam(vo);



        // 当没有对 exam id 的引用时， 修改 exam_id 对应的状态
        if (shengTaiDepartmentEaxmService.selectDeparmentExamByField(arg_vo).size()<=0){

            // 获得 exam_id 对应的 含有 id 的 vo
            // exam_id vo 个数 > 1 或 <= 0 都是不符合预期的，需要回滚
            ShengtaiExamVo arg_exam_vo = new ShengtaiExamVo();
            arg_exam_vo.setExamId(arg_vo.getExamId());
            ShengtaiExamVo exam_id_vo = shengTaiEaxmService.getIdVo(arg_exam_vo);
            ArrayList<ShengtaiExamVo> array_select_exam_id = shengTaiEaxmService.selectExamByField(arg_exam_vo);
            if (exam_id_vo == null){
                res = 0;
                DepartmentExamEntity examEntry = new DepartmentExamEntity();
                BeanUtils.copyProperties(vo,examEntry);
                examEntry.setValid(new Byte((byte)1));
                shengTaiDepartmentEaxmService.updateDeparmentExam(examEntry);
            }else{
                exam_id_vo.setExamStatus(shengTaiEaxmService.kaoHeZhongTuTingZhi);
                shengTaiEaxmService.updateExam(exam_id_vo);
                res = 1;
            }
        }

        if (res != 0)
            return new JsonResponse();
        else
            return new JsonResponse(false,"fail");
    }
/*
    @ResponseBody
    @ApiOperation(
            value = "更改一条考核考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/update_department_exam", method = RequestMethod.POST)
    public JsonResponse updateDepartmentExam(@RequestBody ShengtaiDepartmentExamVo vo){
        int res = shengTaiDepartmentEaxmService.updateDeparmentExam(vo);
        if (res != 0)
          return new JsonResponse();
        else
            return new JsonResponse(false,"fail");
    }
*/
    @ResponseBody
    @ApiOperation(
            value = "获得部门 x 全部考核要点"
    )
    @RequestMapping(value = "/select_exams_by_department", method = RequestMethod.POST)
    public ArrayList<ShengtaiDepartmentExamVo> selectExamsByDepartment(@RequestBody ShengtaiDepartmentExamVo vo){
        return shengTaiDepartmentEaxmService.selectDeparmentExamByField(vo);
    }

    @ResponseBody
    @ApiOperation(
            value = "获得考核要点 y 派发给哪些部门"
    )
    @RequestMapping(value = "/select_departments_by_exam", method = RequestMethod.POST)
    public ArrayList<ShengtaiDepartmentExamVo> selectDepartmentsByExam(@RequestBody ShengtaiDepartmentExamVo vo){
        return shengTaiDepartmentEaxmService.selectDeparmentExamByField(vo);
    }

//    @ResponseBody
//    @ApiOperation(
//            value = "按属性获得 N 条考核分类、考核指标或考核要点,属性直接 and 关系"
//    )
//    @RequestMapping(value = "/get_exam", method = RequestMethod.POST)
//    public ArrayList<ShengtaiExamVo> getAllExam(@RequestBody ShengtaiExamVo vo){
//        return shengTaiDepartmentEaxmService.selectExamFenLeiByField(vo);
//    }
//
//    @ResponseBody
//    @ApiOperation(
//            value = "按属性获得 N 条考核分类、考核指标或考核要点，属性之间 or 关系"
//    )
//    @RequestMapping(value = "/get_exam_fuzzy", method = RequestMethod.POST)
//    public ArrayList<ShengtaiExamVo> getAllExamFuzzy(@RequestBody ShengtaiExamVo vo){
//        return shengTaiDepartmentEaxmService.selectExamFenLeiByFieldFuzzy(vo);
//    }



}
