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
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 进入controller层之前拦截请求
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getParameter( "token" )==null) {
            response.sendRedirect( request.getContextPath()+"/error.html" );
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("Interceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("Interceptor afterCompletion");
    }
}

