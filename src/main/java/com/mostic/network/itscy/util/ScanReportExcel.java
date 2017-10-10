package com.mostic.network.itscy.util;

import com.mostic.network.common.util.ExcelUtil;
import com.mostic.network.itscy.domain.view.ScanReport;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * Created by LIQing
 * 2017/10/2 17:56
 */
public class ScanReportExcel {

    /**
     * 生成系统检测记录的Excel文件，返回文件名
     *
     * @param list 检测记录
     * @param root 文件保存路径
     * @return 文件名（错误返回null）
     */
    public static String write(List<ScanReport> list, String root, String fileNameSub) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = createSheet(workbook, null);
        CellStyle cellStyle = getCellStyle(workbook, false);

        for (int j = 0; j < list.size(); j++) {
            Row row = sheet.createRow(j + 1);
            row.setHeight((short) 500);
            ScanReport report = list.get(j);
            setRow(cellStyle, row, report, (j + 1));
        }

        setColumnWidth(sheet);

        try {
            return ExcelUtil.write(workbook, root, fileNameSub);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 创建sheet页，并命名
     *
     * @param workbook
     * @param sheetName
     * @return
     */
    private static Sheet createSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName == null || "".equals(sheetName) ? "Sheet" : sheetName);
        String[] header = new String[]{"序号", "系统编号", "系统名称", "内网IP", "测试状态", "测试时间"};
        generateHeader(workbook, sheet, header);
        return sheet;
    }

    /**
     * 写标题
     *
     * @param workbook
     * @param sheet
     * @param header
     */
    private static void generateHeader(Workbook workbook, Sheet sheet, String[] header) {
        CellStyle style = getCellStyle(workbook, true);
        Row row = sheet.createRow(0);
        row.setHeight((short) 500); // 设置行高
        for (int i = 0; i < header.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(style);
        }
    }

    /**
     * 设置单元格格式
     *
     * @param workbook
     * @param isHeader
     * @return
     */
    private static CellStyle getCellStyle(Workbook workbook, boolean isHeader) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setLocked(true);
        style.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        if (isHeader) {
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            Font font = workbook.createFont();
            font.setColor(IndexedColors.BLACK.index);
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);
        }
        return style;
    }

    /**
     * 封装一行数据
     *
     * @param style
     * @param row
     * @param report
     * @param number 序号
     */
    private static void setRow(CellStyle style, Row row, ScanReport report, int number) {
        Cell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue(number);

        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue(report.getSystemId());

        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue(report.getSystemName());

        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue(report.getLocalIp());

        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue(report.getScanInfo());

        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue(DateFormatUtils.format(report.getScanCreateTime(), "yyyy-MM-dd HH:mm"));
    }

    /**
     * 设置列宽
     *
     * @param sheet
     */
    private static void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 7000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 5000);
    }
}
