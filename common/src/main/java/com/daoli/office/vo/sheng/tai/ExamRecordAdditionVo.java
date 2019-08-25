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

    @ApiModelProperty(value = "", required = false)
    private Integer id;

    @ApiModelProperty(value = "", required = false)
    private String additionId;

    @ApiModelProperty(value = "附件名称", required = false)
    private String additionName;

    @ApiModelProperty(value = "附件相对路径", required = false)
    private String additionLocation;

    @ApiModelProperty(value = "创建者id", required = false)
    private String createUid;

    @ApiModelProperty(value = "修改时间", required = false)
    private Date modifyTime;

    @ApiModelProperty(value = "上传时间", required = false)
    private Date createTime;


}
