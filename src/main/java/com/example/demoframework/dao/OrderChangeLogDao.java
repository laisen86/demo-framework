package com.example.demoframework.dao;

import com.example.demoframework.domain.entity.OrderChangeLog;
import org.springframework.stereotype.Repository;

public interface OrderChangeLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderChangeLog record);

    int insertSelective(OrderChangeLog record);

    OrderChangeLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderChangeLog record);

    int updateByPrimaryKey(OrderChangeLog record);
}