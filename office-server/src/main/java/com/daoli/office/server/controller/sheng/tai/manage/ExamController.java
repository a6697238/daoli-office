package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamTypeConstant;
import com.daoli.sheng.tai.service.ShengTaiDepartmentEaxmService;
import com.daoli.sheng.tai.service.ShengTaiEaxmService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RestController(value = "考核的增删改查")
@RequestMapping(value = "/api/web/manage/exam")
@Slf4j
public class ExamController {

    @Autowired
    private ShengTaiEaxmService shengTaiEaxmService;
    @Autowired
    private ShengTaiDepartmentEaxmService shengTaiDepartmentEaxmService;


    @ResponseBody
    @ApiOperation(value = "插入一条考核分类、考核指标或考核要点")
    @RequestMapping(value = "/insert_exam", method = RequestMethod.POST)
    public JsonResponse insertExam(@RequestBody ShengtaiExamVo vo ) {
        int res = shengTaiEaxmService.insertExam(vo);
        String msg = "插入成功!";
        if (res != 0)
            return new JsonResponse();
        else
            return new JsonResponse(false,msg);
    }

    @ResponseBody
    @ApiOperation(value = "批量插入 考核分类、考核指标或考核要点")
    @RequestMapping(value = "/insert_batch_exam", method = RequestMethod.POST)
    public JsonResponse insertExam(@RequestBody ShengtaiExamVo[] vos ) {
        int res = 0;
        for(ShengtaiExamVo vo:vos) {
            res = shengTaiEaxmService.insertExam(vo);
            if (res == 0){
                msg = "[" + vo.getExamName() + "]" + "插入失败!";
                break;
            }
        }
        String msg = "批量插入成功!";
        if (res != 0)
            return new JsonResponse();
        else
            return new JsonResponse(false,msg);
    }

    @ResponseBody
    @ApiOperation(value = "删除一条考核分类、考核指标或考核要点")
    @RequestMapping(value = "/delete_exam", method = RequestMethod.POST)
    public JsonResponse deleteExam(@RequestBody ShengtaiExamVo vo) {
        int res = 0;
        String msg = "";

        ShengtaiExamVo detail_vo = shengTaiEaxmService.selectExamById(vo);
        // 只能在 考核未开始 状态下 才能进行删除
        if(detail_vo.getExamStatus() == ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI) {
            res = shengTaiEaxmService.deleteExam(vo);
            msg = "删除成功!";
        } else {
            res = 0;
            msg = "考核已经开始，不能进行删除操作!";
        }
        if (res != 0)
            return new JsonResponse();
        else
            return new JsonResponse(false,msg);
    }

    @ResponseBody
    @ApiOperation(
            value = "更改一条考核考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/update_exam", method = RequestMethod.POST)
    public JsonResponse updateExam(@RequestBody ShengtaiExamVo vo){
        int res = 0;
        String msg = "";

        ShengtaiExamVo detail_vo = shengTaiEaxmService.selectExamById(vo);
        // 只能在 考核未开始 状态下 才能进行删除
        if(detail_vo.getExamStatus() == ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI) {
            res = shengTaiEaxmService.updateExam(vo);
            msg = "更新成功!";
        } else {
           res = 0;
           msg = "考核已经开始，不能进行删除操作!";
        }
        if (res != 0)
          return new JsonResponse();
        else
            return new JsonResponse(false,msg);
    }

    @ResponseBody
    @ApiOperation(
            value = "开始 考核"
    )
    @RequestMapping(value = "/start_exam", method = RequestMethod.POST)
    public JsonResponse startExam(@RequestBody ShengtaiExamVo vo){
        int res = 0;
        String msg = "";

        ShengtaiExamVo detail_vo = shengTaiEaxmService.selectExamById(vo);
        // 只能在 考核未开始 状态下 才能进行删除
        if(detail_vo.getExamStatus() == ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI) {
            res = shengTaiEaxmService.startExam(vo);
            msg = "考核已经开始";
        } else if(detail_vo.getExamStatus() == ShengTaiExamStatusConstant.KAO_HE_JIN_XING_ZHONG){
           res = 1;
           msg = "考核已经开始";
        } else {
            res = 0;
            msg = "考核已经结束";
        }
        if (res != 0)
          return new JsonResponse();
        else
            return new JsonResponse(false,msg);
    }

    @ResponseBody
    @ApiOperation(
            value = "结束 考核"
    )
    @RequestMapping(value = "/end_exam", method = RequestMethod.POST)
    public JsonResponse endExam(@RequestBody ShengtaiExamVo vo){
        int res = 0;
        String msg = "";

        ShengtaiExamVo detail_vo = shengTaiEaxmService.selectExamById(vo);
        // 只能在 考核未开始 状态下 才能进行删除
        if(detail_vo.getExamStatus() == ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI) {
            res = 0;
            msg = "考核未开始!";
        } else if(detail_vo.getExamStatus() == ShengTaiExamStatusConstant.KAO_HE_JIN_XING_ZHONG){
            res = shengTaiEaxmService.startExam(vo);
            msg = "考核已经结束!";
        } else {
            res = 1;
            msg = "考核已经结束!";
        }
        if (res != 0)
          return new JsonResponse();
        else
            return new JsonResponse(false,msg);
    }

