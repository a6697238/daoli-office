package com.daoli.office.vo.sheng.tai;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wanglining on 2019/8/31.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRecordVo {

    private String action;

    private Integer recordPid;

}
