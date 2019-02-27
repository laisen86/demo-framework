package com.example.demoframework.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * order_change_log
 *
 * @author
 */
public class OrderChangeLog implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 商家id
     */
    private Long venderId;

    /**
     * 商家AppKey
     */
    private String appKey;

    /**
     * 业务订单号
     */
    private Long orderId;

    /**
     * 操作时间
     */
    private Date operateDt;

    /**
     * 操作类型(1-商家请求；2-运营操作；3-系统)
     */
    private Byte operateType;

    /**
     * 操作人(商家-请求ID；运营-erp；系统-SYSTEM)
     */
    private String operator;

    /**
     * 原状态(1-待支付/待付款;2-已支付/已付款;3-已取消;4-已完成)
     */
    private Byte oldStatus;

    /**
     * 目标状态(1-待支付/待付款;2-已支付/已付款;3-已取消;4-已完成)
     */
    private Byte targetStatus;

    /**
     * 删除标志 0=已删除 1=未删除
     */
    private Byte yn;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOperateDt() {
        return operateDt;
    }

    public void setOperateDt(Date operateDt) {
        this.operateDt = operateDt;
    }

    public Byte getOperateType() {
        return operateType;
    }

    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Byte getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Byte oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Byte getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(Byte targetStatus) {
        this.targetStatus = targetStatus;
    }

    public Byte getYn() {
        return yn;
    }

    public void setYn(Byte yn) {
        this.yn = yn;
    }
}