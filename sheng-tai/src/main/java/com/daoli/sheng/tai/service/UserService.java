package com.daoli.sheng.tai.service;

import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.IN_VALID;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.VALID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.daoli.office.vo.sheng.tai.UserVo;
import com.daoli.sheng.tai.HttpUtils.PostTool;
import com.daoli.sheng.tai.entity.UserEntity;
import com.daoli.sheng.tai.mapper.UserEntityMapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by wanglining on 2019/10/3.
 */
@Service
public class UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    public UserEntity registerBaseInfo(UserVo userVo) {
        UserEntity userEntity = userEntityMapper.queryUserByLoginName(userVo.getLoginName());
        if (null != userEntity) {
            throw new RuntimeException("该登录名已经被注册");
        }
        userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo, userEntity);
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setModifyTime(new Date());
        userEntity.setCreateTime(new Date());
        userEntity.setDianZiXinXi("{}");
        userEntity.setValid(IN_VALID);
        userEntityMapper.insertSelective(userEntity);
        return userEntity;
    }

    public String genWelecomAudio(String user_name){
        Map<String, Object> argMap = Maps.newHashMap();
        argMap.put("user_name", user_name);
        String resp = PostTool.post("http://localhost:8082/gen_welcome_audio?",argMap);
        JSONObject jo =  JSON.parseObject(resp);
        String msg = jo.getString("msg");
        if ("true".equals(msg)){
            return jo.getString("welcom_audio");
        }
        return "";
    }

    public boolean isUserHasFaceInfo(Integer pid) {
        UserEntity userEntity = userEntityMapper.selectByPrimaryKey(pid);
        if (userEntity == null) {
            return false;
        }
        String dianZiInfo = userEntity.getDianZiXinXi();
        if (dianZiInfo == null) {
            return false;
        }
        JSONObject jsonObject = JSON.parseObject(dianZiInfo);
        String faceMainPic = jsonObject.getString("faceMainPic");
        String faceDescription = jsonObject.getString("face_description");
        return StringUtils.isNotEmpty(faceMainPic) && StringUtils.isNotEmpty(faceDescription);
    }

    public void addUserFace(String pid, String userName,
            MultipartFile multipartFile) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pid", pid);
        paramsMap.put("user_name", userName);
        String addFaceRespContent = PostTool
                .postImage("http://localhost:8082/add_face", paramsMap, multipartFile);

        JSONObject json = JSON.parseObject(addFaceRespContent);
        boolean addFaceResult = json.getBoolean("res");
        if (addFaceResult) {
            String dianZiInfo = json.getString("content");
            UserEntity userEntity = userEntityMapper.selectByPrimaryKey(Integer.valueOf(pid));
            if (null == userEntity) {
                userEntity = new UserEntity();
                userEntity.setUserName(userName);
                userEntity.setDianZiXinXi(dianZiInfo);
                userEntityMapper.insertSelective(userEntity);
            } else {
                userEntity.setDianZiXinXi(dianZiInfo);
                userEntityMapper.updateByPrimaryKeySelective(userEntity);
            }

        }
    }


    public Map<String, Object> verifyUser(UserVo userVo) {
        Map<String, Object> resMap = Maps.newHashMap();
        UserEntity userEntity = new UserEntity();
        userEntity = userEntityMapper.selectByUserName(userVo.getUserName());
        if (userVo.getLoginPassword().equals(userEntity.getLoginPassword())) {
            resMap.put(userEntity.getUserId(), true);
        } else {
            resMap.put(userEntity.getUserId(), false);
        }
        return resMap;
    }
}
