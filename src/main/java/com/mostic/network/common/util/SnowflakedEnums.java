package com.mostic.network.common.util;

/**
 * 用于唯一编号初始化
 *
 * @author LIQing
 * @create 2017-09-22 8:33
 */
public enum SnowflakedEnums {
    ITSCY(0, 0),
    FIREWALL(0, 1);

    private long workerId;
    private long datacenterId;

    SnowflakedEnums(long workerId, long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }
}
