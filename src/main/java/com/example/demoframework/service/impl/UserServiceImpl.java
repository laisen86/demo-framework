package com.example.demoframework.service.impl;

import com.example.demoframework.dao.UserDao;
import com.example.demoframework.domain.entity.User;
import com.example.demoframework.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangxueli6
 * @date 2019/2/17
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User selectByPrimaryKey(Long id) {
        return userDao.selectByPrimaryKey(id);
    }
}
