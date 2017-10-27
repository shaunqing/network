package com.mostic.network.itscy.repository;

import com.mostic.network.itscy.domain.WebScanRecord;

import com.mostic.network.itscy.domain.view.ScanReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author LIQing
 * @create 2017-09-19 13:05
 */
public interface WebScanRecordRepository extends JpaRepository<WebScanRecord, Integer> {

    Long deleteByScanId(Integer scanId);

    /**
     * 统计每月的记录数（既检测的次数）
     *
     * @return
     */
    @Query(value = "SELECT DATE_FORMAT(scan_create_time,'%Y-%m') months,COUNT(scan_id) nums " +
            "FROM itscy_web_scan_record GROUP BY months ORDER BY months ASC ",
            nativeQuery = true)
    List<Object[]> countScanNumsByMonth();

    String report = "select wsr.recordId as recordId, wsr.scanId as scanId , wsr.scanCreateTime as scanCreateTime, " +
            "wsr.scanInfo as scanInfo ,ws.systemId as systemId,ws.name as systemName, ws.localIp as localIp " +
            "from WebScanRecord wsr, WebSystem ws where wsr.systemId = ws.systemId ";

    /**
     * 显示每个系统的检测记录
     * 连表查询使用接口作为返回的对象
     *
     * @return
     */
    @Query(value = report + "order by wsr.scanCreateTime desc")
    List<ScanReport> reportScans();

    /**
     * 按月份区间筛选系统检测记录
     * @param start
     * @param end
     * @return
     */
    @Query(value = report + " AND DATE_FORMAT(wsr.scanCreateTime,'%Y-%m') BETWEEN ?1 AND ?2")
    List<ScanReport> reportScansBetweenMonths(String start, String end);


}
