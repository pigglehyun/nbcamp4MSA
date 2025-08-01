package com.spring_cloud.eureka.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        int serverPort = request.getServerPort();
        ((HttpServletResponse) response).setHeader("Server-Port", String.valueOf(serverPort));
        chain.doFilter(request, response);
    }
}
