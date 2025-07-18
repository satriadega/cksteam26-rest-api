package com.juaracoding.cksteam26.handler;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 18/07/25 13.50
@Last Modified 18/07/25 13.50
Version 1.0
*/

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public ResponseEntity<Object> handleResponse(
            String message,
            HttpStatus status,
            Object data,
            Object errorCode,
            HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("data", data==null?"":data);
        map.put("timestamp", Instant.now().toString());
        map.put("success", !status.isError());
        if(errorCode!=null) {
            map.put("error_code", errorCode);
            map.put("path", request.getRequestURI());
        }

        return new ResponseEntity<>(map, status);
    }

    public ResponseEntity<Object> handleResponse(
            String message,
            HttpStatus status,
            Object data,
            Object errorCode,
            String info,
            HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("data", data==null?"":data);
        map.put("timestamp", Instant.now().toString());
        map.put("success", !status.isError());
        map.put("info", info);
        if(errorCode!=null) {
            map.put("error_code", errorCode);
            map.put("path", request.getRequestURI());
        }

        return new ResponseEntity<>(map, status);
    }
}
