package com.daoli.ze.ren.mapper;

import com.daoli.ze.ren.entity.ZerenUserEntity;
import org.springframework.stereotype.Component;

@Component
public interface ZerenUserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZerenUserEntity record);

    int insertSelective(ZerenUserEntity record);

    ZerenUserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZerenUserEntity record);

    int updateByPrimaryKey(ZerenUserEntity record);
}