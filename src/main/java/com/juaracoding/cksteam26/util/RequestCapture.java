package com.juaracoding.cksteam26.util;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 21/07/25 20.18
@Last Modified 21/07/25 20.18
Version 1.0
*/


import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk menangkap seluruh informasi request dari client
 */
public class RequestCapture {

    public static String allRequest(WebRequest webRequest) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        return processingData(request);
    }

    public static String allRequest(HttpServletRequest requestx) {
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(requestx);
        return processingData(request);
    }

    private static String processingData(HttpServletRequest request) {
        String headerName = "";
        String paramName = "";
        Map<String, Object> requestz = new HashMap<>();
        Map<String, Object> reqParam = new HashMap<>();
        Map<String, Object> reqHeader = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            reqHeader.put(headerName, request.getHeader(headerName));
        }

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            paramName = params.nextElement();
            reqParam.put(paramName, request.getParameter(paramName));
        }
        requestz.put("authType", request.getAuthType());
        requestz.put("method", request.getMethod());
        requestz.put("serverName", request.getServerName());
        requestz.put("session", request.getSession());
        requestz.put("queryString", request.getQueryString());
        requestz.put("remoteAddr", request.getRemoteAddr());
        requestz.put("requestSessionId", request.getRequestedSessionId());
        requestz.put("serverPort", request.getServerPort());
        requestz.put("pathInfo", request.getPathInfo());
        requestz.put("remoteHost", request.getRemoteHost());
        requestz.put("locale", request.getLocale());
        requestz.put("principal", request.getUserPrincipal());
        requestz.put("isSecure", request.isSecure());
        requestz.put("reqHeader", reqHeader);
        requestz.put("reqParam", reqParam);
        try {
            System.out.println("Request Get Method : " + request.getMethod());
            if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod()) || "PATCH".equalsIgnoreCase(request.getMethod())) {
                requestz.put("reqBody", readInputString(request));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String strValue = new JSONObject(requestz).toString();
        return strValue;

    }

    private static String readInputString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        String requestBody;

        try {
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            requestBody = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return requestBody;
    }
}
