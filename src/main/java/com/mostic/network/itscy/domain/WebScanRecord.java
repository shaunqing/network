package com.mostic.network.itscy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统检测记录
 * 只有当WebScan的状态是“待修复”和“已通过”时，才对Record表进行操作
 *
 * @author LIQing
 * @create 2017-09-19 12:38
 */
@Entity
@Table(name = "itscy_web_scan_record")
public class WebScanRecord {
    @Id
    @GeneratedValue
    private Integer recordId;

    @NotNull
    private String systemId;

    @NotNull
    private Integer scanId;

    @Length(max = 300, message = "情况内容过长！")
    private String scanInfo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date scanCreateTime;

    public WebScanRecord() {
    }

    public WebScanRecord(WebScan webScan) {
        this.systemId = webScan.getSystemId();
        this.scanId = webScan.getScanId();
        this.scanInfo = webScan.getInfo();
        this.scanCreateTime = webScan.getCreateTime();
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Integer getScanId() {
        return scanId;
    }

    public void setScanId(Integer scanId) {
        this.scanId = scanId;
    }

    public String getScanInfo() {
        return scanInfo;
    }

    public void setScanInfo(String scanInfo) {
        this.scanInfo = scanInfo;
    }

    public Date getScanCreateTime() {
        return scanCreateTime;
    }

    public void setScanCreateTime(Date scanCreateTime) {
        this.scanCreateTime = scanCreateTime;
    }
}
