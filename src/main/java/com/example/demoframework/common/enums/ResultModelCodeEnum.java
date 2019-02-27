package com.example.demoframework.common.enums;

import java.util.Objects;

/**
 * @author zhangxueli
 * @date 2019年2月18日23:52:20
 */
public enum ResultModelCodeEnum {

    SUCCESS(200, "请求成功"),
    FAULT(400, "请求参数问题"),
    NO_PERMISSION(403, "没有权限"),
    INTERNAL_ERROR(500, "服务端处理异常"),
    NEED_LOGIN(777, "需要登录"),
    BIZ_ERROR(501, "业务规则错误"),
    SERVER_LIMIT(503, "服务限流"),
    ;
    /**
     * 返回modelcode值
     */
    private final Integer code;
    /**
     * 返回model 描述
     */
    private final String desc;

    public Integer code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    ResultModelCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResultModelCodeEnum getEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (ResultModelCodeEnum modelCodeEnum : ResultModelCodeEnum.values()) {
            if (Objects.equals(modelCodeEnum.code(), code)) {
                return modelCodeEnum;
            }
        }
        return null;
    }
}
