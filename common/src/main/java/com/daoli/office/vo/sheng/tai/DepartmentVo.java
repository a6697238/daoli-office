package com.daoli.office.vo.sheng.tai;

import lombok.Data;

import java.util.Date;

/**
 * Created by wanglining on 2019/8/31.
 */

@Data
public class DepartmentVo {
    private Integer id;

    private String departmentId;

    private String departmentName;

    private Date modifyTime;

    private Date createTime;

}
