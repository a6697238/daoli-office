package com.daoli.office.server.controller.shengtai;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.UserVo;
import com.daoli.sheng.tai.HttpUtils.PostTool;
import com.daoli.sheng.tai.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * Created by wanglining on 2019/10/3.
 */

@RestController(value = "用户注册和登录")
@RequestMapping(value = "/api/web/user/register_login")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @ApiOperation(value = "用户注册，user_name/user_age/user_sex/department_id/login_name/login_password 是必须的")
    @RequestMapping(value = "/user_register", method = RequestMethod.POST)
    public JsonResponse registerUser(@RequestBody UserVo userVo){
        return new JsonResponse(userService.registerUser(userVo));
    }

    @ResponseBody
    @ApiOperation(value = "pid")
    @RequestMapping(value = "/query_user_has_face_info", method = RequestMethod.GET)
    public JsonResponse registerUser(Integer pid){
        return new JsonResponse(userService.isUserHasFaceInfo(pid));
    }

    @ResponseBody
    @ApiOperation(value = "用户人脸上传接口，pid")
    @RequestMapping(value = "/user_face_upload", method = RequestMethod.POST)
    public JsonResponse uploadUserFace( HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("'Access-Control-Allow-Headers","x-requested-with");
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, DELETE");

        String pid = request.getParameter("pid");
        String userName = request.getParameter("user_name");
        String writePicToDisk = request.getParameter("write_pic_to_disk");
        MultipartHttpServletRequest mul = (MultipartHttpServletRequest)request;
        MultipartFile multipartFile = mul.getFile("image_content");

        return new JsonResponse(userService.addUserFace(pid, userName, multipartFile));
    }

    @ResponseBody
    @ApiOperation(value = "用户登录, 人脸登录")
    @RequestMapping(value = "/user_login_by_face", method = RequestMethod.POST)
    public JsonResponse verifyUserByFace(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("'Access-Control-Allow-Headers","x-requested-with");
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, DELETE");

        MultipartHttpServletRequest mul = (MultipartHttpServletRequest)request;
        MultipartFile multipartFile = mul.getFile("image_content");

        String verifyFaceResp = PostTool.postImage("http://localhost:8082/verify_video_picture",new HashMap<>(), multipartFile);
        return new JsonResponse(verifyFaceResp);
    }

    @ResponseBody
    @ApiOperation(value = "用户登录, 密码登录")
    @RequestMapping(value = "/user_login_by_pwd", method = RequestMethod.POST)
    public JsonResponse verifyUserByPwd(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("'Access-Control-Allow-Headers","x-requested-with");
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, DELETE");


        return new JsonResponse(true);
    }
}
