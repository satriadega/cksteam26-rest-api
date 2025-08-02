package com.juaracoding.cksteam26.security;

import com.juaracoding.cksteam26.config.JwtConfig;
import com.juaracoding.cksteam26.core.MyHttpServletRequestWrapper;
import com.juaracoding.cksteam26.service.AuthService;
import com.juaracoding.cksteam26.util.LoggingFile;
import com.juaracoding.cksteam26.util.RequestCapture;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        authorization = authorization == null ? "" : authorization;

        try {
            if (authorization.startsWith("Bearer ") && authorization.length() > 7) {
                String token = authorization.substring(7);

                if ("y".equalsIgnoreCase(JwtConfig.getTokenEncryptEnable())) {
                    token = Crypto.performDecrypt(token);
                }

                String username = jwtUtility.getUsernameFromToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = authService.loadUserByUsername(username);

                    if (jwtUtility.validateToken(token, userDetails)) {
                        String strContentType = request.getContentType() == null ? "" : request.getContentType();
                        if (!strContentType.startsWith("multipart/form-data") || "".equals(strContentType)) {
                            request = new MyHttpServletRequestWrapper(request);
                        }

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            LoggingFile.logException("JwtFilter", "doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) " + RequestCapture.allRequest(request), e);
        }

        filterChain.doFilter(request, response);
    }
}
