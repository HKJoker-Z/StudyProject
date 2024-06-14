package com.example.springboot.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

/***
 * 功能：
 * 作者：赵二
 * 日期：6/6/2024 12:44
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    UserMapper userMapper;

    @Override
    public boolean save(User entity) {
        if (StrUtil.isBlank(entity.getName())) {
            entity.setName(entity.getUsername());
        }
        if (StrUtil.isBlank(entity.getPassword())) {
           entity.setPassword("123");
        }
        if (StrUtil.isBlank(entity.getRole())) {
            entity.setRole("用户");
        }

        return super.save(entity);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list) {
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null && currentUser.getId() != null && list.contains(currentUser.getId())) {
            throw new ServiceException("不能删除当前用户！");
        }

        return super.removeBatchByIds(list);
    }

    public User selectByUserName(String username) {
        //根据用户名查询用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();//条件查询器
        queryWrapper.eq("username", username);//where username = #{username}

        return getOne(queryWrapper);// 等价于 select * from user where username = #{username}
    }

    //验证用户账户是否合法
    public User login(User user) {
        User dbUser = selectByUserName(user.getUsername());

        if (dbUser == null) {
            //抛出一个自定义的异常
            throw new ServiceException("用户名或密码错误！");
        }

        if (!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误！");
        }

        //生成token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);

        return dbUser;
    }

    public User register(User user) {
        User dbUser = selectByUserName(user.getUsername());

        if (dbUser != null) {
            //抛出一个自定义的异常
            throw new ServiceException("用户名已存在！");
        }

        user.setName("default" + RandomUtil.randomNumbers(4));
        userMapper.insert(user);
        return user;
    }

    public void resetPassword(User user) {
        User dbUser = selectByUserName(user.getUsername());
        if (dbUser == null) {
            //抛出一个自定义的异常
            throw new ServiceException("用户不存在！");
        }
        if (!user.getPhone().equals(dbUser.getPhone())) {
            throw new ServiceException("验证不通过！");
        }

        dbUser.setPassword("123");//将密码重置为123
        updateById(dbUser);
    }


}
