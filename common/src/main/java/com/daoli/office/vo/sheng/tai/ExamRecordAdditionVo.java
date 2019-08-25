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
@AllArgsConstructor
@NoArgsConstructor
public class ExamRecordAdditionVo {

    @ApiModelProperty(value = "id", required = false)
    private Integer id;

    @ApiModelProperty(value = "additionId", required = false)
    private String additionId;

    @ApiModelProperty(value = "additionName", required = false)
    private String additionName;

    @ApiModelProperty(value = "additionLocation", required = false)
    private String additionLocation;

    @ApiModelProperty(value = "createUid", required = false)
    private String createUid;

    private Date modifyTime;

    private Date createTime;


}
