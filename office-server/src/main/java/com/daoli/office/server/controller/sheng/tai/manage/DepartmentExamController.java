package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.DepartmentVo;
import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.sheng.tai.service.ExamRecordService;
import com.daoli.sheng.tai.service.ShengTaiDepartmentExamService;
import com.daoli.sheng.tai.service.ShengTaiExamService;
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
@RestController(value = "试卷的删查")
@RequestMapping(value = "/api/web/manage/deparment_exam")
@Slf4j
public class DepartmentExamController {

    @Autowired
    private ShengTaiDepartmentExamService shengTaiDepartmentExamService;
    @Autowired
    private ShengTaiExamService shengTaiExamService; //shengTaiExamService
    @Autowired
    private ExamRecordService examRecordService;


    //@ResponseBody
    //@ApiOperation(
    //        value = "通过 exam id 获得 分配的部门list"
    //)
    //@RequestMapping(value = "/query_departments_by_exam_primary_id", method = RequestMethod.POST)
    //public JsonResponse queryDepartmentsByExamPrimaryId(@RequestBody ShengtaiExamVo vo){
    //  return  new JsonResponse(shengTaiDepartmentExamService.queryDepartmentsByExamPrimaryId(vo));
    //}

    @ResponseBody
    @ApiOperation(
            value = "通过 department id 获得分配的考核要点"
    )
    @RequestMapping(value = "/query_exams_detail_by_department_primary_id", method = RequestMethod.POST)
    public JsonResponse queryExamsDetailByDepartmentPrimaryId(@RequestBody  Integer departmentPid){
       return  new JsonResponse(shengTaiDepartmentExamService.queryExamsDetailByDepartmentPrimaryId(departmentPid));
    }

//    @ResponseBody
//    @ApiOperation(
//            value = "获得部门 x 全部raw考核要点, id、depart_id、exam_id"
//    )
//    @RequestMapping(value = "/query_exams_raw_by_department", method = RequestMethod.POST)
//    public List<ShengtaiDepartmentExamVo> queryExamsRawByDepartment(@RequestBody ShengtaiDepartmentExamVo vo){
//        //去重
//        List<ShengtaiDepartmentExamVo> arr_raw_exam_vo=  shengTaiDepartmentEaxmService.selectDeparmentExamByField(vo);
//        return arr_raw_exam_vo;
//    }
//
//    @ResponseBody
//    @ApiOperation(
//            value = "获得部门 x 提交的 全部Record"
//    )
//    @RequestMapping(value = "/query_records_by_department", method = RequestMethod.POST)
//    public ArrayList<ShengtaiExamRecordVo> queryRecordsByDepartment(@RequestBody ShengtaiDepartmentExamVo vo){
//        ArrayList<ShengtaiExamRecordVo> arr_record_vo = new ArrayList<>();
//        List<ShengtaiDepartmentExamVo> raw_depart_exam_vo = queryExamsRawByDepartment(vo);
//        for(ShengtaiDepartmentExamVo one_depart_exam: raw_depart_exam_vo){
//            ShengtaiExamRecordVo query_recor_vo = new ShengtaiExamRecordVo();
//            query_recor_vo.setDepartmentId(one_depart_exam.getDepartmentId());
//            arr_record_vo.addAll(examRecordService.queryRecordByCondition(query_recor_vo));
//        }
//        return arr_record_vo;
//    }
//    @ResponseBody
//    @ApiOperation(
//            value = "获得部门 x 的要点 a 提交的 全部Record"
//    )
//    @RequestMapping(value = "/query_records_by_department_and_yao_dian", method = RequestMethod.POST)
//    public ArrayList<ShengtaiExamRecordVo> queryRecordsByDepartmentAndYaoDian(@RequestBody ShengtaiDepartmentExamVo one_depart_exam){
//        ArrayList<ShengtaiExamRecordVo> arr_record_vo = new ArrayList<>();
//        ShengtaiExamRecordVo query_recor_vo = new ShengtaiExamRecordVo();
//            query_recor_vo.setExamDetailId(one_depart_exam.getExamId());
//            query_recor_vo.setDepartmentId(one_depart_exam.getDepartmentId());
//            arr_record_vo.addAll(examRecordService.queryRecordByDetailIdiAndDepartId(query_recor_vo));
//        return arr_record_vo;
//    }



}
