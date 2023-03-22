package com.atguigu.yygh.cmn.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author kkk
 * @Date 2023/3/21    19:48
 * @注释:
 */

@Configuration
@MapperScan("com.atguigu.yygh.cmn.mapper")
public class CmnConfig {

    /**
     * 配置mp分页插件
     */

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}



