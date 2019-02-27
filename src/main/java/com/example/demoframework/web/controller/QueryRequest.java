package com.example.demoframework.web.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 查询请求入参
 *
 * @author zhangxueli6
 * @date 2019/2/18
 */
public class QueryRequest {
    /**
     * id
     */
    @NotNull(message = "id不可以为空")
    private Long id;
    /**
     * 名称
     */
    @NotEmpty(message = "名称不可以为空")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
