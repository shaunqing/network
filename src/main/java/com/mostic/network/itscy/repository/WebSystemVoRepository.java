package com.mostic.network.itscy.repository;

import com.mostic.network.itscy.domain.WebSystemVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author LIQing
 * @create 2017-09-19 13:13
 */
public interface WebSystemVoRepository extends JpaRepository<WebSystemVo, Integer> {

    // 按照系统分组选出最近的状态
    String lastStateGroupBySystemId = "SELECT a.* FROM itscy_v_web_system a INNER JOIN " +
            "(SELECT system_id , MAX(scan_create_time) scan_create_time FROM itscy_v_web_system GROUP BY system_id) b " +
            "ON a.system_id = b.system_id AND a.scan_create_time = b.scan_create_time ";

    /**
     * 选出每个系统最新的安全测试结果（日期最大）
     * 按systemId分组，选出每组中scanCreateTime最大的数据
     *
     * @param pageable
     * @return
     */
    @Query(value = lastStateGroupBySystemId + "ORDER BY ?#{#pageable}",
            countQuery = "SELECT COUNT(*) FROM (SELECT DISTINCT system_id FROM itscy_v_web_system ) b",
            nativeQuery = true)
    // 分页（未使用，已经调通）
    Page<WebSystemVo> findPageGroupBySystemIdAndLastState(Pageable pageable);

    // 不分页（使用）
    @Query(value = lastStateGroupBySystemId + "ORDER BY scan_create_time DESC", nativeQuery = true)
    List<WebSystemVo> findGroupBySystemIdAndLastState();
}
