package com.daoli.office.vo.sheng.tai;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wanglining on 2019/10/3.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private Integer id;

    private String userId;

    private String zhiWu;

    private String departmentId;

    @ApiModelProperty(value = "姓名", required = true)
    private String userName;

    @ApiModelProperty(value = "登录名", required = true)
    private String loginName;

    @ApiModelProperty(value = "密码", required = true)
    private String loginPassword;


}
