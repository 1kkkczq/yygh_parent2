package com.atguigu.yygh.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/26    19:55
 * @注释: gateway网关
 */
@SpringBootApplication
public class ServerGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerGatewayApplication.class , args);
    }
}
