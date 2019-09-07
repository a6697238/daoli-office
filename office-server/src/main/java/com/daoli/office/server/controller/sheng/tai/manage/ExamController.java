package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.sheng.tai.service.ShengTaiDepartmentExamService;
import com.daoli.sheng.tai.service.ShengTaiExamService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AUTO-GENERATED: wln @ 2019/8/20 下午8:52
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController(value = "考核的增删改查")
@RequestMapping(value = "/api/web/manage/exam")
@Slf4j
public class ExamController {

    @Autowired
    private ShengTaiExamService shengTaiExamService;

    @Autowired
    private ShengTaiDepartmentExamService shengTaiDepartmentExamService;


    //TODO
    //1.查询 命名 query,插入 add，删除 delete
    //2.批量操作返回哪个成功，哪个失败
    //3.对于这种场景，应该是部分成功，部分失败
    //4.批量接口 用 list来承接
    @ResponseBody
    @ApiOperation(value = "批量插入")
    @RequestMapping(value = "/add_batch_exam", method = RequestMethod.POST)
    public JsonResponse addBatchExam(@RequestBody List<ShengtaiExamVo> vos) {
        return new JsonResponse(shengTaiExamService.addBatchExam(vos));
    }


    @ResponseBody
    @ApiOperation(value = "批量分配")
    @RequestMapping(value = "/assign_batch_exam", method = RequestMethod.POST)
    public JsonResponse assignBatchExam(@RequestParam String examId,
            @RequestParam List<String> departmentId) {
        return new JsonResponse(shengTaiExamService.assignBatchExam(examId,departmentId));
    }

    @ResponseBody
    @ApiOperation(value = "批量发布")
    @RequestMapping(value = "/publish_batch_exam", method = RequestMethod.POST)
    public JsonResponse publishBatchExam(@RequestParam Integer[] examPIds) {
        return new JsonResponse(shengTaiExamService.publishBatchExam(examPIds));
    }

    @ResponseBody
    @ApiOperation(value = "撤销发布")
    @RequestMapping(value = "/rollback_publish_batch_exam", method = RequestMethod.POST)
    public JsonResponse rollbackPublishBatchExam(@RequestParam Integer[] examPIds) {
        return new JsonResponse(shengTaiExamService.rollbackPublishBatchExam(examPIds));
    }


    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete_batch_exam", method = RequestMethod.POST)
    public JsonResponse deleteBatchExam(@RequestParam Integer[] examPIds) {
        return new JsonResponse(shengTaiExamService.deleteBatchExam(examPIds));
    }


    @ResponseBody
    @ApiOperation(
            value = "更改一条考核考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/modify_exam", method = RequestMethod.POST)
    public JsonResponse modifyExam(@RequestBody ShengtaiExamVo vo) {
        ShengtaiExamVo shengtaiExamVo = shengTaiExamService.queryExamByPId(vo.getId());
        if (ShengTaiExamStatusConstant.KAO_HE_DAI_FA_BU.equals(shengtaiExamVo.getExamStatus())) {
            if (shengTaiExamService.updateExam(vo) > 0) {
                return new JsonResponse();
            }
        }
        return new JsonResponse(false);
    }


    @ResponseBody
    @ApiOperation(
            value = "开始 考核"
    )
    @RequestMapping(value = "/start_exam", method = RequestMethod.POST)
    public JsonResponse startExam(@RequestParam Integer examPId) {
        ShengtaiExamVo vo = shengTaiExamService.queryExamByPId(examPId);
        if (ShengTaiExamStatusConstant.KAO_HE_YI_FA_BU.equals(vo.getExamStatus())) {
            if (shengTaiExamService.startExam(examPId) > 0) {
                return new JsonResponse();
            }
        }
        return new JsonResponse(false);
    }

    @ResponseBody
    @ApiOperation(
            value = "结束 考核"
    )
    @RequestMapping(value = "/end_exam", method = RequestMethod.POST)
    public JsonResponse endExam(@RequestParam Integer examPId) {
        ShengtaiExamVo vo = shengTaiExamService.queryExamByPId(examPId);
        // 只能在 考核未开始 状态下 才能进行删除
        if (ShengTaiExamStatusConstant.KAO_HE_YI_FA_BU.equals(vo.getExamStatus())) {
            if (shengTaiExamService.endExam(examPId) > 0) {
                return new JsonResponse();
            }
        }
        return new JsonResponse(false);

    }

    @ResponseBody
    @ApiOperation(
            value = "获取要点已经分配的单位列表"
    )
    @RequestMapping(value = "/query_assigned_departments_by_exam_id", method = RequestMethod.GET)
    public JsonResponse queryAssignedDepartmentsByExamId(@RequestParam String examId) {
        return new JsonResponse(shengTaiExamService.queryAssignedDepartmentsByExamId(examId));
    }

    @ResponseBody
    @ApiOperation(
            value = "获取要点 没有分配的单位列表"
    )
    @RequestMapping(value = "/query_not_assigned_departments_by_exam_id", method = RequestMethod.GET)
    public JsonResponse queryNotAssignedDepartmentsByExamId(String examId) {
        return new JsonResponse(shengTaiExamService.queryNotAssignedDepartsmensByExamPrimaryId(examId));
    }

    @ResponseBody
    @ApiOperation(
            value = "模糊搜索:按属性获得 N 条考核分类、考核指标或考核要点,属性直接 and 关系"
    )
    @RequestMapping(value = "/query_exams_by_and_fields", method = RequestMethod.GET)
    public JsonResponse queryExamsByAndFields(@RequestBody ShengtaiExamVo vo) {
        return new JsonResponse(shengTaiExamService.queryExamsByCondition(vo));
    }

    @ResponseBody
    @ApiOperation(
            value = "按主键获得一条考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/query_exam_by_id", method = RequestMethod.GET)
    public JsonResponse queryExamByPId(@RequestParam Integer examPid) {
        return new JsonResponse(shengTaiExamService.queryExamByPId(examPid));
    }

    @ResponseBody
    @ApiOperation(
            value = "按exam id 获得一条考核分类、考核指标或考核要点 详细信息"
    )
    @RequestMapping(value = "/query_exam_by_exam_id", method = RequestMethod.GET)
    public JsonResponse queryExamByExamId(@RequestParam String examId) {
        return new JsonResponse(shengTaiExamService.queryExamByExamId(examId));
    }

    @ResponseBody
    @ApiOperation(
            value = "query 所有的exam"
    )
    @RequestMapping(value = "/query_all_exams", method = RequestMethod.POST)
    public JsonResponse queryAllExam() {

        List<ShengtaiExamVo> allVo = shengTaiExamService.queryAllExams();
        return new JsonResponse(allVo);
    }


}
