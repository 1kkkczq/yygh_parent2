package com.atguigu.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/21    18:21
 * @注释:
 */

@SpringBootApplication

@EnableDiscoveryClient  //nacos注册中心
@EnableFeignClients( basePackages = "com.atguigu")
//@MapperScan("com.atguigu")  和 mapper只能加一个
public class ServiceHospSetApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospSetApplication.class , args);
    }
}
