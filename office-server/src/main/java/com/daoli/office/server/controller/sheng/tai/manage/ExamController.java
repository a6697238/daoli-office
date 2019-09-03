package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.sheng.tai.service.ShengTaiDepartmentEaxmService;
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
    private ShengTaiExamService shengTaiEaxmService;

    @Autowired
    private ShengTaiDepartmentEaxmService shengTaiDepartmentEaxmService;


    //TODO
    //1.查询 命名 query,插入 add，删除 delete
    //2.批量操作返回哪个成功，哪个失败
    //3.对于这种场景，应该是部分成功，部分失败
    //4.批量接口 用 list来承接
    @ResponseBody
    @ApiOperation(value = "批量插入")
    @RequestMapping(value = "/add_batch_exam", method = RequestMethod.POST)
    public JsonResponse addBatchExam(@RequestBody List<ShengtaiExamVo> vos) {
        return new JsonResponse(shengTaiEaxmService.addBatchExam(vos));
    }


    @ResponseBody
    @ApiOperation(value = "批量分配")
    @RequestMapping(value = "/assign_batch_exam", method = RequestMethod.POST)
    public JsonResponse assignBatchExam(@RequestBody List<ShengtaiDepartmentExamVo> vos) {
        return new JsonResponse(shengTaiEaxmService.assignBatchExam(vos));
    }

    @ResponseBody
    @ApiOperation(value = "批量发布")
    @RequestMapping(value = "/publish_batch_exam", method = RequestMethod.POST)
    public JsonResponse publishBatchExam(@RequestBody ShengtaiExamVo[] vos) {
        return  new JsonResponse(shengTaiEaxmService.publishBatchExam(vos));
    }

    @ResponseBody
    @ApiOperation(value = "撤销发布")
    @RequestMapping(value = "/backout_publish_batch_exam", method = RequestMethod.POST)
    public JsonResponse backoutPublishBatchExam(@RequestBody ShengtaiExamVo[] vos) {
        return new JsonResponse(shengTaiEaxmService.backoutPublishBatchExam(vos));
    }


    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete_batch_exam", method = RequestMethod.POST)
    public JsonResponse deleteBatchExam(@RequestBody ShengtaiExamVo[] vos) {
       return new JsonResponse(shengTaiEaxmService.deleteBatchExam(vos));
    }


    @ResponseBody
    @ApiOperation(
            value = "更改一条考核考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/update_exam", method = RequestMethod.POST)
    public JsonResponse updateExam(@RequestBody ShengtaiExamVo vo) {
        int res = 0;
        String msg = "";

        ShengtaiExamVo shengtaiExamVo = shengTaiEaxmService.selectExamById(vo);
        // 只能在 考核未开始 状态下 才能进行删除
        if (ShengTaiExamStatusConstant.KAO_HE_DAI_FA_BU.equals(shengtaiExamVo.getExamStatus())) {
            res = shengTaiEaxmService.updateExam(vo);
            msg = "更新成功!";
        } else {
            res = 0;
            msg = "考核已经发布，不能进行删除操作!";
        }
        if (res != 0) {
            return new JsonResponse();
        } else {
            return new JsonResponse(false, msg);
        }
    }





    @ResponseBody
    @ApiOperation(
            value = "开始 考核"
    )
    @RequestMapping(value = "/start_exam", method = RequestMethod.POST)
    public JsonResponse startExam(@RequestBody ShengtaiExamVo vo) {
        int res = 0;
        String msg = "";

        ShengtaiExamVo filledVo = shengTaiEaxmService.selectExamById(vo);
        // 只能在 考核未开始 状态下 才能进行删除
        if (ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI.equals(filledVo.getExamStatus())) {
            res = shengTaiEaxmService.startExam(vo);
            msg = "考核已经开始";
        } else if (ShengTaiExamStatusConstant.KAO_HE_JIN_XING_ZHONG.equals(filledVo.getExamStatus())) {
            res = 1;
            msg = "考核已经开始";
        } else {
            res = 0;
            msg = "考核已经结束";
        }
        if (res != 0) {
            return new JsonResponse();
        } else {
            return new JsonResponse(false, msg);
        }
    }

    @ResponseBody
    @ApiOperation(
            value = "结束 考核"
    )
    @RequestMapping(value = "/end_exam", method = RequestMethod.POST)
    public JsonResponse endExam(@RequestBody ShengtaiExamVo vo) {
        int res = 0;
        String msg = "";

        ShengtaiExamVo filledVo = shengTaiEaxmService.selectExamById(vo);
        // 只能在 考核未开始 状态下 才能进行删除
        if (ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI.equals(filledVo.getExamStatus() )) {
            res = 0;
            msg = "考核未开始!";
        } else if ( ShengTaiExamStatusConstant.KAO_HE_JIN_XING_ZHONG.equals(filledVo.getExamStatus() )) {
            res = shengTaiEaxmService.startExam(vo);
            msg = "考核已经结束!";
        } else {
            res = 1;
            msg = "考核已经结束!";
        }
        if (res != 0) {
            return new JsonResponse();
        } else {
            return new JsonResponse(false, msg);
        }
    }

    @ResponseBody
    @ApiOperation(
            value = "获取要点已经分配的单位列表"
    )
    @RequestMapping(value = "/query_assigned_departments_by_exam_id", method = RequestMethod.POST)
    public  JsonResponse queryAssignedepartmentsByExamId(@RequestBody ShengtaiExamVo vo) {
        return new JsonResponse( shengTaiEaxmService.queryAssignedDepartsmensByExamPrimaryId(vo));
    }

    @ResponseBody
    @ApiOperation(
            value = "获取要点 没有分配的单位列表"
    )
    @RequestMapping(value = "/query_not_assigned_departments_by_exam_id", method = RequestMethod.POST)
    public  JsonResponse queryNotAssignedDepartmentsByExamId(@RequestBody ShengtaiExamVo vo) {
        return new JsonResponse( shengTaiEaxmService.queryNotAssignedDepartsmensByExamPrimaryId(vo));
    }

    @ResponseBody
    @ApiOperation(
            value = "模糊搜索:按属性获得 N 条考核分类、考核指标或考核要点,属性直接 and 关系"
    )
    @RequestMapping(value = "/query_exams_by_and_fields", method = RequestMethod.POST)
    public  JsonResponse queryExamsByAndFields(@RequestBody ShengtaiExamVo vo) {
        return new JsonResponse( shengTaiEaxmService.selectExamByField(vo));
    }

    @ResponseBody
    @ApiOperation(
            value = "按主键获得一条考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/query_exam_by_id", method = RequestMethod.POST)
    public JsonResponse queryExamById(@RequestBody ShengtaiExamVo vo) {
        return  new JsonResponse (shengTaiEaxmService.selectExamById(vo));
    }

    @ResponseBody
    @ApiOperation(
            value = "按exam id 获得一条考核分类、考核指标或考核要点 详细信息"
    )
    @RequestMapping(value = "/query_exam_by_exam_id", method = RequestMethod.POST)
    public JsonResponse queryExamByExamId(@RequestBody ShengtaiExamVo vo) {
        return  new JsonResponse (shengTaiEaxmService.queryExamDetailByExamBusinessId(vo));
    }

    @ResponseBody
    @ApiOperation(
            value = "query 所有的exam"
    )
    @RequestMapping(value = "/query_all_exams", method = RequestMethod.POST)
    public JsonResponse  queryAllExam() {

        ShengtaiExamVo vo = new ShengtaiExamVo();
        vo.setExamName("");
        List<ShengtaiExamVo> allVo = shengTaiEaxmService.selectExamByFieldFuzzy(vo);
        return new JsonResponse(allVo);
    }

