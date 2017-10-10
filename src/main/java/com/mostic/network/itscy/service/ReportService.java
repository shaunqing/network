package com.mostic.network.itscy.service;

import com.mostic.network.common.properties.GloablProperties;
import com.mostic.network.common.service.BaseService;
import com.mostic.network.common.util.FileUpDownUtil;
import com.mostic.network.common.web.AjaxResult;
import com.mostic.network.itscy.domain.view.ScanReport;
import com.mostic.network.itscy.dto.WebScanReport;
import com.mostic.network.itscy.util.ScanReportExcel;
import com.mostic.network.itscy.repository.WebScanRecordRepository;
import com.mostic.network.itscy.repository.WebScanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计与导出
 *
 * @author LIQing
 * @create 2017-09-21 16:23
 */
@Service
public class ReportService extends BaseService {

    private final static Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    private WebScanRepository webScanRepository;

    @Autowired
    private WebScanRecordRepository webScanRecordRepository;

    @Autowired
    private GloablProperties gloablProperties;

    /**
     * 按月统计主动提交的测试次数
     * 这个方法不准
     *
     * @return
     */
    @Deprecated
    public AjaxResult stateInitiativeReport() {
        List<WebScanReport> scans = new ArrayList<>();

        List<Object[]> list = webScanRepository.countScanNumsByMonth();
        for (Object[] o : list) {
            WebScanReport scan = new WebScanReport(String.valueOf(o[0]), String.valueOf(o[1]));
            scans.add(scan);
        }
        return new AjaxResult(true, scans);
    }

    /**
     * 按月统计系统检测次数（根据WebScanRecord表）
     *
     * @return
     */
    public List<WebScanReport> statsScanTimes() {
        List<WebScanReport> scans = new ArrayList<>();

        // 封装sql结果
        List<Object[]> list = webScanRecordRepository.countScanNumsByMonth();
        for (Object[] o : list) {
            WebScanReport scan = new WebScanReport(String.valueOf(o[0]), String.valueOf(o[1]));
            scans.add(scan);
        }
        return scans;
    }

    /**
     * 按月统计系统检测次数，用于Echarts显示
     *
     * @return
     */
    public List<String[]> statsScanTimesForEcharts() {
        List<String[]> scans = new ArrayList<>();

        // 封装sql结果
        List<Object[]> list = webScanRecordRepository.countScanNumsByMonth();

        // 初始化Echarts的横纵坐标
        String[] monthArray = new String[list.size()]; // 横坐标
        String[] countArray = new String[list.size()]; // 纵坐标

        int index = 0;
        for (Object[] o : list) {
            monthArray[index] = String.valueOf(o[0]);
            countArray[index] = String.valueOf(o[1]);
            ++index;
        }
        scans.add(monthArray);
        scans.add(countArray);
        return scans;
    }


    /**
     * 显示系统所有检测记录
     *
     * @return
     */
    public List<ScanReport> listRecord() {
        return webScanRecordRepository.reportScans();
    }

    /**
     * 导出月份区间的检测记录的Excel
     *
     * @param start
     * @param end
     * @return
     */
    public ResponseEntity<byte[]> exportRecord(String start, String end) {
        List<ScanReport> list = webScanRecordRepository.reportScansBetweenMonths(start, end);

        // 生成Excel，获取文件名
        String fileNameSub = start + "至" + end + "系统检测记录";
        String fileName = ScanReportExcel.write(list, gloablProperties.getItscyExport(), fileNameSub);
        if (fileName != null) {
            File file = new File(gloablProperties.getItscyExport() + fileName);
            return FileUpDownUtil.downloadFile(fileName, file);
        } else {
            return null;
        }
    }

}
