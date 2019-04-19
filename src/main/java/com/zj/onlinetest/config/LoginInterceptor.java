package com.zj.onlinetest.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: zj
 * @Date: 2019/4/17 15:51
 * @Description: 拦截器
 */
@Component
//1、创建自己的拦截器类并实现 HandlerInterceptor 接口。
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 进入controller层之前拦截请求
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("Interceptor preHandle");
//        System.out.println( request.getParameter("token") );
        if (request.getParameter( "token" )==null) {
            response.sendRedirect( request.getContextPath()+"/error.html" );
            return false;
        }
        return true;
    }

    /**
     * 处理请求完成后视图渲染之前的处理操作
     *
     * 请求之后，控制器中抛出了异常的话就不会执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("Interceptor postHandle");
    }

    /**
     * 视图渲染之后的操作
     * 请求之后调用，不管抛不抛出异常都会被调用.参数中异常如果被异常处理器调用的话就不会传入到参数中．
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("Interceptor afterCompletion");
    }
}

