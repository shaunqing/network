package com.mostic.network.itscy.repository;

import com.mostic.network.common.BaseTest;
import com.mostic.network.itscy.domain.view.ScanReport;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author LIQing
 * @create 2017-09-22 12:52
 */
public class WebScanRecordRepositoryTest extends BaseTest {
    @Autowired
    private WebScanRecordRepository recordRepository;


    public void findTest() throws Exception {
        List<ScanReport> list = recordRepository.reportScans();
        for (ScanReport report : list) {
//            logger.info(report.getSystemName() + "\t" + report.getScanInfo() + "\t" + report.getSystemId() + "\t" + report.getRecordId()
//            + "\t" + report.getScanCreateTime() + "\t" + report.getScanId());
            System.out.println(report.getRecordId() + "\t" + report.getSystemId() + "\t" +
                    report.getSystemName() + "\t" + report.getLocalIp() + "\t" + report.getScanId() + "\t" + report.getScanCreateTime() + "\t" + report.getScanInfo());
        }
    }

    @Test
    public void tt() {
        List<ScanReport> list = recordRepository.reportScansBetweenMonths("2017-09","2017-10");
        Date d = list.get(0).getScanCreateTime();
        System.out.println("xxxxxxx");
        System.out.println(DateFormatUtils.format(d,"yyyy-MM-dd HH:mm"));
    }



}