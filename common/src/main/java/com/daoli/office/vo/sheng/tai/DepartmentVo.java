package com.daoli.office.vo.sheng.tai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by wanglining on 2019/8/31.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVo {
    private Integer id;

    private String departmentId;

    private String departmentName;

    private Date modifyTime;

    private Date createTime;

}
