package com.daoli.office.server.controller.shengtai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.DianziXinxiVo;
import com.daoli.office.vo.sheng.tai.ExamRecordAdditionVo;
import com.daoli.office.vo.sheng.tai.UserVo;
import com.daoli.sheng.tai.HttpUtils.PostTool;
import com.daoli.sheng.tai.entity.UserEntity;
import com.daoli.sheng.tai.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * Created by wanglining on 2019/10/3.
 */

@RestController(value = "用户注册和登录")
@RequestMapping(value = "/api/web/sheng_tai/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${daoli.baseSavePath}")
    private String baseSavePath;

    @ResponseBody
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register_base_info", method = RequestMethod.POST)
    public JsonResponse registerBaseInfo(@RequestBody UserVo userVo) {
        return new JsonResponse(userService.registerBaseInfo(userVo));
    }

    @ResponseBody
    @ApiOperation(value = "用户人脸上传接口，pid")
    @RequestMapping(value = "/user_face_upload", method = RequestMethod.POST)
    public JsonResponse uploadUserFace(HttpServletRequest request) {
        String userPid = request.getParameter("userPid");
        String userName = request.getParameter("userName");
        MultipartHttpServletRequest mul = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = mul.getFile("userFaceImage");
        return new JsonResponse(userService.addUserFace(userPid, userName, multipartFile));
    }

    @ResponseBody
    @ApiOperation(value = "用户登录, 人脸登录")
    @RequestMapping(value = "/user_login_by_face", method = RequestMethod.POST)
    public JsonResponse userLoginByFace(HttpServletRequest request) {

        MultipartHttpServletRequest mul = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = mul.getFile("userFaceImage");

        return new JsonResponse(userService.userLoginByFace(multipartFile));
    }

    @ResponseBody
    @ApiOperation(value = "用户登录, 密码登录")
    @RequestMapping(value = "/user_login_by_pwd", method = RequestMethod.POST)
    public JsonResponse verifyUserByPwd(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("'Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");

        return new JsonResponse(true);
    }



    @ApiOperation(value = "输出欢迎声音")
    @RequestMapping(value = "/download_welcome_audio", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadWelcomeAudio(@RequestParam Integer userPid)
            throws Exception {
        UserEntity userEntity = userService.queryUserByPid(userPid);
        File file = new File(baseSavePath + JSON.parseObject(userEntity.getDianZiXinXi(),
                DianziXinxiVo.class).getWelcomeAudioPath());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder
                .encode(file.getName(), "UTF-8"));
        HttpStatus statusCode = HttpStatus.OK;
        return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, statusCode);
    }
}
