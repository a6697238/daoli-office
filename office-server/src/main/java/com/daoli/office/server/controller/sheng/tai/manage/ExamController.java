package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
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
    public JsonResponse insertExam(@RequestBody ShengtaiExamVo vo) {
        int res = shengTaiEaxmService.insertExam(vo);
        if (res == 0)
            return new JsonResponse();
        else
            return new JsonResponse(false,"fail");
    }

    @ResponseBody
    @ApiOperation(value = "删除一条考核分类、考核指标或考核要点")
    @RequestMapping(value = "/delete_exam", method = RequestMethod.POST)
    public JsonResponse deleteExam(@RequestBody ShengtaiExamVo vo) {
        int res = shengTaiEaxmService.deleteExam(vo);
        if (res == 0)
            return new JsonResponse();
        else
            return new JsonResponse(false,"fail");
    }

    @ResponseBody
    @ApiOperation(
            value = "更改一条考核考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/update_exam", method = RequestMethod.POST)
    public JsonResponse updateExam(@RequestBody ShengtaiExamVo vo){
        int res = shengTaiEaxmService.updateExam(vo);
        if (res == 0)
          return new JsonResponse();
        else
            return new JsonResponse(false,"fail");
    }

    @ResponseBody
    @ApiOperation(
            value = "按主键获得一条考核分类、考核指标或考核要点"
    )
    @RequestMapping(value = "/select_exam", method = RequestMethod.POST)
    public ShengtaiExamVo selectExam(@RequestBody ShengtaiExamVo vo){
        return shengTaiEaxmService.selectExamById(vo);
    }

    @ResponseBody
    @ApiOperation(
            value = "按属性获得 N 条考核分类、考核指标或考核要点,属性直接 and 关系"
    )
    @RequestMapping(value = "/get_exam", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo> getAllExam(@RequestBody ShengtaiExamVo vo){
        return shengTaiEaxmService.selectExamByField(vo);
    }

    @ResponseBody
    @ApiOperation(
            value = "按属性获得 N 条考核分类、考核指标或考核要点，属性之间 or 关系"
    )
    @RequestMapping(value = "/get_exam_fuzzy", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamVo> getAllExamFuzzy(@RequestBody ShengtaiExamVo vo){
        return shengTaiEaxmService.selectExamByFieldFuzzy(vo);
    }

}
