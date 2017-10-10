package com.mostic.network.itscy.enums;

/**
 * @author LIQing
 * @create 2017-09-21 13:03
 */
public enum WebScanFileTypeEnum {

    ORDERS(1, "安全服务工作单"),
    REPORT(2, "漏洞报告"),
    NOTICE(3, "安全通告");

    private Integer code;
    private String type;

    WebScanFileTypeEnum(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
