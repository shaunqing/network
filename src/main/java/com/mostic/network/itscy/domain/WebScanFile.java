package com.mostic.network.itscy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 每条状态记录对应的上传文件（与webscan是1对1的关系）
 *
 * @author LIQing
 * @create 2017-09-19 12:42
 */
@Entity
@Table(name = "itscy_web_scan_file")
public class WebScanFile {
    @Id
    @GeneratedValue
    private Integer scanFileId;

    private Integer scanId;

    private Integer userId;

    @Length(max = 100, message = "附加名称过长！")
    private String name;

    @Length(max = 10)
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public WebScanFile() {
    }

    public WebScanFile(Integer scanId, String name, String type, Date createTime) {
        this.scanId = scanId;
        this.name = name;
        this.type = type;
        this.createTime = createTime;
    }

    public Integer getScanFileId() {
        return scanFileId;
    }

    public void setScanFileId(Integer scanFileId) {
        this.scanFileId = scanFileId;
    }

    public Integer getScanId() {
        return scanId;
    }

    public void setScanId(Integer scanId) {
        this.scanId = scanId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
