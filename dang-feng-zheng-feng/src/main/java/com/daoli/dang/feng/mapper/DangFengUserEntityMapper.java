package com.daoli.dang.feng.mapper;

import com.daoli.dang.feng.entity.DangFengUserEntity;
import org.springframework.stereotype.Component;

@Component
public interface DangFengUserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DangFengUserEntity record);

    int insertSelective(DangFengUserEntity record);

    DangFengUserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DangFengUserEntity record);

    int updateByPrimaryKey(DangFengUserEntity record);
}