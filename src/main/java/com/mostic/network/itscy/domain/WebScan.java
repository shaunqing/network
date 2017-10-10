package com.mostic.network.itscy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统检测状态
 *
 * @author LIQing
 * @create 2017-09-19 12:38
 */
@Entity
@Table(name = "itscy_web_scan")
public class WebScan {
    @Id
    @GeneratedValue
    private Integer scanId;

    private String systemId;

    @Length(max = 10)
    private String state;

    @Length(max = 300, message = "情况内容过长！")
    private String info;

    private Integer fileCount;

    private String fileExtension;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @Transient
    private String fileType; //不对应数据库字段，用于临时保存，最终保存到WebScanFile中

    public WebScan() {
    }

    public WebScan(String systemId, String state, Date createTime, Integer fileCount) {
        this.systemId = systemId;
        this.state = state;
        this.createTime = createTime;
        this.fileCount = fileCount;
    }

    @Override
    public String toString() {
        return "WebScan{" +
                "scanId=" + scanId +
                ", systemId=" + systemId +
                ", state='" + state + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public Integer getScanId() {
        return scanId;
    }

    public void setScanId(Integer scanId) {
        this.scanId = scanId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
