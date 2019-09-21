package com.daoli.office.vo.sheng.tai;

import java.util.Date;
import java.util.UUID;

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

    @ApiModelProperty(value = "记录id", required = false,example = "")
    private Integer id;

    @ApiModelProperty(value = "记录uuid", required = false, example = "修改时候必填，36e3694b-d754-49f0-b927-e4c576df2029")
    private String examRecordId;

    @ApiModelProperty(value = "指标业务id", required = true, example = "36e3694b-d754-49f0-b927-e4c576df2029")
    private String examIndexId;

    @ApiModelProperty(value = "指标描述", required = true, example = "xxx考核指标")
    private String examIndexDesc;

    @ApiModelProperty(value = "要点业务id", required = true, example = "36e3694b-d754-49f0-b927-e4c576df2029")
    private String examDetailId;

    @ApiModelProperty(value = "要点描述", required = true, example = "xxx要点")
    private String examDetailDesc;

    @ApiModelProperty(value = "记录得分", required = false,example = "2.0")
    private Float examScore;

    @ApiModelProperty(value = "部门id", required = true, example = "36e3694b-d754-49f0-b927-e4c576df2029")
    private String departmentId;

    @ApiModelProperty(value = "记录名称", required = true,example = "xxx考核记录")
    private String recordName;

    @ApiModelProperty(value = "记录摘要", required = false,example = "xxx考核记录摘要")
    private String recordAbstract;

    @ApiModelProperty(value = "负责人姓名", required = true,example = "xxxName")
    private String recordMasterName;

    @ApiModelProperty(value = "相关组织人员", required = true,example = "xxxName")
    private String recordGroupName;

    @ApiModelProperty(value = "记录状态", required = false)
    private String recordStatus;

    @ApiModelProperty(value = "创建者id", required = true)
    private String createUid;

    private Date modifyTime;

    private Date createTime;

    private Date startTime;

    private Date endTime;



}
