package com.example.demoframework.common;

import java.io.Serializable;

/**
 * @author zhangxueli6
 * @date 2019/2/18
 */
public class ResultModel<D> implements Serializable {
    private static final long serialVersionUID = -4873593947049050524L;
    private Integer code;
    private D data;
    private String tip;
    private Integer currentTime;
    private String msg;

    public ResultModel() {
    }

    public Integer getCode() {
        return this.code;
    }

    public ResultModel<D> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public D getData() {
        return this.data;
    }

    public ResultModel<D> setData(D data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public ResultModel<D> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Integer getCurrentTime() {
        return this.currentTime;
    }

    public ResultModel<D> setCurrentTime(Integer currentTime) {
        this.currentTime = currentTime;
        return this;
    }

    public String getTip() {
        return this.tip;
    }

    public ResultModel<D> setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public static <T> ResultModel<T> getInstance(Integer code, T data) {
        return getInstance(code, (String) null, (int) (System.currentTimeMillis() / 1000L), (String) null, data);
    }

    public static <T> ResultModel<T> getInstance(Integer code, Integer currentTime, T data) {
        return getInstance(code, (String) null, currentTime, (String) null, data);
    }

    public static <T> ResultModel<T> getInstance(Integer code, String msg, Integer currentTime, String tip, T data) {
        ResultModel<T> rm = new ResultModel();
        rm.setCode(code);
        rm.setCurrentTime(currentTime);
        rm.setData(data);
        rm.setMsg(msg);
        rm.setTip(tip);
        return rm;
    }

    @Override
    public String toString() {
        return "ResultModel{code=" + this.code + ", data=" + this.data + ", tip='" + this.tip + '\'' + ", currentTime=" + this.currentTime + ", msg='" + this.msg + '\'' + '}';
    }
}
