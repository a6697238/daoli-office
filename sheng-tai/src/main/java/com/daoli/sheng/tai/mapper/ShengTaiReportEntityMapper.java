package com.daoli.sheng.tai.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.daoli.office.vo.sheng.tai.ShengtaiExamVo;
import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ShengTaiReportEntityMapper {


    List<Map<String, Object>> querytitleReport(@Param(value = "startTime") Date startTime,
            @Param(value = "endTime") Date endTime);
}