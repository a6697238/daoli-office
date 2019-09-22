package com.daoli.office.server.controller.shengtai;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.daoli.office.server.controller.BaseController;
import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ExamRecordAdditionVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.sheng.tai.service.ExamRecordService;
import com.daoli.utils.FileUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;
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
@RestController(value = "上传考核内容")
@RequestMapping(value = "/api/web/exam_record")
@Slf4j
public class ExamRecordController extends BaseController {

    @Autowired
    private ExamRecordService examRecordService;


    @Value("${daoli.baseSavePath}")
    private String baseSavePath;

    @ResponseBody
    @ApiOperation(value = "上传一条考核记录")
    @RequestMapping(value = "/upload_exam_record", method = RequestMethod.POST)
    public JsonResponse uploadExamRecord(@RequestBody ShengtaiExamRecordVo vo) {
        examRecordService.uploadRecord(vo, Lists.newArrayList(vo.getAdditionId()));
        return new JsonResponse();
    }


    @ResponseBody
    @ApiOperation(value = "修改一条考核记录")
    @RequestMapping(value = "/modify_exam_record", method = RequestMethod.POST)
    public JsonResponse modifyExamRecord(@RequestBody ShengtaiExamRecordVo req,
            @RequestParam List<Integer> additionId) {
        examRecordService.modifyRecord(req, Lists.newArrayList(additionId));
        return new JsonResponse();
    }


    @ResponseBody
    @ApiOperation(value = "给一条考核记录打分")
    @RequestMapping(value = "/score_exam_record", method = RequestMethod.POST)
    public JsonResponse scoreExamRecord(@RequestParam Float score,
            @RequestParam String detailId, @RequestParam String departmentId) {
        examRecordService.scoreExamRecord(score, detailId, departmentId);
        return new JsonResponse();
    }


    @ResponseBody
    @ApiOperation(value = "上传一条考核附件")
    @RequestMapping(value = "/upload_exam_addition", method = RequestMethod.POST)
    public JsonResponse uploadExamAddition(@RequestParam String userId,
            @RequestParam MultipartFile[] recordAdditionFile) throws IOException {
        List<ExamRecordAdditionVo> resList = Lists.newArrayList();
        for (MultipartFile file : recordAdditionFile) {
            if (!file.isEmpty()) {
                String relativeFile = "/shengtai/" + userId + "/" + file.getOriginalFilename();
                String relativePath = "/shengtai/" + userId;
                FileUtils.writeFile(file.getOriginalFilename(), baseSavePath + relativePath,
                        file.getBytes());
                ExamRecordAdditionVo vo = ExamRecordAdditionVo.builder()
                        .additionLocation(relativeFile)
                        .additionName(file.getOriginalFilename())
                        .createUid(userId)
                        .createTime(new Date())
                        .modifyTime(new Date())
                        .build();
                examRecordService.uploadAddition(vo);
                resList.add(vo);
            }
        }
        return new JsonResponse(resList);
    }


    @ResponseBody
    @ApiOperation(value = "查询一条考核记录")
    @RequestMapping(value = "/query_exam_record", method = RequestMethod.GET)
    @ApiImplicitParam(value = "1(数据库自增主键)", name = "recordId", required = true, dataType = "String", paramType = "query")
    public JsonResponse queryExamRecord(@RequestParam Integer recordId) {
        ShengtaiExamRecordVo vo = examRecordService.queryExamRecord(recordId);
        List<ExamRecordAdditionVo> additionVoList = examRecordService
                .queryRecordAdditionList(vo.getId());
        return new JsonResponse(
                ImmutableMap.<String, Object>builder()
                        .put("vo", vo)
                        .put("additionVoList", additionVoList)
                        .build());
    }


    @ResponseBody
    @ApiOperation(value = "根据部门查询考核记录")
    @RequestMapping(value = "/query_exam_record_by_department_id", method = RequestMethod.GET)

    public JsonResponse queryExamRecordByDepartmentId(@RequestParam String departmentId,
            @RequestParam long startTime,
            @RequestParam long endTime) {
        return new JsonResponse(
                examRecordService.queryExamRecordByDepartmentId(departmentId, startTime, endTime));
    }


    @ResponseBody
    @ApiOperation(value = "查询打分目录")
    @RequestMapping(value = "/query_department_score_report", method = RequestMethod.GET)
    @ApiImplicitParam(value = "XIANG_ZHEN|SHI_QU||", name = "departmentType", required = true, dataType = "String", paramType = "query")
    public JsonResponse queryDepartmentScoreReport(@RequestParam String departmentName,
            @RequestParam String departmentType, @RequestParam long startTime,
            @RequestParam long endTime) {
        return new JsonResponse(examRecordService.queryDepartmentScoreReport(departmentName,
                departmentType, startTime,
                endTime));
    }


    @ResponseBody
    @ApiOperation(value = "根据部门id,要点id,查询考核记录")
    @RequestMapping(value = "/query_exam_record_by_department_id_and_detail_id", method = RequestMethod.GET)
    public JsonResponse queryExamRecordByDepartmentIdAndDetailId(@RequestParam String departmentId,
            @RequestParam String detailId) {
        return new JsonResponse(
                examRecordService.queryExamRecordByDepartmentIdAndDetailId(departmentId, detailId));
    }


    @ApiOperation(value = "下载考核附件")
    @RequestMapping(value = "/down_load_addition", method = RequestMethod.GET)
    @ApiImplicitParam(value = "1(数据库自增主键)", name = "additionPId", required = true, dataType = "String", paramType = "query")
    public ResponseEntity<byte[]> downloadAddition(@RequestParam Integer additionPId)
            throws Exception {
        ExamRecordAdditionVo vo = examRecordService.queryAdditionById(additionPId);
        File file = new File(baseSavePath + "/" + vo.getAdditionLocation());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder
                .encode(file.getName(), "UTF-8"));
        HttpStatus statusCode = HttpStatus.OK;
        return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, statusCode);
    }


}
