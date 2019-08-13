package com.daoli.office.server;


//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;

import com.daoli.ze.ren.ZeRenConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.daoli")
@PropertySource(value = {"classpath:application.yaml"})
@EnableAspectJAutoProxy
public class Config {


}
