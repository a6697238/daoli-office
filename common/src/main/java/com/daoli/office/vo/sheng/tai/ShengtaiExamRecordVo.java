package com.daoli.office.vo.sheng.tai;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * AUTO-GENERATED: houlu @ 2019/8/20 下午9:01
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class ShengtaiExamRecordVo {

    @ApiModelProperty(value = "id", required = false)
    private Integer id;

    @ApiModelProperty(value = "examRecordId", required = false)
    private String examRecordId;

    @ApiModelProperty(value = "examIndexId", required = false)
    private String examIndexId;

    @ApiModelProperty(value = "examIndexDesc", required = false)
    private String examIndexDesc;

    @ApiModelProperty(value = "examDetailId", required = false)
    private String examDetailId;

    @ApiModelProperty(value = "examDetailDesc", required = false)
    private String examDetailDesc;

    @ApiModelProperty(value = "examScore", required = false)
    private Integer examScore;

    @ApiModelProperty(value = "departmentId", required = false)
    private String departmentId;

    @ApiModelProperty(value = "recordName", required = false)
    private String recordName;

    @ApiModelProperty(value = "recordAbstract", required = false)
    private String recordAbstract;

    @ApiModelProperty(value = "recordAdditionName", required = false, example = "不用填")
    private String recordAdditionName;

    @ApiModelProperty(value = "recordAdditionLocation", required = false)
    private String recordAdditionLocation;

    @ApiModelProperty(value = "recordMasterName", required = false)
    private String recordMasterName;

    @ApiModelProperty(value = "recordGroupName", required = false)
    private String recordGroupName;

    @ApiModelProperty(value = "recordStatus", required = false)
    private String recordStatus;

    @ApiModelProperty(value = "createUid", required = false)
    private String createUid;

}
