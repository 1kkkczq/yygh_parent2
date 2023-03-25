package com.atguigu.yygh.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/22    22:51
 * @注释:
 */
@SpringBootApplication
@EnableDiscoveryClient  //nacos注册中心
@ComponentScan(basePackages = "com.atguigu")
public class ServiceHospCmnApplication {
    public static void main(String[] args) {

        SpringApplication.run(ServiceHospCmnApplication.class,args);
    }
}
