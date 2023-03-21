package com.atguigu.yygh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/21    19:48
 * @注释:
 */

@Configuration
@MapperScan("com.atguigu.yygh.hosp.mapper")
public class HospConfig {
}
