package com.example.springboot.common;

import lombok.Data;

import java.util.List;

/***
 * 功能：
 * 作者：赵二
 * 日期：6/6/2024 14:46
 **/
@Data
public class Page<T> {
    private Integer total;
    private List<T> list;
}
