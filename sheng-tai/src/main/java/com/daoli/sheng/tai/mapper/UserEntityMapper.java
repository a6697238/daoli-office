package com.daoli.sheng.tai.mapper;

import com.daoli.sheng.tai.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}