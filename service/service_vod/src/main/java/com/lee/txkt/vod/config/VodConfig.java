package com.lee.txkt.vod.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
@MapperScan("com.lee.txkt.vod.mapper")
public class VodConfig {

}