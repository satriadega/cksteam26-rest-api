package com.juaracoding.cksteam26.util;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 18/07/25 13.44
@Last Modified 18/07/25 13.44
Version 1.0
*/

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.juaracoding.cksteam26.handler.ResponseHandler;

import jakarta.servlet.http.HttpServletRequest;

public class GlobalResponse {

    public static ResponseEntity<Object> dataSavedSuccessfully(Object data, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("DATA SAVED SUCCESSFULLY", HttpStatus.CREATED, data, null, request);
    }

    public static ResponseEntity<Object> dataUpdatedSuccessfully(HttpServletRequest request) {
        return new ResponseHandler().handleResponse("DATA UPDATED SUCCESSFULLY", HttpStatus.OK, null, null, request);
    }

    public static ResponseEntity<Object> serverError(String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, errorCode, request);
    }

    public static ResponseEntity<Object> dataIsNotFound(String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("DATA IS NOT FOUND", HttpStatus.BAD_REQUEST, null, errorCode, request);
    }

    public static ResponseEntity<Object> dataIsFound(Object data, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("DATA SUCCESSFULLY FOUND", HttpStatus.OK, data, null, request);
    }

    public static ResponseEntity<Object> objectNull(String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("INVALID DATA", HttpStatus.BAD_REQUEST, null, errorCode, request);
    }

    public static ResponseEntity<Object> dataIsDeleted(HttpServletRequest request) {
        return new ResponseHandler().handleResponse("DATA SUCCESSFULLY DELETED", HttpStatus.OK, null, null, request);
    }
    
    public static ResponseEntity<Object> customError(String errorCode, String message, HttpServletRequest request) {
        return new ResponseHandler().handleResponse(message, HttpStatus.BAD_REQUEST, errorCode, null, request);
    }

    public static ResponseEntity<Object> forbidden(String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("FORBIDDEN", HttpStatus.FORBIDDEN, null, errorCode, request);
    }
}
