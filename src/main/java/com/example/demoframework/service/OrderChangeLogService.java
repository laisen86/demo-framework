package com.example.demoframework.service;

import com.example.demoframework.domain.entity.OrderChangeLog;

/**
 * OrderChangeLogService
 *
 * @author zhangxueli6
 * @since 2019/2/17
 */
public interface OrderChangeLogService {
    public OrderChangeLog selectByPrimaryKey(Long id);
}
