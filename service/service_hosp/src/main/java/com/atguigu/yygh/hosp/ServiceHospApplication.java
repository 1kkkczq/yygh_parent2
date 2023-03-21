package com.atguigu.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/21    18:21
 * @注释:
 */
@SpringBootApplication
//@MapperScan("com.atguigu.yygh.mapper")
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class , args);
    }
}
