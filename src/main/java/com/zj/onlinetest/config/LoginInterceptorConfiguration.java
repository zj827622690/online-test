package com.zj.onlinetest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @Auther: zj
 * @Date: 2019/4/17 15:58
 * @Description: 拦截器config
 */
@Configuration
public class LoginInterceptorConfiguration implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns( "/login" )
                                                                        .excludePathPatterns( "/" )
                                                                        .excludePathPatterns( "/static/**" );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/static/**" ).
                addResourceLocations( "classpath:/static/" );
    }


}

