package com.daoli.office.vo.sheng.tai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by wanglining on 2019/8/22.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShengtaiExamVo {
    private Integer id;

    private String examId;

    private Integer parentExamId;

    private String examType;

    private String examName;

    private String examDesc;

    private Float examScore;

    private String examStatus;

    private String createUid;

    private Date startTime;

    private Date endTime;

    private Date modifyTime;

    private Date createTime;

    private Integer assignedNum;

    private boolean searched;

}
