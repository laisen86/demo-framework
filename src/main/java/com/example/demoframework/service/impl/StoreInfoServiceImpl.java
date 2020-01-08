package com.example.demoframework.service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.example.demoframework.dao.StoreInfoDao;
import com.example.demoframework.domain.entity.StoreInfo;
import com.example.demoframework.service.StoreInfoService;

@Service
public class StoreInfoServiceImpl implements StoreInfoService {

    @Resource
    private StoreInfoDao storeInfoDao;

    @Override
    public int updateBatch(List<StoreInfo> list) {
        return storeInfoDao.updateBatch(list);
    }

    @Override
    public int batchInsert(List<StoreInfo> list) {
        return storeInfoDao.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(StoreInfo record) {
        return storeInfoDao.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(StoreInfo record) {
        return storeInfoDao.insertOrUpdateSelective(record);
    }

    @Override
    public List<StoreInfo> selectAll() {
        return storeInfoDao.selectAll();
    }

}
