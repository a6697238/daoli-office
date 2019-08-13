package com.daoli.office.server.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.daoli.dang.feng.entity.DangFengUserEntity;
import com.daoli.dang.feng.mapper.DangFengUserEntityMapper;
import com.daoli.ze.ren.entity.ZerenUserEntity;
import com.daoli.ze.ren.mapper.ZerenUserEntityMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * AUTO-GENERATED: houlu @ 2019/7/28 下午1:17
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController(value = "data import dag finished callback")
@RequestMapping(value = "/api/dag/callback/import")
public class HomeController {

    @Autowired
    private DangFengUserEntityMapper dangFengUserEntityMapper;

    @Autowired
    private ZerenUserEntityMapper zerenUserEntityMapper;



    @RequestMapping(value="/home",method = GET)
    @ApiOperation(value = "插入数据测试")
    public String home(){
        System.out.println("redirect to home page!");
        DangFengUserEntity userEntity = new DangFengUserEntity();
        userEntity.setAuthResult("111");

        ZerenUserEntity userEntity1 = new ZerenUserEntity();
        userEntity1.setAuthResult("222");

        dangFengUserEntityMapper.insertSelective(userEntity);
        zerenUserEntityMapper.insertSelective(userEntity1);

        return "index";
    }

//
//    @RequestMapping(value="/home/page")
//    @ResponseBody
//    @ApiOperation(value = "创建新的导入流程")
//    public ModelAndView goHome(){
//        System.out.println("go to the home page!");
//        ModelAndView mode = new ModelAndView();
//        mode.addObject("name", "zhangsan");
//        mode.setViewName("index");
//        return mode;
//    }



}
