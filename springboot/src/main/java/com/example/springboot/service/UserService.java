package com.example.springboot.service;

import cn.hutool.core.util.RandomUtil;
import com.example.springboot.common.Page;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 功能：
 * 作者：赵二
 * 日期：6/6/2024 12:44
 **/
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    public void batchDeleteUser(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteUser(id);
        }
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public List<User> selectByName(String name) {
        return userMapper.selectByName(name);
    }

    public List<User> selectByMore(String username, String name) {
        return userMapper.selectByMore(username, name);
    }

    public List<User> selectByMo(String username, String name) {
        return userMapper.selectByMo(username, name);
    }

    public Page<User> selectByPage(String username, String name, Integer pageNum, Integer pageSize) {
        Integer skipNum = (pageNum - 1) * pageSize;

        Page<User> page = new Page<>();
        Map<String, Object> res = new HashMap<>();
        List<User> userList = userMapper.selectByPage(username, name, skipNum, pageSize);
        Integer total = userMapper.selectByCountByPage(username, name);

        page.setTotal(total);
        page.setList(userList);

        return page;
    }

    //验证用户账户是否合法
    public User login(User user) {
        //根据用户名查询用户信息
        User dbUser = userMapper.selectByUserName(user.getUsername());
        if (dbUser == null) {
            //抛出一个自定义的异常
            throw new ServiceException("用户名或密码错误！");
        }

        if (!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误！");
        }

        return dbUser;
    }

    public User register(User user) {
        User dbUser = userMapper.selectByUserName(user.getUsername());
        if (dbUser != null) {
            //抛出一个自定义的异常
            throw new ServiceException("用户名已存在！");
        }

        user.setName("default" + RandomUtil.randomNumbers(4));
        userMapper.insertUser(user);
        return user;
    }
}
