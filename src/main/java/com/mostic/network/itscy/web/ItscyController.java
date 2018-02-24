package com.mostic.network.itscy.web;

import com.mostic.network.common.web.AjaxResult;
import com.mostic.network.common.web.BaseController;
import com.mostic.network.itscy.domain.WebScan;
import com.mostic.network.itscy.domain.WebSystem;
import com.mostic.network.itscy.domain.WebSystemVo;
import com.mostic.network.itscy.service.ReportService;
import com.mostic.network.itscy.service.WebScanService;
import com.mostic.network.itscy.service.WebSystemService;
import com.mostic.network.itscy.util.OfficeUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;

/**
 * @author LIQing
 * @create 2017-09-19 15:11
 */
@Controller
@RequestMapping("/itscy")
public class ItscyController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ItscyController.class);

    @Autowired
    private WebSystemService webSystemService;

    @Autowired
    private WebScanService webScanService;

    @Autowired
    private ReportService reportService;

    // itscy模块的页面根目录
    private String itscyPageRoot = "itscy";

    /**
     * 页面：显示所有系统以及最新状态
     *
     * @param model
     * @return
     */
    @GetMapping("/page/system")
    public String listWebSystemVo(Model model) {
        model.addAttribute("systemVo", webSystemService.listWebSystemVo());
        // 测试中
        List<WebSystemVo> list = webSystemService.listWebSystemVoScaning();
        int count = list.size();
        model.addAttribute("systemVoScaning", list);
        model.addAttribute("scanCount", count);

        // 待修复
        list = webSystemService.listWebSystemVoBug();
        count = list.size();
        model.addAttribute("systemVoBug", list);
        model.addAttribute("bugCount", count);

        return itscyPageRoot + "/system/list";
    }

    // 页面：添加系统
    @GetMapping("/page/system/create")
    public String pageSystemCreate(Model model) {
        model.addAttribute("state", webScanService.listStateEnums());
        model.addAttribute("fileType", webScanService.listScanFileTypEnums());
        return itscyPageRoot + "/system/create";
    }

    /**
     * 保存新建的系统
     *
     * @param webSystem
     * @param webScan
     * @param file
     * @return
     */
    @PostMapping("/system")
    @ResponseBody
    public AjaxResult saveSystem(@Valid WebSystem webSystem, @Valid WebScan webScan,
                                 @RequestParam("file") MultipartFile file) {
        try {
            return webSystemService.saveWebSystemAndScan(webSystem, webScan, file);
        } catch (Exception e) {
            return new AjaxResult(false, e.getMessage());
        }
    }

    // 页面：修改系统
    @GetMapping("/page/system/{systemId}/update")
    public String pageSystemUpdate(@PathVariable("systemId") String systemId, Model model) {
        model.addAttribute("webSystem", webSystemService.getWebSystemById(systemId));
        return itscyPageRoot + "/system/update";
    }

    /**
     * 修改系统信息
     *
     * @param systemId
     * @param webSystem
     * @return
     */
    @PostMapping("/system/{systemId}")
    @ResponseBody
    public AjaxResult updateSystem(@PathVariable("systemId") String systemId, @Valid WebSystem webSystem) {
        try {
            webSystem.setSystemId(systemId);
            return webSystemService.updateWebSystem(webSystem);
        } catch (Exception e) {
            return new AjaxResult(false, e.getMessage());
        }
    }

    /**
     * 页面：显示一个系统的信息以及所有状态
     *
     * @param systemId
     * @param model
     * @return
     */
    @GetMapping("/page/system/{systemId}")
    public String pageSystemForm(@PathVariable("systemId") String systemId, Model model) {
        model.addAttribute("webSystem", webSystemService.getWebSystemById(systemId));
        model.addAttribute("webScan", webScanService.listScanBySystemId(systemId));
        return itscyPageRoot + "/system/info";
    }

    /**
     * 显示一个系统的所有状态
     *
     * @param systemId
     * @return
     */
    @GetMapping("/system/{systemId}/scan")
    @ResponseBody
    public AjaxResult listSystemScan(@PathVariable("systemId") String systemId) {
        return new AjaxResult(true, webScanService.listScanBySystemId(systemId));
    }

    /**
     * 页面：添加系统状态
     * 系统检测的状态码表
     *
     * @param systemId
     * @param model
     * @return
     */
    @GetMapping("/page/{systemId}/scan/create")
    public String pageScanCreate(@PathVariable("systemId") String systemId, Model model) {
        model.addAttribute("systemId", systemId);
        model.addAttribute("state", webScanService.listStateEnums());
        model.addAttribute("fileType", webScanService.listScanFileTypEnums());
        return itscyPageRoot + "/scan/create";
    }

    /**
     * 添加系统检测状态
     *
     * @param webScan
     * @param file
     * @return
     */
    @PostMapping("/system/{systemId}/scan")
    @ResponseBody
    public AjaxResult saveWebScan(@PathVariable("systemId") String systemId,
                                  @Valid WebScan webScan, @RequestParam("file") MultipartFile file) {
        try {
            webScan.setSystemId(systemId);
            return webScanService.saveScan(webScan, file);
        } catch (Exception e) {
            return new AjaxResult(false, e.getMessage());
        }
    }

    /**
     * 删除系统检测状态
     *
     * @param scanId
     * @return
     */
    @DeleteMapping("/scan/{scanId}")
    @ResponseBody
    public AjaxResult removeWebScan(@PathVariable("scanId") Integer scanId) {
        try {
            return webScanService.removeScan(scanId);
        } catch (Exception e) {
            return new AjaxResult(false, e.getMessage());
        }
    }

    /**
     * 页面：上传状态的附件
     *
     * @param scanId
     * @param model
     * @return
     */
    @GetMapping("/page/scan/{scanId}/file/upload")
    public String pageScanFileUpload(@PathVariable("scanId") Integer scanId, Model model) {
        model.addAttribute("scanId", scanId);
        model.addAttribute("fileType", webScanService.listScanFileTypEnums());
        return itscyPageRoot + "/scan/file_upload";
    }

    /**
     * 上传附件
     *
     * @param scanId
     * @param fileType
     * @param file
     * @return
     */
    @PostMapping("scan/{scanId}/file")
    @ResponseBody
    public AjaxResult uploadWebScanFile(@PathVariable("scanId") Integer scanId,
                                        @RequestParam("fileType") String fileType,
                                        @RequestParam("file") MultipartFile file) {
        try {
            return webScanService.uploadScanFile(scanId, fileType, file);
        } catch (Exception e) {
            return new AjaxResult(false, e.getMessage());
        }
    }

    /**
     * 页面：预览图片
     *
     * @param scanId
     * @param model
     * @return
     */
    @GetMapping("/page/scan/{scanId}/photo")
    public String photo(@PathVariable("scanId") Integer scanId, Model model) {
        model.addAttribute("scanId", scanId);
        return itscyPageRoot + "/scan/photo";
    }

    /**
     * 预览图片
     *
     * @param scanId
     * @param response
     * @throws IOException
     */
    @GetMapping("/scan/{scanId}/photo")
    public void viewWebScanPhoto(@PathVariable("scanId") Integer scanId, HttpServletResponse response) throws IOException {
        // java7 try-with-resources
        response.setContentType("application/octet-stream;charset=UTF-8");
        try (FileInputStream fin = webScanService.viewPhotoByScanId(scanId);
             OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());) {

            int i = fin.available();
            byte[] data = new byte[i];
            fin.read(data);

            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * 预览word文件（实际是pdf文件）
     * @param response
     */
    @GetMapping("/page/scan/{scanId}/preview")
    public void previewPdf(@PathVariable("scanId") Integer scanId, HttpServletResponse response) {
        try {
            File file = webScanService.getPreviewPdf(scanId);
            FileInputStream fileInputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment;fileName=test.pdf");
            response.setContentType("multipart/form-data");
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载系统检测状态的附件
     *
     * @param scanId
     * @return
     */
    @GetMapping("/scan/{scanId}/download")
    public ResponseEntity<byte[]> downloadWebScanFile(@PathVariable("scanId") Integer scanId) {
        return webScanService.downScanFileByScanId(scanId);
    }

    /**
     * 系统检测统计报告页面，显示所有系统的检测记录、月检测次数
     *
     * @return
     */
    @GetMapping("/page/report")
    public String pageReport(Model model) {
        model.addAttribute("records", reportService.listRecord());
        model.addAttribute("scans", reportService.statsScanTimes());
        return itscyPageRoot + "/report/main";
    }

    /**
     * 按月份区间导出系统的检测记录，并下载
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/record/download")
    public ResponseEntity<byte[]> downloadRecord(@RequestParam("start") String start,
                                                 @RequestParam("end") String end) {
        return reportService.exportRecord(start, end);
    }

    /**
     * 页面使用Echarts显示月检测次数
     *
     * @return
     */
    @GetMapping("/stats")
    @ResponseBody
    public AjaxResult statsForEcharts() {
        List<String[]> scans = reportService.statsScanTimesForEcharts();
        return new AjaxResult(true, scans);
    }




}
