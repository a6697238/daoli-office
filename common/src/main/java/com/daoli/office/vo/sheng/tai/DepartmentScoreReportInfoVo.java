package com.daoli.office.vo.sheng.tai;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class DepartmentScoreReportInfoVo {

    private String indexName;

    private float indexScore;

    private float indexTotalScore;

    private int indexScoredCount;

    private int indexTotalCount;

}
