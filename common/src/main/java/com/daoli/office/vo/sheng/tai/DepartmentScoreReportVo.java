package com.daoli.office.vo.sheng.tai;

import java.util.Date;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AUTO-GENERATED: houlu @ 2019/8/20 下午9:01
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentScoreReportVo {

    private Integer departmentPid;

    private String departmentId;

    private String departmentName;

    @ApiModelProperty(value = "打分记录数", required = false)
    private int scoredCount;

    @ApiModelProperty(value = "上传记录数", required = false)
    private int uploadCount;

    @ApiModelProperty(value = "打分完成率", required = false)
    private float scoredCompleteRate;


    @ApiModelProperty(value = "真实得分", required = false)
    private float realScore;

    @ApiModelProperty(value = "上传应得分", required = false)
    private float uploadTargetScore;

    @ApiModelProperty(value = "上传得分率", required = false)
    private float uploadTargetScoreRate;


    @ApiModelProperty(value = "考核应得分", required = false)
    private float examTargetScore;

    @ApiModelProperty(value = "考核得分率", required = false)
    private float examTargetScoreRate;

    @ApiModelProperty(value = "已上传考核数", required = false)
    private int uploadExamCount;

    @ApiModelProperty(value = "应上传考核数", required = false)
    private int examTargetCount;

    @ApiModelProperty(value = "上传完成率", required = false)
    private float uploadTargetCompleteRate;


}
