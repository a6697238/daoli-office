package com.daoli.sheng.tai.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.daoli.sheng.tai.mapper.ShengTaiReportEntityMapper;
import com.daoli.sheng.tai.mapper.ShengtaiExamRecordEntityMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AUTO-GENERATED: houlu @ 2019/9/28 上午9:31
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ShengTaiReportService {


    @Autowired
    private ShengTaiReportEntityMapper reportEntityMapper;

    public Map<String, Object> queryTitle(long startTime, long endTime) {
        Map<String, Object> report = Maps.newHashMap();
        report.put("uploadDepartmentCnt", 10);
        report.put("departmentCnt", 20);
        report.put("scoredRecordCnt", 140);
        report.put("totalRecordCnt", 160);
        report.put("finishDepartmentCnt", 4);
        report.put("daifabuExamCnt", 10);
        report.put("jinxingzhongCnt", 10);
        report.put("jieshuCnt", 10);
        return report;
    }


    public List<Map<String, Object>> queryRank(long startTime, long endTime) {
        List<Map<String, Object>> res = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        map.put("departmentId", "asd");
        map.put("departmentType", "asd");
        map.put("departmentName", "asd");
        map.put("score", "11");
        map.put("totalScore", "110");
        map.put("uploadDetailCnt", "43");
        map.put("totalDetailCnt", "51");
        res.add(map);
        return res;
//        return report;
    }

    public Map<String, Object> queryTypeRadar() {
        Map<String, Object> res = Maps.newHashMap();
        List<Map<String, Object>> labelList = Lists.newArrayList();
        List<Map<String, Object>> dataList = Lists.newArrayList();

        Map<String, Object> fenlei1 = Maps.newHashMap();
        fenlei1.put("name", "fenlei1");
        fenlei1.put("max", "100");
        Map<String, Object> fenlei2 = Maps.newHashMap();
        fenlei2.put("name", "fenlei2");
        fenlei2.put("max", "100");
        Map<String, Object> fenlei3 = Maps.newHashMap();
        fenlei3.put("name", "fenlei3");
        fenlei3.put("max", "100");
        Map<String, Object> fenlei4 = Maps.newHashMap();
        fenlei4.put("name", "fenlei4");
        fenlei4.put("max", "100");
        labelList.add(fenlei1);
        labelList.add(fenlei2);
        labelList.add(fenlei3);
        labelList.add(fenlei4);

        Map<String, Object> xaingzhen = Maps.newHashMap();
        List<String> xiangzhenScore = Arrays.asList("90", "95", "80", "85", "75");
        xaingzhen.put("name", "xiangzhen");
        xaingzhen.put("value", xiangzhenScore);

        Map<String, Object> quzhi = Maps.newHashMap();
        List<String> quzhiScore = Arrays.asList("90", "90", "70", "95", "75");
        quzhi.put("name", "quzhiScore");
        quzhi.put("value", quzhiScore);

        Map<String, Object> jiedao = Maps.newHashMap();
        List<String> jiedaoScore = Arrays.asList("80", "95", "80", "95", "75");
        jiedao.put("name", "jiedao");
        jiedao.put("value", jiedaoScore);

        dataList.add(xaingzhen);
        dataList.add(quzhi);
        dataList.add(jiedao);

        res.put("label", labelList);
        res.put("data", dataList);
        return res;
    }


}
