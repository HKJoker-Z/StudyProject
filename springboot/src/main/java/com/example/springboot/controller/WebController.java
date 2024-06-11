package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * 功能：第一个接口
 * 作者：赵二
 * 日期：5/6/2024 16:54
 **/
@RestController
public class WebController {

    @Resource
    UserService userService;

    @AuthAccess
    @GetMapping("/")
    public Result hello() {
        return Result.success("hello, hell~!");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("输入数据有误！");
        }

        user = userService.login(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("输入数据有误！");
        }

        if (user.getUsername().length() > 18 || user.getPassword().length() > 18 ) {
            return Result.error("用户名或密码太长！");
        }

        user = userService.register(user);
        return Result.success(user);
    }
}
