package com.daoli.sheng.tai.mapper;

import com.daoli.sheng.tai.entity.UserEntity;

public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);
    UserEntity selectByUserId(String userId);
    UserEntity selectByUserName(String userName);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKeyWithBLOBs(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}