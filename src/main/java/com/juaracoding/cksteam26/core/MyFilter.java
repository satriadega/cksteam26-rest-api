package com.juaracoding.cksteam26.core;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 26/07/25 04.58
@Last Modified 26/07/25 04.58
Version 1.0
*/


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (servletRequest instanceof HttpServletRequest) {
            String strContentType = request.getContentType();
            if (strContentType != null && !strContentType.startsWith("multipart/form-data")) {
                request = new MyHttpServletRequestWrapper(request);
            }

        }
        filterChain.doFilter(request, servletResponse);
    }
}
