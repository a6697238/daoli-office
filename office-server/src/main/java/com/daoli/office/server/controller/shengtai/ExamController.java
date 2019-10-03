package com.daoli.office.server.controller.shengtai;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.sheng.tai.service.ShengTaiDepartmentExamService;
import com.daoli.sheng.tai.service.ShengTaiExamService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AUTO-GENERATED: wln @ 2019/8/20 下午8:52
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController(value = "考核的增删改查")
@RequestMapping(value = "/api/web/sheng_tai/manage/exam")
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
    @ApiOperation(value = "批量插入，createUid、examName、examDesc、examType、examStatus、parentExamId，必须。其中 parentExamId = 0表示分类；examStatus默认为 '待发布'")
    @RequestMapping(value = "/add_batch_exam", method = RequestMethod.POST)
    public JsonResponse addBatchExam(@RequestBody List<ShengtaiExamVo> vos) {
        return new JsonResponse(shengTaiExamService.addBatchExam(vos));
    }


    @ResponseBody
    @ApiOperation(value = "批量分配, examId 表示 exam 业务键，departmentId 部门业务键 list 。")
    @RequestMapping(value = "/assign_batch_exam", method = RequestMethod.POST)
    public JsonResponse assignBatchExam(@RequestBody Map<String, Object> reqMap) {
        return new JsonResponse(shengTaiExamService
                .assignBatchExam((String) reqMap.get("examId"),
                        (List<String>) reqMap.get("departmentId")));
    }

    @ResponseBody
    @ApiOperation(value = "批量发布, examPIds exam 数据库主键 list")
    @RequestMapping(value = "/publish_batch_exam", method = RequestMethod.POST)
    public JsonResponse publishBatchExam(@RequestBody Integer[] examPIds) {
        return new JsonResponse(shengTaiExamService.publishBatchExam(examPIds));
    }

    @ResponseBody
    @ApiOperation(value = "撤销发布， examPIds exam 数据库主键 list")
    @RequestMapping(value = "/rollback_publish_batch_exam", method = RequestMethod.POST)
    public JsonResponse rollbackPublishBatchExam(@RequestBody Integer[] examPIds) {
        return new JsonResponse(shengTaiExamService.rollbackPublishBatchExam(examPIds));
    }


    @ApiOperation(value = "批量删除，examPIds exam 数据库主键 list")
    @RequestMapping(value = "/delete_batch_exam", method = RequestMethod.POST)
    public JsonResponse deleteBatchExam(@RequestBody Integer[] examPIds) {
        return new JsonResponse(shengTaiExamService.deleteBatchExam(examPIds));
    }


    @ResponseBody
    @ApiOperation(
            value = "更改一条考核考核分类、考核指标或考核要点, id 数据库主键,examName、examDesc、start_time 、end_time 等可选"
    )
    @RequestMapping(value = "/modify_exam", method = RequestMethod.POST)
    public JsonResponse modifyExam(@RequestBody ShengtaiExamVo vo) {
        shengTaiExamService.modifyExam(vo);
        return new JsonResponse();
    }


    @ResponseBody
    @ApiOperation(
            value = "开始 考核, examPId 数据库主键"
    )
    @RequestMapping(value = "/start_exam", method = RequestMethod.POST)
    public JsonResponse startExam(@RequestBody Integer examPId) {
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
            value = "结束 考核, examPId 数据库主键"
    )
    @RequestMapping(value = "/end_exam", method = RequestMethod.POST)
    public JsonResponse endExam(@RequestBody Integer examPId) {
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
            value = "获取要点已经分配的单位列表，examId 表示 exam 的业务键"
    )
    @RequestMapping(value = "/query_assigned_departments_by_exam_id", method = RequestMethod.GET)
    public JsonResponse queryAssignedDepartmentsByExamId(@RequestParam String examId) {
        return new JsonResponse(shengTaiExamService.queryAssignedDepartmentsByExamId(examId));
    }

    @ResponseBody
    @ApiOperation(
            value = "获取要点 没有分配的单位列表，examId 表示 exam 的业务键"
    )
    @RequestMapping(value = "/query_not_assigned_departments_by_exam_id", method = RequestMethod.GET)
    public JsonResponse queryNotAssignedDepartmentsByExamId(String examId) {
        return new JsonResponse(
                shengTaiExamService.queryNotAssignedDepartsmensByExamPrimaryId(examId));
    }

    @ResponseBody
    @ApiOperation(
            value = "模糊搜索:按属性获得 N 条考核分类、考核指标或考核要点,属性直接 and 关系，examName、examDesc、start_time 、end_time 等可选"
    )
    @RequestMapping(value = "/query_exams_by_fields", method = RequestMethod.GET)
    public JsonResponse queryExamsByFields(@ModelAttribute ShengtaiExamVo vo) {
        return new JsonResponse(shengTaiExamService.queryExamsByCondition(vo));
    }

    @ResponseBody
    @ApiOperation(
            value = "按主键获得一条考核分类、考核指标或考核要点，examPid 数据库主键"
    )
    @RequestMapping(value = "/query_exam_by_id", method = RequestMethod.GET)
    public JsonResponse queryExamByPId(@RequestParam Integer examPid) {
        return new JsonResponse(shengTaiExamService.queryExamByPId(examPid));
    }


    @RequestMapping(value = "/query_jin_xing_zhong_exam_by_department_id", method = RequestMethod.GET)
    @ApiOperation(
            value = "根据部门id正在进行中的考核要点"
    )
    public JsonResponse queryJinXingZhongExamByDepartmentId(@RequestParam String departmentId) {
        return new JsonResponse(
                shengTaiExamService.queryJinXingZhongExamByDepartmentId(departmentId));
    }

    @ResponseBody
    @ApiOperation(
            value = "按exam id 获得一条考核分类、考核指标或考核要点 详细信息，examId 表示 exam 的业务键"
    )
    @RequestMapping(value = "/query_exam_by_exam_id", method = RequestMethod.GET)
    public JsonResponse queryExamByExamId(@RequestParam String examId) {
        return new JsonResponse(shengTaiExamService.queryExamByExamId(examId));
    }

    @ResponseBody
    @ApiOperation(
            value = "query 所有的exam，没有参数"
    )
    @RequestMapping(value = "/query_all_exams", method = RequestMethod.POST)
    public JsonResponse queryAllExam() {

        List<ShengtaiExamVo> allVo = shengTaiExamService.queryAllExams();
        return new JsonResponse(allVo);
    }


}
