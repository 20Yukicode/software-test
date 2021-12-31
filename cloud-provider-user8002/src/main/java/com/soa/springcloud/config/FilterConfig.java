//package com.soa.springcloud.config;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class FilterConfig implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
//        //支持跨域请求
//        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        //是否支持cookie跨域
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        //Origin, X-Requested-With, Content-Type, Accept,Access-Token
//        response.setHeader("Access-Control-Allow-Headers",
//                "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");
//        return true;
//    }
//}