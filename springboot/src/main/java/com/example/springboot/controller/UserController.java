package com.example.springboot.controller;

import com.example.springboot.common.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 功能：
 * 作者：赵二
 * 日期：6/6/2024 12:45
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /***
     * 新增用户
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {

        try {
            userService.insertUser(user);
        } catch (Exception e){
            if (e instanceof DuplicateKeyException){
                return Result.error("用户名重复！");
            } else {
                return Result.error("系统错误");
            }
        }

        return Result.success();
    }

    /**
     * 修改用户信息
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }


    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{id}")
    public Result delete (@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete (@RequestBody List<Integer> ids) {
        userService.batchDeleteUser(ids);
        return Result.success();
    }


    /**
     * 查询用户信息
     */
    @GetMapping("/selectAll")
    public Result selectAll () {
        List<User> res = userService.selectAll();
        return Result.success(res);
    }

    /***
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }

    @GetMapping("/selectByName/{name}")
    public Result selectByName(@PathVariable String name) {
        List<User> res = userService.selectByName(name);
        return Result.success(res);
    }

    @GetMapping("/selectByMore")
    public Result selectByMore(@RequestParam String username, @RequestParam String name) {
        List<User> res = userService.selectByMore(username, name);
        return Result.success(res);
    }

    @GetMapping("/selectByMo")
    public Result selectByMo(@RequestParam String username, @RequestParam String name) {
        List<User> res = userService.selectByMo(username, name);
        return Result.success(res);
    }

    /***
     * 分页查询
     * @param username
     * @param name
     * @param pageNum 当前页码
     * @param pageSize 每页有多少个
     * @return
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam String username,
                               @RequestParam String name,
                               @RequestParam Integer pageNum,
                               @RequestParam Integer pageSize
    ) {
        Page<User> res = userService.selectByPage(username, name, pageNum, pageSize);
        return Result.success(res);
    }


}
