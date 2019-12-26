package com.example.demoframework.web.controller.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * excel 文件生成
 */
public class GenerateExcel {
    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        String fileName = TestFileUtil.getPath() + "write" + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, Order.class).sheet("模板").doWrite(data());
        Long endTime = System.currentTimeMillis();
        System.out.println("花费时间为：" + (endTime - startTime));
    }

    private static List<Order> data() {
        List<Order> dataList = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            Order order = new Order();
            order.setDesc("这个是订单描述");
            order.setRemark("投递不合格");
            order.setTextName("斗破苍穹");
            order.setModityDate(new Date());
            order.setLocalDate(new Date());
            order.setId(Long.valueOf(i));
            order.setName("订单");
            order.setAddress("京东商城");
            order.setOrderTypeName("厨余订单");
            order.setCreateDate(new Date());

            dataList.add(order);
        }
        return dataList;
    }
}
