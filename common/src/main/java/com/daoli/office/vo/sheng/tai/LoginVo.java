package com.daoli.office.vo.sheng.tai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wanglining on 2019/8/31.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {

    private String loginName;

    private String loginPassword;



}
