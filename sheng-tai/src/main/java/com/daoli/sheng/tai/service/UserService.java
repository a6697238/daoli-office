package com.daoli.sheng.tai.service;

import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.IN_VALID;
import static com.daoli.office.vo.sheng.tai.constant.ShengTaiDBconstant.VALID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.daoli.office.vo.sheng.tai.DianziXinxiVo;
import com.daoli.office.vo.sheng.tai.UserVo;
import com.daoli.sheng.tai.HttpUtils.PostTool;
import com.daoli.sheng.tai.entity.UserEntity;
import com.daoli.sheng.tai.mapper.UserEntityMapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by wanglining on 2019/10/3.
 */
@Service
public class UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Value("${daoli.baseSavePath}")
    private String baseSavePath;

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
        userEntity.setDianZiXinXi(JSON.toJSONString(
                DianziXinxiVo.builder().welcomeAudioPath(genWelcomeAudio(userVo.getUserName()))
                        .build()));
        userEntity.setValid(IN_VALID);
        userEntityMapper.insertSelective(userEntity);
        return userEntity;
    }


    private String genWelcomeAudio(String userName) {
        Map<String, Object> argMap = Maps.newHashMap();
        argMap.put("user_name", userName);
        String resp = PostTool.post("http://localhost:8082/gen_welcome_audio?", argMap);
        JSONObject jo = JSON.parseObject(resp);
        return jo.getString("welcome_audio");
    }


    public boolean addUserFace(String userPid, String userName,
            MultipartFile multipartFile) {
        UserEntity userEntity = userEntityMapper.selectByPrimaryKey(Integer.valueOf(userPid));
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pid", userPid);
        paramsMap.put("user_id", userEntity.getUserId());
        paramsMap.put("user_name", userName);
        String addFaceRespContent = PostTool
                .postImage("http://localhost:8082/add_face", paramsMap, multipartFile);
        JSONObject json = JSON.parseObject(addFaceRespContent);
        boolean addFaceResult = json.getBoolean("res");
        if (addFaceResult) {
            String dianZiInfo = json.getString("content");
            DianziXinxiVo vo = JSON.parseObject(dianZiInfo, DianziXinxiVo.class);
            vo.setWelcomeAudioPath(
                    JSON.parseObject(userEntity.getDianZiXinXi(), DianziXinxiVo.class)
                            .getWelcomeAudioPath());
            userEntity.setDianZiXinXi(JSON.toJSONString(vo));
            userEntityMapper.updateByPrimaryKeySelective(userEntity);
            return true;
        } else {
            return false;
        }
    }

    public UserEntity queryUserByPid(Integer userPid){
        return userEntityMapper.selectByPrimaryKey(userPid);
    }


    public Map<String, Object> userLoginByFace(MultipartFile multipartFile) {
        Map<String, Object> resMap = Maps.newHashMap();
        String verifyFaceResp = PostTool
                .postImage("http://localhost:8082/verify_video_picture", new HashMap<>(),
                        multipartFile);
        resMap.put("verifyRes", false);
        JSONObject json = JSON.parseObject(verifyFaceResp);
        if ("true".equals(json.get("login_result"))) {
            String userId = (String) json.get("user_id");
            UserEntity userEntity = userEntityMapper.queryUserByUserId(userId);
            resMap.put("verifyRes", true);
            resMap.put("userPid", userEntity.getId());
        }
        return resMap;
    }
}