//    @ResponseBody
//    @ApiOperation(
//            value = "query 全部考核分类"
//    )
//    @RequestMapping(value = "/query_all_exam_fen_lei", method = RequestMethod.POST)
//    public List<ShengtaiExamVo> queryAllExamFenLei() {
//
//        ShengtaiExamVo vo = new ShengtaiExamVo();
//        vo.setExamType(ShengTaiExamTypeConstant.KAO_HE_FEI_LEI);
//        List<ShengtaiExamVo> allVo = shengTaiEaxmService.selectExamByFieldFuzzy(vo);
//        return allVo;
//    }
//
//    @ResponseBody
//    @ApiOperation(
//            value = "query 全部考核指标"
//    )
//    @RequestMapping(value = "/query_all_exam_zhi_biao", method = RequestMethod.POST)
//    public List<ShengtaiExamVo> queryAllExamZhiBiao() {
//
//        ShengtaiExamVo vo = new ShengtaiExamVo();
//        vo.setExamType(ShengTaiExamTypeConstant.KAO_HE_ZHI_BIAO);
//        List<ShengtaiExamVo> allVo = shengTaiEaxmService.selectExamByFieldFuzzy(vo);
//        return allVo;
//    }
//
//    @ResponseBody
//    @ApiOperation(
//            value = "query 全部考核要点"
//    )
//    @RequestMapping(value = "/query_all_exam_yao_dian", method = RequestMethod.POST)
//    public List<ShengtaiExamVo> queryAllExamYaoDian() {
//
//        ShengtaiExamVo vo = new ShengtaiExamVo();
//        vo.setExamType(ShengTaiExamTypeConstant.KAO_HE_YAO_DIAN);
//        List<ShengtaiExamVo> allVo = shengTaiEaxmService.selectExamByFieldFuzzy(vo);
//        return allVo;
//    }
//
//    @ResponseBody
//    @ApiOperation(
//            value = "query 某个 exam 的层次结构"
//    )
//    @RequestMapping(value = "/query_one_exam_tree_construct_by_id_or_exam_id", method = RequestMethod.POST)
//    public List<ShengtaiExamVo> queryOneExamTreeConstructByIdOrExamExamId(
//            @RequestBody ShengtaiExamVo arg_vo) {
//        List<ShengtaiExamVo> allVo = shengTaiEaxmService
//                .queryExamAllTreeByExamIdOrId(arg_vo);
//        if (allVo == null) {
//            return new ArrayList<>();
//        }
//        return allVo;
//    }
//
//
//    @ResponseBody
//    @ApiOperation(
//            value = "query 某个 exam 子层次结构, 有可能返回null"
//    )
//    @RequestMapping(value = "/query_one_exam_parent_by_id_or_exam_id", method = RequestMethod.POST)
//    public List<ShengtaiExamVo> queryOneExamParentByIdOrExamId(@RequestBody ShengtaiExamVo arg_vo) {
//
//        List<ShengtaiExamVo> resVo = shengTaiEaxmService
//                .queryExamSubTreeByExamIdOrId(arg_vo);
//        if (resVo == null) {
//            return new ArrayList<ShengtaiExamVo>();
//        }
//        return resVo;
//    }


}
