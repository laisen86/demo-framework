package com.example.demoframework.dao;

import com.example.demoframework.domain.entity.StoreInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface StoreInfoDao extends Mapper<StoreInfo> {
    int updateBatch(List<StoreInfo> list);

    int batchInsert(@Param("list") List<StoreInfo> list);

    int insertOrUpdate(StoreInfo record);

    int insertOrUpdateSelective(StoreInfo record);

    List<StoreInfo> selectAll();
}