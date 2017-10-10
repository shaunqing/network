package com.mostic.network.common.enums;

/**
 * @author LIQing
 * @create 2017-09-21 13:12
 */
public enum FileTypeEnum {
    ORDERS(1, "安全服务工作单"),
    REPORT(2, "安全服务报告"),
    NOTICE(3, "网络安全通告工作单"),
    FIREWALL(4, "防火墙维护工作单");

    private Integer code;
    private String type;

    FileTypeEnum(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public static void main(String[] args) {
        FileTypeEnum[] x = FileTypeEnum.values();
    }
}
