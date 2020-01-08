package com.example.demoframework.service;

import com.example.demoframework.domain.entity.User;

/**
 * OrderChangeLogService
 *
 * @author zhangxueli6
 * @since 2019/2/17
 */
public interface UserService {
    public User selectByPrimaryKey(Long id);
}
