package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.TokenUtils;
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
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    /***
     * 新增用户
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {

        try {
            userService.save(user);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
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
        userService.updateById(user);
        return Result.success();
    }


    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        User currentUser = TokenUtils.getCurrentUser();
        if (id.equals(currentUser.getId())) {
            throw new ServiceException("不能删除当前的用户");
        }
        userService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return Result.success();
    }


    /**
     * 查询用户信息
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> res = userService.list(new QueryWrapper<User>().orderByDesc("Id"));//select * from user order by id DESC
        return Result.success(res);
    }

    /***
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
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
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByAsc("id");
        queryWrapper.like(StrUtil.isNotBlank(username), "username", username);
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        //select * from user where username like '%#{username}%' and name like '%#{name}%'


        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);

        return Result.success(page);
    }


}
