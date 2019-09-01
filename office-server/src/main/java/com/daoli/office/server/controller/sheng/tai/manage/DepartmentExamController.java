package com.daoli.office.server.controller.sheng.tai.manage;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ShengtaiDepartmentExamVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.office.vo.sheng.tai.constant.ShengTaiExamStatusConstant;
import com.daoli.sheng.tai.entity.DepartmentExamEntity;
import com.daoli.sheng.tai.service.ExamRecordService;
import com.daoli.sheng.tai.service.ShengTaiDepartmentEaxmService;
import com.daoli.sheng.tai.service.ShengTaiExamService;
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
@RestController(value = "试卷的增删改查")
@RequestMapping(value = "/api/web/manage/deparment_exam")
@Slf4j
public class DepartmentExamController {

    @Autowired
    private ShengTaiDepartmentEaxmService shengTaiDepartmentEaxmService;
    @Autowired
    private ShengTaiExamService shengTaiExamService; //shengTaiExamService
    @Autowired
    private ExamRecordService examRecordService;

//    @ResponseBody
//    @ApiOperation(
//            value = "给某个部门发配考核要点"
//    )
//    @RequestMapping(value = "/send_exam_to_department", method = RequestMethod.POST)
//    public JsonResponse sendExamToDepartment(@RequestBody ShengtaiDepartmentExamVo vo){
//
//        int res = 0;
//
//        ShengtaiExamVo examVo = new ShengtaiExamVo();
//        exam_vo.setExamId(vo.getExamId());
//        ShengtaiExamVo id_vo = shengTaiExamService.getIdVo(exam_vo);
//        if (id_vo != null) {
//            id_vo.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_WEI_KAI_SHI);
//            res = shengTaiExamService.updateExam(id_vo);
//            res = shengTaiDepartmentEaxmService.insertDeparmentExam(vo);
//        } else {
//            res = 0;
//        }
//
//        if (res != 0) {
//            return new JsonResponse();
//        } else {
//            return new JsonResponse(false,"fail");
//        }
        //ShengtaiExamVo examVo = shengTaiExamService.getIdVo()

//    }

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
            ShengtaiExamVo exam_id_vo = shengTaiExamService.queryExamByExamId(arg_exam_vo);
            List<ShengtaiExamVo> array_select_exam_id = shengTaiExamService.selectExamByField(arg_exam_vo);
            if (exam_id_vo == null){
                res = 0;
                DepartmentExamEntity examEntry = new DepartmentExamEntity();
                BeanUtils.copyProperties(vo,examEntry);
                examEntry.setValid(new Byte((byte)1));
                shengTaiDepartmentEaxmService.updateDeparmentExam(examEntry);
            }else{
                exam_id_vo.setExamStatus(ShengTaiExamStatusConstant.KAO_HE_ZHONG_TU_TING_ZHI );
                shengTaiExamService.updateExam(exam_id_vo);
                res = 1;
            }
        }

        if (res != 0) {
            return new JsonResponse();
        } else {
            return new JsonResponse(false,"fail");
        }
    }

    @ResponseBody
    @ApiOperation(
            value = "获得部门 x 全部考核要点详情, 将会返回要点的父节点"
    )
    @RequestMapping(value = "/query_exams_detail_by_department", method = RequestMethod.POST)
    public List<ShengtaiExamVo> queryExamsDetailByDepartment(@RequestBody ShengtaiDepartmentExamVo vo){
        ArrayList<ShengtaiExamVo> res = new ArrayList<>();
        //去重
        HashSet<ShengtaiExamVo > exam_vo_set = new HashSet<>();
        ArrayList<ShengtaiDepartmentExamVo> arr_raw_exam_vo=  shengTaiDepartmentEaxmService.selectDeparmentExamByField(vo);
        for(ShengtaiDepartmentExamVo raw_exam_vo : arr_raw_exam_vo){
            ShengtaiExamVo query_exam_vo = new ShengtaiExamVo();
            query_exam_vo.setExamId(raw_exam_vo.getExamId());
            List<ShengtaiExamVo> tmp = shengTaiExamService.query_exam_all_tree_by_exam_id_or_id(query_exam_vo);
            if (tmp != null)
              for (ShengtaiExamVo t:tmp){
                 exam_vo_set.add(t);
              }
        }
        Iterator it = exam_vo_set.iterator();
        while (it.hasNext()){
           res.add((ShengtaiExamVo) it.next());
        }
        return res;
    }

    @ResponseBody
    @ApiOperation(
            value = "获得部门 x 全部raw考核要点, id、depart_id、exam_id"
    )
    @RequestMapping(value = "/query_exams_raw_by_department", method = RequestMethod.POST)
    public ArrayList<ShengtaiDepartmentExamVo> queryExamsRawByDepartment(@RequestBody ShengtaiDepartmentExamVo vo){
        //去重
        ArrayList<ShengtaiDepartmentExamVo> arr_raw_exam_vo=  shengTaiDepartmentEaxmService.selectDeparmentExamByField(vo);
        return arr_raw_exam_vo;
    }

    @ResponseBody
    @ApiOperation(
            value = "获得部门 x 提交的 全部Record"
    )
    @RequestMapping(value = "/query_records_by_department", method = RequestMethod.POST)
    public ArrayList<ShengtaiExamRecordVo> queryRecordsByDepartment(@RequestBody ShengtaiDepartmentExamVo vo){
        ArrayList<ShengtaiExamRecordVo> arr_record_vo = new ArrayList<>();
        ArrayList<ShengtaiDepartmentExamVo> raw_depart_exam_vo = queryExamsRawByDepartment(vo);
        for(ShengtaiDepartmentExamVo one_depart_exam: raw_depart_exam_vo){
            ShengtaiExamRecordVo query_recor_vo = new ShengtaiExamRecordVo();
            query_recor_vo.setDepartmentId(one_depart_exam.getDepartmentId());
            arr_record_vo.addAll(examRecordService.queryRecordByCondition(query_recor_vo));
        }
        return arr_record_vo;
    }
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
    @ResponseBody
    @ApiOperation(
            value = "获得考核要点 y 派发给哪些部门。因为department   有待完善"
    )
    @RequestMapping(value = "/query_departments_by_exam", method = RequestMethod.POST)
    public ArrayList<ShengtaiDepartmentExamVo> queryDepartmentsByExam(@RequestBody ShengtaiDepartmentExamVo vo){
        return shengTaiDepartmentEaxmService.selectDeparmentExamByField(vo);
    }


}
