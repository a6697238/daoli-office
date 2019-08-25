package com.daoli.office.vo.sheng.tai;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
public class ShengtaiExamRecordVo {

    @ApiModelProperty(value = "记录id", required = false)
    private Integer id;

    @ApiModelProperty(value = "记录uuid", required = false)
    private String examRecordId;

    @ApiModelProperty(value = "指标id", required = false)
    private String examIndexId;

    @ApiModelProperty(value = "指标描述", required = false)
    private String examIndexDesc;

    @ApiModelProperty(value = "要点id", required = false)
    private String examDetailId;

    @ApiModelProperty(value = "要点描述", required = false)
    private String examDetailDesc;

    @ApiModelProperty(value = "记录得分", required = false)
    private Integer examScore;

    @ApiModelProperty(value = "部门id", required = false)
    private String departmentId;

    @ApiModelProperty(value = "记录名称", required = false)
    private String recordName;

    @ApiModelProperty(value = "记录摘要", required = false)
    private String recordAbstract;

    @ApiModelProperty(value = "负责人姓名", required = false)
    private String recordMasterName;

    @ApiModelProperty(value = "相关组织人员", required = false)
    private String recordGroupName;

    @ApiModelProperty(value = "记录状态", required = false)
    private String recordStatus;

    @ApiModelProperty(value = "创建者id", required = false)
    private String createUid;

    private Date modifyTime;

    private Date createTime;

}
