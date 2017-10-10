package com.mostic.network.itscy.enums;

/**
 * @author LIQing
 * @create 2017-09-21 11:09
 */
public enum WebScanStateEnum {
    SCANING(1, "测试中"),
    BUG(2, "待修复"),
    RESCANING(3, "复测中"),
    SUCCESS(4, "已通过"),
    CANCEL(5, "已废弃");

    private Integer code;
    private String state;

    WebScanStateEnum(Integer code, String state) {
        this.code = code;
        this.state = state;
    }

    public Integer getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
