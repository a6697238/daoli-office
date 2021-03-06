package com.daoli.office.vo.sheng.tai;

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
public class DianziXinxiVo {


    private Integer pid;

    private String userName;

    private String facePathDir;

    private String faceMainPic;

    private String faceDescription;

    private String welcomeAudioPath;


}
