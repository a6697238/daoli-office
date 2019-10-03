package com.daoli.office.server.controller.shengtai;

import com.daoli.office.vo.JsonResponse;
import com.daoli.office.vo.sheng.tai.UserVo;
import com.daoli.sheng.tai.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "用户登录, login_name/login_password ")
    @RequestMapping(value = "/user_login", method = RequestMethod.POST)
    public JsonResponse verifyUser(@RequestBody UserVo userVo){
        return new JsonResponse(userService.verifyUser(userVo));
    }

}
