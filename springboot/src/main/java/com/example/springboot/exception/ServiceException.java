package com.example.springboot.exception;

import lombok.Getter;

/***
 * 功能：
 * 作者：赵二
 * 日期：11/6/2024 13:14
 **/
@Getter
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(String msg) {
        super(msg);
        this.code = "500";
    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}