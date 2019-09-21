package com.daoli.sheng.tai.mapper;

import com.daoli.sheng.tai.entity.ShengTaiExamEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShengTaiExamEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ShengTaiExamEntity record);

    int insertSelective(ShengTaiExamEntity record);

    ShengTaiExamEntity selectByPrimaryKey(Integer id);
    ShengTaiExamEntity queryByExamId(String examId);

    int updateByPrimaryKeySelective(ShengTaiExamEntity record);

    int updateByPrimaryKey(ShengTaiExamEntity record);

    List<ShengTaiExamEntity> queryExamsByCondition(ShengTaiExamEntity record);

    List<ShengTaiExamEntity> queryExamsByFuzzyCondition(ShengTaiExamEntity record);
    List<ShengTaiExamEntity> queryAllExams();

    int updateZhiBiaoStatusByFenLeiPid(ShengTaiExamEntity record);
    int updateYaoDianStatusByFenLeiPid(ShengTaiExamEntity record);

    int updateZhiBiaoScoreByYaoDianParentId(Integer yaoDianParentId);
    int updateFenLeiScoreByYaoDianParentId(Integer yaoDianParentId);
    //ShengTaiExamEntity queryByExamId(String examId);

}