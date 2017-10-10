package com.mostic.network.itscy.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统信息
 *
 * @author LIQing
 * @create 2017-09-19 11:32
 */
@Entity
@Table(name = "itscy_web_system")
public class WebSystem {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String systemId;

    private Integer userId;

    @Length(max = 50, message = "系统名称过长！")
    private String name;

    @Length(max = 40)
    private String project;

    @Length(max = 100)
    private String localIp;

    @Length(max = 100)
    private String interIp;

    @Length(max = 100)
    private String frameworkVer;

    @Length(max = 100)
    private String databaseVer;

    @Length(max = 100)
    private String middlewareVer;

    @Length(max = 30)
    private String netType;

    @Length(max = 30)
//    @Column(updatable = false)
    private String scanType;

    @Length(max = 30)
    private String linker;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Override
    public String toString() {
        return "WebSystem{" +
                "systemId=" + systemId +
                ", name='" + name + '\'' +
                '}';
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getInterIp() {
        return interIp;
    }

    public void setInterIp(String interIp) {
        this.interIp = interIp;
    }

    public String getFrameworkVer() {
        return frameworkVer;
    }

    public void setFrameworkVer(String frameworkVer) {
        this.frameworkVer = frameworkVer;
    }

    public String getDatabaseVer() {
        return databaseVer;
    }

    public void setDatabaseVer(String databaseVer) {
        this.databaseVer = databaseVer;
    }

    public String getMiddlewareVer() {
        return middlewareVer;
    }

    public void setMiddlewareVer(String middlewareVer) {
        this.middlewareVer = middlewareVer;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
