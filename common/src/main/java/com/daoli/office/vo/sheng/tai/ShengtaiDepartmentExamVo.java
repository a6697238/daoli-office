package com.daoli.office.vo.sheng.tai;

import lombok.Data;

import java.util.Date;

/**
 * Created by wanglining on 2019/8/24.
 */

@Data
public class ShengtaiDepartmentExamVo {

    private Integer id;

    private String departmentId;

    private String examId;

    private Date modifyTime;

    private Date createTime;

    //private Byte valid;
}
