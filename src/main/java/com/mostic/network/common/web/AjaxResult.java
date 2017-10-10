package com.mostic.network.common.web;

import com.mostic.network.common.enums.MessageEnums;

/**
 * 将所有的ajax请求返回类型，全部封装成json数据
 *
 * @author LIQing
 * @create 2017-09-19 14:48
 */
public class AjaxResult<T> {

    private boolean success; //执行是否成功
    private String msg;
    private T data;

    public AjaxResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public AjaxResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public AjaxResult(boolean success, String error, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public AjaxResult(boolean success, MessageEnums messageEnums) {
        this.success = success;
        this.msg = messageEnums.getMsg();
    }

    public AjaxResult(boolean success, MessageEnums messageEnums, T data) {
        this.success = success;
        this.msg = messageEnums.getMsg();
        this.data = data;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
