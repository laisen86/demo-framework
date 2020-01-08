package com.example.demoframework.service;

import java.util.List;

import com.example.demoframework.domain.entity.StoreInfo;

public interface StoreInfoService {


    int updateBatch(List<StoreInfo> list);

    int batchInsert(List<StoreInfo> list);

    int insertOrUpdate(StoreInfo record);

    int insertOrUpdateSelective(StoreInfo record);

    List<StoreInfo> selectAll();

}
