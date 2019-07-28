package com.office.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(value="/home")
    public String home(){
        System.out.println("redirect to home page!");
        return "index";
    }


    @RequestMapping(value="/home/page")
    @ResponseBody
    public ModelAndView goHome(){
        System.out.println("go to the home page!");
        ModelAndView mode = new ModelAndView();
        mode.addObject("name", "zhangsan");
        mode.setViewName("index");
        return mode;
    }



}
