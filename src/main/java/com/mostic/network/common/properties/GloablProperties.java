package com.mostic.network.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用于获取配置文件application-*中的内容
 *
 * @author LIQing
 * @create 2017-09-22 8:44
 */
@Component
@ConfigurationProperties(prefix = "network")
public class GloablProperties {
    private String itscyRoot;
    private String itscyExport;
    private String firewallRoot;

    public String getItscyRoot() {
        return itscyRoot;
    }

    public void setItscyRoot(String itscyRoot) {
        this.itscyRoot = itscyRoot;
    }

    public String getItscyExport() {
        return itscyExport;
    }

    public void setItscyExport(String itscyExport) {
        this.itscyExport = itscyExport;
    }

    public String getFirewallRoot() {
        return firewallRoot;
    }

    public void setFirewallRoot(String firewallRoot) {
        this.firewallRoot = firewallRoot;
    }
}
