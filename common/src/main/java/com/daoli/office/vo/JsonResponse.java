package com.daoli.office.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "api response")
@Data
public class JsonResponse {


    public final static int error404 = 404;
    public final static int error500 = 500;
    public final static int success200 = 200;


    @ApiModelProperty(value = "是否成功", required = true)
    protected boolean status;

    @ApiModelProperty(value = "具体信息", required = true)
    protected String msg;

    @ApiModelProperty(value = "状态码", required = true)
    protected int code;

    @ApiModelProperty(value = "附加数据信息", required = false)
    protected Object data;


    public JsonResponse() {
        this("");
    }

    public JsonResponse(Object data) {
        this.msg = "success";
        this.status = true;
        this.data = data;
        this.code = success200;
    }

    public JsonResponse(boolean status, String msg) {
        this(status, success200, msg);
    }

    public JsonResponse(boolean status, int code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(String msg, Object data) {
        this.msg = msg;
        this.status = true;
        this.data = data;
        this.code = success200;
    }

}
