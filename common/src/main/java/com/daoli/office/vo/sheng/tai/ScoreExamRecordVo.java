package com.daoli.office.vo.sheng.tai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AUTO-GENERATED: houlu @ 2019/9/22 下午11:15
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreExamRecordVo {

    private float score;

    private String detailId;

    private String departmentId;


}
