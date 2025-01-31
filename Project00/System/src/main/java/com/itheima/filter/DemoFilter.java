package com.itheima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")//拦截所有请求
@Slf4j
public class DemoFilter implements Filter {
    //拦截到请求执行
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到请求");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    //初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化");
    }

    @Override
    public void destroy() {
        log.info("销毁");
    }
}
