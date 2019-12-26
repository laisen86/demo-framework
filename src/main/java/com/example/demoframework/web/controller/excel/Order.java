package com.example.demoframework.web.controller.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Order {
    @ExcelProperty("编号")
    private Long id;
    @ExcelProperty("名称")
    private String name;
    @ExcelProperty("地址")
    private String address;
    @ExcelProperty("描述")
    private String desc;
    @ExcelProperty("备注")
    private String remark;
    @ExcelProperty("小说名称")
    private String textName;
    @ExcelProperty("订单类型名称")
    private String orderTypeName;
    @ExcelProperty("创建日期")
    private Date createDate;
    @ExcelProperty("修改日期")
    private Date modityDate;
    @ExcelProperty("local日期")
    private Date localDate;
}
