package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into `user` (username, password, name, phone, email, address, avatar) values (#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
    void insertUser(User user);

    @Update("update `user` set username = #{username}, password = #{password}, name= #{name}, phone = #{phone}, email = #{email}, address = #{address}, avatar = #{avatar} where id = #{id} ")
    void updateUser(User user);

    @Delete("delete from `user` where id = #{id}")
    void deleteUser(Integer id);

    @Select("select * from `user` order by id DESC")
    List<User> selectAll();

    @Select("select * from `user` where id = #{id}")
    User selectById(Integer id);

    @Select("select * from `user` where name = #{name}")
    List<User> selectByName(String name);

    @Select("select * from `user` where username = #{username} and name = #{name} order by id DESC")
    List<User> selectByMore(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
    List<User> selectByMo(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc limit #{skipNum}, #{pageSize} ")
    List<User> selectByPage(@Param("username") String username, @Param("name") String name, @Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize);

    @Select("select count(id) from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc ")
    int selectByCountByPage(@Param("username") String username, @Param("name") String name);
}
