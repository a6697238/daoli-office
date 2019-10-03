package com.daoli.sheng.tai.service;

import com.daoli.office.vo.sheng.tai.UserVo;
import com.daoli.sheng.tai.HttpUtils.PostTool;
import com.daoli.sheng.tai.entity.UserEntity;
import com.daoli.sheng.tai.mapper.UserEntityMapper;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.VALID;

/**
 * Created by wanglining on 2019/10/3.
 */
@Service
public class UserService {
    @Autowired
    private UserEntityMapper userEntityMapper;

    public Map<String,Object> registerUser(UserVo userVo){
        Map<String, Object> resMap = Maps.newHashMap();
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo, userEntity);
        String userId = UUID.randomUUID().toString();
        userEntity.setUserId(userId);
        userEntity.setModifyTime(new Date());
        userEntity.setCreateTime(new Date());
        userEntity.setDianZiXinXi("{}");
        userEntity.setValid(VALID);
        int res = userEntityMapper.insertSelective(userEntity);
        if (res != 0 )
        {
            resMap.put(userEntity.getUserName(), true);
        }else {
            resMap.put(userEntity.getUserName(), false);
            return resMap;
        }

        UserEntity userEntityFromDb = userEntityMapper.selectByUserId(userId);
        Map<String,Object> arg_map = new HashMap<>();
        arg_map.put("pid", userEntityFromDb.getId());
        arg_map.put("user_name",userEntityFromDb.getUserName());
        String resp = PostTool.post("http://localhost:8082/gen_welcome_audio?", arg_map);
        if ( resp.contains("true") ){
           resMap.put("gen_audio", "true");
        } else {
            resMap.put("gen_audio", "false");
        }
        return resMap;
    }

    public Map<String,Object> verifyUser(UserVo userVo){
        Map<String, Object> resMap = Maps.newHashMap();
        UserEntity userEntity = new UserEntity();
        userEntity = userEntityMapper.selectByUserName(userVo.getUserName());
        if (userVo.getLoginPassword().equals(userEntity.getLoginPassword()) )
        {
            resMap.put(userEntity.getUserId(), true);
        }else {
            resMap.put(userEntity.getUserId(), false);
        }
        return resMap;
    }
}
