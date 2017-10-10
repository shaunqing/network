package com.mostic.network.itscy.domain.view;

import java.util.Date;

/**
 * 用于显示每个系统检测记录
 * 需要连表查询，所以要使用interface
 *
 * @author LIQing
 * @create 2017-09-22 12:39
 */
public interface ScanReport {

    Integer getRecordId();

    String getSystemId();

    Integer getScanId();

    String getSystemName();

    String getLocalIp();

    Date getScanCreateTime();

    String getScanInfo();

}
