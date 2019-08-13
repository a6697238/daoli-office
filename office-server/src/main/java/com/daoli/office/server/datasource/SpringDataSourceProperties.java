package com.daoli.office.server.datasource;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * IdcSetting
 *
 * @author hl162981
 * @date 2018/7/3
 */
@Setter
@Getter
@Data
public class SpringDataSourceProperties {

  private String password;

  private String username;

  private String initialize;

  private String url;

  private String driverClassName;

}
