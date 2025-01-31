package com.itheima.filter;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.io.IOException;


@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

          // 设置 CORS 头部
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, token");

        // 如果是 OPTIONS 请求，直接返回
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        //1、获取请求路径
        String path = request.getRequestURI();
        //2、判断是否包含login
        if(path.contains("/login")){
            log.info("是登录，放行：");
            filterChain.doFilter(servletRequest,servletResponse);
        }

        //3、获取token
        String token = request.getHeader("token");


        //4、判断token是否为空，空就返回错误信息（401）
        if(token == null || token.isEmpty()){
            log.info("令牌为空，返回错误信息（401）");
            response.setStatus(401);
           return;
        }

        //5、判断token是否正确，不正确就返回错误信息（401）
        try {
            log.info("开始解析token");
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌解析失败，返回错误信息（401）");
            response.setStatus(401);
            return;
        }

        //6、放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
