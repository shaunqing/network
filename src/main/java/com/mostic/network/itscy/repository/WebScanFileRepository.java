package com.mostic.network.itscy.repository;

import com.mostic.network.itscy.domain.WebScanFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LIQing
 * @create 2017-09-19 13:07
 */
public interface WebScanFileRepository extends JpaRepository<WebScanFile, Integer> {

    WebScanFile findByScanId(Integer scanId);

    Long deleteByScanId(Integer scanId);
}