    @ResponseBody
    @ApiOperation(
            value = "按主键获得一条考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/query_exam_by_id", method = RequestMethod.POST)
    public ShengtaiExamVo queryExamById(@RequestBody ShengtaiExamVo vo){
        return shengTaiEaxmService.selectExamById(vo);
    }

    @ResponseBody
    @ApiOperation(
            value = "按exam id 获得一条考核分类、考核指标或考核要点 详细信息"
    )
    @RequestMapping(value = "/query_exam_by_exam_id", method = RequestMethod.POST)
    public ShengtaiExamVo queryExamByExamId(@RequestBody ShengtaiExamVo vo){
        return shengTaiEaxmService.query_exam_detail_by_exam_id(vo);
    }

    @ResponseBody
    @ApiOperation(
            value = "按属性获得 N 条考核分类、考核指标或考核要点,属性直接 and 关系"
    )
    @RequestMapping(value = "/query_exams_by_and_fields", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo> queryExamsByAndFields(@RequestBody ShengtaiExamVo vo){
        return shengTaiEaxmService.selectExamByField(vo);
    }

    @ResponseBody
    @ApiOperation(
            value = "按属性获得 N 条考核分类、考核指标或考核要点，属性之间 or 关系"
    )
    @RequestMapping(value = "/query_exams_by_fuzzy_fields", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo> queryExamByFuzzyFields(@RequestBody ShengtaiExamVo vo){
        return shengTaiEaxmService.selectExamByFieldFuzzy(vo);
    }

    @ResponseBody
    @ApiOperation(
            value = "query 所有的exam"
    )
    @RequestMapping(value = "/query_all_exams", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo>  queryAllExam(){

        ShengtaiExamVo vo = new ShengtaiExamVo();
        vo.setExamName("*");
        ArrayList<ShengtaiExamVo> all_vo= shengTaiEaxmService.selectExamByFieldFuzzy(vo);
        return all_vo;
    }

    @ResponseBody
    @ApiOperation(
            value = "query 全部考核分类"
    )
    @RequestMapping(value = "/query_all_exam_fen_lei", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo>  queryAllExamFenLei(){

        ShengtaiExamVo vo = new ShengtaiExamVo();
        vo.setExamType(ShengTaiExamTypeConstant.KAO_HE_FEI_LEI);
        ArrayList<ShengtaiExamVo> all_vo= shengTaiEaxmService.selectExamByFieldFuzzy(vo);
        return all_vo;
    }

    @ResponseBody
    @ApiOperation(
            value = "query 全部考核指标"
    )
    @RequestMapping(value = "/query_all_exam_zhi_biao", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo>  queryAllExamZhiBiao(){

        ShengtaiExamVo vo = new ShengtaiExamVo();
        vo.setExamType(ShengTaiExamTypeConstant.KAO_HE_ZHI_BIAO);
        ArrayList<ShengtaiExamVo> all_vo= shengTaiEaxmService.selectExamByFieldFuzzy(vo);
        return all_vo;
    }

    @ResponseBody
    @ApiOperation(
            value = "query 全部考核要点"
    )
    @RequestMapping(value = "/query_all_exam_yao_dian", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo>  queryAllExamYaoDian(){

        ShengtaiExamVo vo = new ShengtaiExamVo();
        vo.setExamType(ShengTaiExamTypeConstant.KAO_HE_YAO_DIAN);
        ArrayList<ShengtaiExamVo> all_vo= shengTaiEaxmService.selectExamByFieldFuzzy(vo);
        return all_vo;
    }

    @ResponseBody
    @ApiOperation(
            value = "query 某个 exam 的层次结构"
    )
    @RequestMapping(value = "/query_one_exam_tree_construct_by_id_or_exam_id", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo>  queryOneExamTreeConstructByIdOrExamExamId(@RequestBody ShengtaiExamVo arg_vo){
        ArrayList<ShengtaiExamVo> all_vo= shengTaiEaxmService.query_exam_all_tree_by_exam_id_or_id(arg_vo);
        if (all_vo == null)
            return new ArrayList<>();
        return all_vo;
    }


    @ResponseBody
    @ApiOperation(
            value = "query 某个 exam 子层次结构, 有可能返回null"
    )
    @RequestMapping(value = "/query_one_exam_parent_by_id_or_exam_id", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo>  queryOneExamParentByIdOrExamId(@RequestBody ShengtaiExamVo arg_vo){

        ArrayList<ShengtaiExamVo> res_vo= shengTaiEaxmService.query_exam_sub_tree_by_exam_id_or_id(arg_vo);
        if (res_vo == null){
            return new ArrayList<ShengtaiExamVo>();
        }
        return res_vo;
    }
}
