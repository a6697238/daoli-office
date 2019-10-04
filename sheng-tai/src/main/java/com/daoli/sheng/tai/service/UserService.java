package com.daoli.sheng.tai.service;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.UserVo;
import com.daoli.sheng.tai.HttpUtils.PostTool;
import com.daoli.sheng.tai.entity.UserEntity;
import com.daoli.sheng.tai.mapper.UserEntityMapper;
import com.google.common.collect.Maps;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
        resMap.put("pid", userEntityFromDb.getId());
        resMap.put("user_name",userEntityFromDb.getUserName());

        //String resp = PostTool.post("http://localhost:8082/gen_welcome_audio?", arg_map);
//        if ( resp.contains("true") ){
//           resMap.put("gen_audio", "true");
//        } else {
//            resMap.put("gen_audio", "false");
//        }
        return resMap;
    }

    public boolean isUserHasFaceInfo(Integer pid){
       UserEntity userEntity = userEntityMapper.selectByPrimaryKey(pid);
       if(userEntity == null) {
           return false;
       }
       String dianZiInfo = userEntity.getDianZiXinXi();
       if(dianZiInfo == null) {
           return false;
       }
       JSONObject jsonObject = new JSONObject(dianZiInfo);
       String faceMainPic = jsonObject.getString("faceMainPic");
       String  faceDescription = jsonObject.getString("face_description");
       if (StringUtils.isNotEmpty(faceMainPic) && StringUtils.isNotEmpty(faceDescription)){
           return true;
       }
       return false;
    }

    public Map<String,Object>  addUserFace(String pid, String userName, MultipartFile multipartFile ){

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("pid",pid);
        paramsMap.put("user_name",userName);
        System.out.println("arg from web: " + paramsMap.toString());
        String addFaceRespContent = PostTool.postImage("http://localhost:8082/add_face",paramsMap, multipartFile);

        JSONObject json = new JSONObject(addFaceRespContent);
        boolean addFaceResult= json.getBoolean("res");
        if (true == addFaceResult){
            String dianZiInfo = json.getString("content");
            //System.out.println(dianZiInfo);

            return  updateUserDianZiInfo(Integer.valueOf(pid),dianZiInfo );
        }
       return new HashMap<>();
    }

    public  Map<String,Object> updateUserDianZiInfo(Integer pid ,String dianZiInfo){
        Map<String, Object> resMap = Maps.newHashMap();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(pid);
        userEntity.setDianZiXinXi(dianZiInfo);
        int res = userEntityMapper.updateByPrimaryKeySelective(userEntity);
        if (res != 0){
            resMap.put(userEntity.getUserId(), true);
        } else {
            resMap.put(userEntity.getUserId(), false);
        }
        return  resMap;
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
