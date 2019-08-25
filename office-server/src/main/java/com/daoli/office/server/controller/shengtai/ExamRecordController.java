package com.daoli.office.server.controller.shengtai;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.daoli.office.server.controller.BaseController;
import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ExamRecordAdditionVo;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.sheng.tai.service.ExamRecordService;
import com.daoli.utils.FileUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public JsonResponse uploadExamRecord(@RequestBody ShengtaiExamRecordVo req,
            @RequestParam List<Integer> additionId) {
        examRecordService.uploadRecord(req,additionId);
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
                Map<String, String> resultMap = Maps.newHashMap();
                String relativeFile = "/" + userId + "/" + file.getOriginalFilename();
                String relativePath = "/" + userId;
                FileUtils.writeFile(file.getOriginalFilename(), baseSavePath + relativePath,
                        file.getBytes());
                resultMap.put("fileName", file.getOriginalFilename());
                resultMap.put("filePath", relativeFile);
                ExamRecordAdditionVo vo = ExamRecordAdditionVo.builder()
                        .additionLocation(relativeFile)
                        .additionName(file.getOriginalFilename())
                        .createUid(userId)
                        .additionId(UUID.randomUUID().toString())
                        .build();
                examRecordService.uploadeAddition(vo);
                resList.add(vo);
            }
        }
        return new JsonResponse(resList);
    }


    @ResponseBody
    @ApiOperation(value = "查询一条考核记录")
    @RequestMapping(value = "/query_exam_record", method = RequestMethod.GET)
    public JsonResponse queryExamRecord(@RequestParam Integer recordId) {
        return new JsonResponse(examRecordService.queryExamRecord(recordId));
    }

    @ResponseBody
    @ApiOperation(value = "查询一条考核记录")
    @RequestMapping(value = "/query_exam_record", method = RequestMethod.GET)
    public JsonResponse queryRecordAddition(@RequestParam String examRecordId) {
        return new JsonResponse(examRecordService.queryRecordAdditionList(examRecordId));
    }


//    public List<ExamRecordAdditionVo> queryRecordAdditionList(String examRecordId) {


    }
