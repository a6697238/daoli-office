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
        Map<String, Object> map1 = Maps.newHashMap();
        map1.put("departmentId", "asdd");
        map1.put("departmentType", "区直单位");
        map1.put("departmentName", "区委办");
        map1.put("score", "11");
        map1.put("totalScore", "110");
        map1.put("uploadDetailCnt", "43");
        map1.put("totalDetailCnt", "51");


        Map<String, Object> map2 = Maps.newHashMap();
        map2.put("departmentId", "asd");
        map2.put("departmentType", "区直单位");
        map2.put("departmentName", "区直机关工委");
        map2.put("score", "70");
        map2.put("totalScore", "110");
        map2.put("uploadDetailCnt", "43");
        map2.put("totalDetailCnt", "51");


        Map<String, Object> map3 = Maps.newHashMap();
        map3.put("departmentId", "asd");
        map3.put("departmentType", "区直单位");
        map3.put("departmentName", "区科协");
        map3.put("score", "90");
        map3.put("totalScore", "110");
        map3.put("uploadDetailCnt", "23");
        map3.put("totalDetailCnt", "51");

        Map<String, Object> map4 = Maps.newHashMap();
        map4.put("departmentId", "asd");
        map4.put("departmentType", "街道单位");
        map4.put("departmentName", "新华街道");
        map4.put("score", "90");
        map4.put("totalScore", "120");
        map4.put("uploadDetailCnt", "13");
        map4.put("totalDetailCnt", "61");


        Map<String, Object> map5 = Maps.newHashMap();
        map5.put("departmentId", "asd");
        map5.put("departmentType", "乡镇单位");
        map5.put("departmentName", "榆树镇");
        map5.put("score", "78");
        map5.put("totalScore", "110");
        map5.put("uploadDetailCnt", "56");
        map5.put("totalDetailCnt", "70");


        res.add(map1);
        res.add(map2);
        res.add(map3);
        res.add(map4);
        res.add(map5);

        return res;
    }

    public Map<String, Object> queryTypeRadar(long startTime, long endTime) {
        Map<String, Object> res = Maps.newHashMap();
        List<Map<String, Object>> labelList = Lists.newArrayList();
        List<Map<String, Object>> dataList = Lists.newArrayList();

        Map<String, Object> fenlei1 = Maps.newHashMap();
        fenlei1.put("name", "树立四个意识");
        fenlei1.put("max", "100");
        Map<String, Object> fenlei2 = Maps.newHashMap();
        fenlei2.put("name", "领导班子凝聚力");
        fenlei2.put("max", "100");
        Map<String, Object> fenlei3 = Maps.newHashMap();
        fenlei3.put("name", "选人用人");
        fenlei3.put("max", "100");
        Map<String, Object> fenlei4 = Maps.newHashMap();
        fenlei4.put("name", "政风反腐");
        fenlei4.put("max", "100");
        Map<String, Object> fenlei5 = Maps.newHashMap();
        fenlei5.put("name", "问卷调查");
        fenlei5.put("max", "100");

        labelList.add(fenlei1);
        labelList.add(fenlei2);
        labelList.add(fenlei3);
        labelList.add(fenlei4);
        labelList.add(fenlei5);


        Map<String, Object> xaingzhen = Maps.newHashMap();
        List<String> xiangzhenScore = Arrays.asList("90", "95", "80", "85", "75");
        xaingzhen.put("name", "乡镇单位");
        xaingzhen.put("value", xiangzhenScore);

        Map<String, Object> quzhi = Maps.newHashMap();
        List<String> quzhiScore = Arrays.asList("90", "90", "70", "95", "75");
        quzhi.put("name", "区直单位");
        quzhi.put("value", quzhiScore);

        Map<String, Object> jiedao = Maps.newHashMap();
        List<String> jiedaoScore = Arrays.asList("80", "95", "80", "95", "75");
        jiedao.put("name", "街道单位");
        jiedao.put("value", jiedaoScore);

        dataList.add(xaingzhen);
        dataList.add(quzhi);
        dataList.add(jiedao);

        res.put("label", labelList);
        res.put("data", dataList);
        return res;
    }

    public List<Map<String, Object>> queryRecordRate(long startTime, long endTime) {
        Map<String, Object> res1 = Maps.newHashMap();
        res1.put("name", "树立四个意识");
        res1.put("value", "40");

        Map<String, Object> res2 = Maps.newHashMap();
        res2.put("name", "领导班子凝聚力");
        res2.put("value", "60");

        Map<String, Object> res3 = Maps.newHashMap();
        res3.put("name", "选人用人");
        res3.put("value", "79");

        Map<String, Object> res4 = Maps.newHashMap();
        res4.put("name", "政风反腐");
        res4.put("value", "90");


        Map<String, Object> res5 = Maps.newHashMap();
        res5.put("name", "问卷调查");
        res5.put("value", "90");



        List<Map<String, Object>> resList = Lists.newArrayList();
        resList.add(res1);
        resList.add(res2);
        resList.add(res3);
        resList.add(res4);
        resList.add(res5);

        return resList;
    }



    public Map<String, Object> queryDepartmentScore(long startTime, long endTime) {
        List<String> labelLsit = Arrays.asList("中央大街", "顾乡开发办", "区人民法院", "区人民检察院", "区人民检察院","兆麟街道","尚志街道","通江街道","斯大林街道","太平镇");
        List<Object> dataList = Lists.newArrayList();





        List<String> fenleiscore1 = Arrays.asList("70", "90", "80", "70", "90","70", "90", "80", "30", "90");
        Map<String, Object> res1 = Maps.newHashMap();
        res1.put("name", "树立四个意识");
        res1.put("stack", "score");
        res1.put("data", fenleiscore1);

        List<String> fenleiscore2 = Arrays.asList("40", "40", "90", "30", "30","40", "90", "80", "20", "90");
        Map<String, Object> res2 = Maps.newHashMap();
        res2.put("name", "领导班子凝聚力");
        res2.put("stack", "score");
        res2.put("data", fenleiscore2);

        List<String> fenleiscore3 = Arrays.asList("20", "30", "30", "20", "70","70", "40", "80", "30", "90");
        Map<String, Object> res3 = Maps.newHashMap();
        res3.put("name", "选人用人");
        res3.put("value", "79");
        res3.put("stack", "score");
        res3.put("data", fenleiscore3);


        List<String> fenleiscore4 = Arrays.asList("90", "60", "50", "60", "80","40", "90", "40", "70", "30");
        Map<String, Object> res4 = Maps.newHashMap();
        res4.put("name", "政风反腐");
        res4.put("value", "90");
        res4.put("stack", "score");
        res4.put("data", fenleiscore4);


        List<String> fenleiscore5 = Arrays.asList("20", "40", "40", "80", "60","70", "90", "80", "40", "90");
        Map<String, Object> res5 = Maps.newHashMap();
        res5.put("name", "问卷调查");
        res5.put("value", "90");
        res5.put("stack", "score");
        res5.put("data", fenleiscore5);




        dataList.add(res1);
        dataList.add(res2);
        dataList.add(res3);
        dataList.add(res4);
        dataList.add(res5);


        Map<String,Object> res = Maps.newHashMap();
        res.put("label",labelLsit);
        res.put("data",dataList);

        return res;
    }

}
