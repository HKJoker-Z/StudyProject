package com.example.springboot.exception;

/***
 * 功能：
 * 作者：赵二
 * 日期：11/6/2024 13:14
 **/
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
