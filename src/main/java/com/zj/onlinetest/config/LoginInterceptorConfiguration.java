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
//2、创建一个Java类继承WebMvcConfigurerAdapter，并重写 addInterceptors 方法。
public class LoginInterceptorConfiguration implements WebMvcConfigurer {

    //3、实例化自定义的拦截器
    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //如果interceptor中不注入redis或其他项目可以直接new
        //4、将拦截器对像手动添加到拦截器链中（在addInterceptors方法中添加）
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns( "/login" )
                                                                        .excludePathPatterns("/test/**")
                                                                        .excludePathPatterns( "/static/**" );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/static/**" ).
                addResourceLocations( "classpath:/static/" );
    }


}

