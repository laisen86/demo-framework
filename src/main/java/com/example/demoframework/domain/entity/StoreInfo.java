package com.example.demoframework.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "store_info")
public class StoreInfo {
    /**
     * 主键id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 门店名称
     */
    @Column(name = "store_name")
    private String storeName;

    /**
     * 门店地址
     */
    @Column(name = "store_addr")
    private String storeAddr;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}