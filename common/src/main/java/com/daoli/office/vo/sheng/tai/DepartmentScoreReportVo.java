package com.daoli.office.vo.sheng.tai;

import java.util.Date;

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

    private Integer scoredRecord;

    private Integer totalRecord;

    private Date totalScore;


}
