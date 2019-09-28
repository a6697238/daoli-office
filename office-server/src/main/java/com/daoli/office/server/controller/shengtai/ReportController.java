package com.daoli.office.server.controller.shengtai;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.daoli.office.server.controller.BaseController;
import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ExamRecordAdditionVo;
import com.daoli.office.vo.sheng.tai.ScoreExamRecordVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.sheng.tai.service.ExamRecordService;
import com.daoli.sheng.tai.service.ShengTaiReportService;
import com.daoli.utils.FileUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * AUTO-GENERATED: houlu @ 2019/8/20 下午8:52
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController(value = "查询政治生态报表")
@RequestMapping(value = "/api/web/sheng_tai/report")
@Slf4j
public class ReportController extends BaseController {

    @Autowired
    private ShengTaiReportService reportService;


    @ResponseBody
    @ApiOperation(value = "查询标题")
    @RequestMapping(value = "/query_title", method = RequestMethod.GET)
    public JsonResponse queryTitle(long startTime, long endTime) {
        return new JsonResponse(reportService.queryTitle(startTime,endTime));
    }

    @ResponseBody
    @ApiOperation(value = "查询排名列表")
    @RequestMapping(value = "/query_rank", method = RequestMethod.GET)
    public JsonResponse queryRank(long startTime, long endTime) {
        return new JsonResponse(reportService.queryRank(startTime,endTime));
    }



    @ResponseBody
    @ApiOperation(value = "查询分类雷达")
    @RequestMapping(value = "/query_type_radar", method = RequestMethod.GET)
    public JsonResponse queryTypeRadar(long startTime, long endTime) {
        return new JsonResponse(reportService.queryTypeRadar(startTime,endTime));
    }



    @ResponseBody
    @ApiOperation(value = "查询记录占比饼图")
    @RequestMapping(value = "/query_record_rate", method = RequestMethod.GET)
    public JsonResponse queryRecordRate(long startTime, long endTime) {
        return new JsonResponse(reportService.queryRecordRate(startTime,endTime));
    }


    @ResponseBody
    @ApiOperation(value = "查询部门柱状图")
    @RequestMapping(value = "/query_department_score", method = RequestMethod.GET)
    public JsonResponse queryDepartmentScore(long startTime, long endTime) {
        return new JsonResponse(reportService.queryDepartmentScore(startTime,endTime));
    }



}
