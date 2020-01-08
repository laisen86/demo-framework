package com.example.demoframework.web.controller;

import com.example.demoframework.domain.entity.StoreInfo;
import com.example.demoframework.domain.entity.User;
import com.example.demoframework.service.StoreInfoService;
import com.example.demoframework.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * druid数据库连接监控控制层
 *
 * @author zhangxueli6
 * @date 2019/2/18
 */
@RestController
public class StoreInfoController {
    @Resource
    private StoreInfoService storeInfoService;

    @GetMapping("/storeInfo/queryAll")
    public List<StoreInfo> queryAll() {
        List<StoreInfo> storeInfoList = storeInfoService.selectAll();
        return storeInfoList;
    }
}
