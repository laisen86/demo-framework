package com.example.demoframework.web.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.example.demoframework.domain.entity.User;
import com.example.demoframework.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * druid数据库连接监控控制层
 *
 * @author zhangxueli6
 * @date 2019/2/18
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/user/query")
    public User query(Long id) {
        User user = userService.selectByPrimaryKey(id);
        return user;
    }
}
