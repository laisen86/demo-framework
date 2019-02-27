package com.example.demoframework.service.impl;

import com.example.demoframework.dao.OrderChangeLogDao;
import com.example.demoframework.domain.entity.OrderChangeLog;
import com.example.demoframework.service.OrderChangeLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangxueli6
 * @date 2019/2/17
 */
@Service
public class OrderChangeLogServiceImpl implements OrderChangeLogService {
    @Resource
    private OrderChangeLogDao orderChangeLogDao;

    @Override
    public OrderChangeLog selectByPrimaryKey(Long id) {
        return orderChangeLogDao.selectByPrimaryKey(id);
    }
}
