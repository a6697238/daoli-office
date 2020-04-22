package com.daoli.office.server.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.daoli.office.vo.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * AUTO-GENERATED: houlu @ 2019/8/24 下午1:26
 *
 * @author houlu
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@Slf4j
public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResponse exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("", e);
        JsonResponse response = new JsonResponse();
        if (StringUtils.isEmpty(e.getMessage())) {
            response.setMsg("请联系管理员");
        } else {
            response.setMsg(e.getMessage());
        }
        response.setCode(200);
        response.setStatus(false);
        //front not support, so set to null again...
        response.setData(null);
        return response;
    }


}
