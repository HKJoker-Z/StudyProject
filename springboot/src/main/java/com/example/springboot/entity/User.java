package com.example.springboot.entity;

import lombok.*;

/***
 * 功能：用户类
 * 作者：赵二
 * 日期：6/6/2024 12:36
 **/
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;

}
