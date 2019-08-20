package com.daoli.office.server.controller.sheng.tai.custom;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.ShengtaiExamRecordVo;
import com.daoli.sheng.tai.service.ExamRecordService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
public class ExamRecordController {

    @Autowired
    private ExamRecordService examRecordService;

    @ResponseBody
    @ApiOperation(value = "上传一条考核记录")
    @RequestMapping(value = "/upload_exam_record", method = RequestMethod.POST)
    public JsonResponse uploadExamRecord(@RequestBody ShengtaiExamRecordVo req) {
        examRecordService.uploadRecord(req);
        return new JsonResponse();
    }

}
