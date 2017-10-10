package com.mostic.network.itscy.repository;

import com.mostic.network.itscy.domain.WebScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author LIQing
 * @create 2017-09-19 13:05
 */
public interface WebScanRepository extends JpaRepository<WebScan, Integer> {

    List<WebScan> findBySystemId(String systemId);

    /**
     * 用于统计每月的安全检测次数
     *
     * @return
     */
    @Deprecated
    @Query(value = "SELECT DATE_FORMAT(create_time,'%Y%m') months,COUNT(scan_id) nums " +
            "FROM t_web_scan WHERE scan_status LIKE '%测试%' GROUP BY months ORDER BY months DESC",
            nativeQuery = true)
    List<Object[]> countScanNumsByMonth();

}
