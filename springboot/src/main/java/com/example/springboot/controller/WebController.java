package com.example.springboot.controller;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * 功能：第一个接口
 * 作者：赵二
 * 日期：5/6/2024 16:54
 **/
@RestController
public class WebController {

    @GetMapping("/hello")
    public Result hello() {
        return Result.success("hello, hell~!");
    }
}
