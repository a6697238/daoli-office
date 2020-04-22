package com.daoli.office.vo.sheng.tai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by wanglining on 2019/8/24.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShengtaiDepartmentExamVo {

    private Integer id;

    private String departmentId;

    private String examId;

    private Date modifyTime;

    private Date createTime;
}
