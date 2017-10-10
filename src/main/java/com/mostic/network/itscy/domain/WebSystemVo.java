package com.mostic.network.itscy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用于显示每个系统以及该系统最新的检测状态
 * 数据库中以视图的形式存在
 *
 * @author LIQing
 * @create 2017-09-19 12:44
 */
@Entity
@Table(name = "itscy_v_web_system")
public class WebSystemVo {
    @Id
    private Integer scanId;

    private String systemId;

    private String name;

    private String project;

    private String localIp;

    private String interIp;

    private String frameworkVer;

    private String databaseVer;

    private String middlewareVer;

    private String linker;

    private String info;

    private String state; // 最近一次的状态

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date scanCreateTime; // 最近一次状态的时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date systemCreateTime; // 系统创建时间

    @Override
    public String toString() {
        return "WebSystemVo{" +
                "systemId=" + systemId +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", scanCreateTime=" + scanCreateTime +
                ", systemCreateTime=" + systemCreateTime +
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

    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getScanCreateTime() {
        return scanCreateTime;
    }

    public void setScanCreateTime(Date scanCreateTime) {
        this.scanCreateTime = scanCreateTime;
    }

    public Date getSystemCreateTime() {
        return systemCreateTime;
    }

    public void setSystemCreateTime(Date systemCreateTime) {
        this.systemCreateTime = systemCreateTime;
    }
}
