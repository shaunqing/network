package com.mostic.network.itscy.dto;

/**
 * 统计每个月的安全检测次数
 *
 * @author LIQing
 * @create 2017-09-19 12:55
 */
public class WebScanReport {
    private String months;
    private String counts;

    public WebScanReport(String months, String counts) {
        this.months = months;
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "WebScanReport{" +
                "months='" + months + '\'' +
                ", counts='" + counts + '\'' +
                '}';
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }
}
