package com.mostic.network.itscy.service;

import com.mostic.network.common.enums.MessageEnums;
import com.mostic.network.common.properties.GloablProperties;
import com.mostic.network.common.service.BaseService;
import com.mostic.network.common.util.FileUpDownUtil;
import com.mostic.network.common.util.FileUpDownUtilException;
import com.mostic.network.common.web.AjaxResult;
import com.mostic.network.itscy.domain.WebScan;
import com.mostic.network.itscy.domain.WebScanFile;
import com.mostic.network.itscy.domain.WebScanRecord;
import com.mostic.network.itscy.enums.WebScanFileTypeEnum;
import com.mostic.network.itscy.enums.WebScanStateEnum;
import com.mostic.network.itscy.exception.ItscyException;
import com.mostic.network.itscy.repository.WebScanFileRepository;
import com.mostic.network.itscy.repository.WebScanRecordRepository;
import com.mostic.network.itscy.repository.WebScanRepository;
import com.mostic.network.itscy.util.OfficeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 状态相关业务
 *
 * @author LIQing
 * @create 2017-09-19 13:21
 */
@Service
public class WebScanService extends BaseService {

    private final static Logger logger = LoggerFactory.getLogger(WebScanService.class);

    @Autowired
    private WebScanRepository webScanRepository;

    @Autowired
    private WebScanFileRepository webScanFileRepository;

    @Autowired
    private WebScanRecordRepository webScanRecordRepository;

    @Autowired
    private GloablProperties gloablProperties;

    @Autowired
    private ConvertTaskService convertTaskService;

    /**
     * 根据systemId查询一个系统的所有安全测试状态
     *
     * @param systemId
     * @return
     */
    public List<WebScan> listScanBySystemId(String systemId) {
        return webScanRepository.findBySystemIdOrderByCreateTimeAsc(systemId);
    }

    /**
     * 为系统添加一条状态
     *
     * @param webScan
     * @param file
     * @return
     */
    public AjaxResult saveScan(WebScan webScan, MultipartFile file)
            throws FileUpDownUtilException, ItscyException {
        try {
            // 检查并上传文件，返回值格式：子目录 + 文件名
            String fileRelativePath = FileUpDownUtil.checkAndUploadFile(gloablProperties.getItscyRoot(), file, Arrays.asList("jpg", "png", "doc", "docx", "wps", "pdf"));

            // 判断时间是否为空
            if (null == webScan.getCreateTime()) {
                webScan.setCreateTime(new Date());
            }

            // 是否上传文件，未上传文件设为0，否则设为1
            int fileCount = fileRelativePath.equals(FileUpDownUtil.EMPTY) ? 0 : 1;
            webScan.setFileCount(fileCount);

            saveScanAndFileAndRecord(webScan, webScan.getFileType(), fileRelativePath);

            return new AjaxResult(true, MessageEnums.SAVE_SUCCESS);
        } catch (FileUpDownUtilException e) {
            logger.error("添加状态出现文件异常", e);
            throw e;
        } catch (Exception e) {
            logger.error("添加状态出现异常", e);
            throw new ItscyException(MessageEnums.SYSTEM_ERROR, e);
        }
    }

    /**
     * 保存状态、文件记录和测试记录
     *
     * @param webScan
     * @param fileType
     * @param fileRelativePath
     */
    public void saveScanAndFileAndRecord(WebScan webScan, String fileType, String fileRelativePath) {
        webScan.setFileExtension(webScan.getFileCount() == 0 ? "" : FileUpDownUtil.getFileExtension(fileRelativePath));
        webScan = webScanRepository.save(webScan);
        // 若上传了文件，则保存记录
        if (webScan.getFileCount() > 0) {
            WebScanFile webScanFile = new WebScanFile(webScan.getScanId(), fileRelativePath, fileType, webScan.getCreateTime());
            webScanFileRepository.save(webScanFile);
        }

        WebScanRecord webScanRecord = null;
        if (webScan.getState().equals(WebScanStateEnum.BUG.getState())) {
            webScanRecord = new WebScanRecord(webScan);
        } else if (webScan.getState().equals(WebScanStateEnum.SUCCESS.getState())) {
            webScan.setInfo(WebScanStateEnum.SUCCESS.getState());
            webScanRecord = new WebScanRecord(webScan);
        }
        if (webScanRecord != null) {
            webScanRecordRepository.save(webScanRecord);
        }

        // 执行word转pdf
        convertTaskService.convertPdfByWord(webScan.getScanId());
    }

    /**
     * 删除一条状态和对应的文件记录
     *
     * @param scanId
     * @return
     * @throws ItscyException
     */
    @Transactional
    public AjaxResult removeScan(Integer scanId) throws ItscyException {
        try {
            WebScan webScan = webScanRepository.findOne(scanId);
            // 若webscan不存在，返回错误
            if (null == webScan) {
                return new AjaxResult(false, MessageEnums.NOT_FOUND);
            }

            // 删除状态、文件记录
            webScanRepository.delete(scanId);
            webScanFileRepository.deleteByScanId(scanId);

            // 判断是否需要删除检测记录
            if (webScan.getState().equals(WebScanStateEnum.BUG.getState()) ||
                    webScan.getState().equals(WebScanStateEnum.SUCCESS.getState())) {
                webScanRecordRepository.deleteByScanId(scanId);
            }

            return new AjaxResult(true, MessageEnums.DELETE_SUCCESS);
        } catch (Exception e) {
            logger.error("删除出现系统异常", e);
            throw new ItscyException(MessageEnums.SYSTEM_ERROR, e);
        }
    }

    /**
     * 为一个状态上传文件
     *
     * @param scanId
     * @param fileType
     * @param file
     * @return
     * @throws FileUpDownUtilException
     * @throws ItscyException
     */
    public AjaxResult uploadScanFile(Integer scanId, String fileType, MultipartFile file)
            throws FileUpDownUtilException, ItscyException {
        try {
            // 是否存在该状态记录
            WebScan webScan = webScanRepository.findOne(scanId);
            if (null == webScan) {
                return new AjaxResult(false, MessageEnums.NOT_FOUND);
            }

            // 检查并上传文件，返回值格式：子目录 + 文件名
            String fileRelativePath = FileUpDownUtil.checkAndUploadFile(gloablProperties.getItscyRoot(), file, Arrays.asList("jpg", "png", "doc", "docx", "wps", "pdf"));
            if (fileRelativePath.equals(FileUpDownUtil.EMPTY)) {
                return new AjaxResult(false, MessageEnums.UPLOAD_ERROR);
            }

            // 获取状态记录对应的文件记录
            WebScanFile webScanFile = webScanFileRepository.findByScanId(scanId);

            if (webScanFile == null) { // 文件记录是空，先初始化
                webScanFile = new WebScanFile();
                webScanFile.setScanId(scanId);
            }

            // 设置相关信息后，保存
            webScanFile.setName(fileRelativePath);
            webScanFile.setType(fileType);
            webScanFile.setCreateTime(new Date());
            webScanFileRepository.save(webScanFile);

            // 更新状态的文件数
            webScan.setFileCount(1);
            webScan.setFileExtension(FileUpDownUtil.getFileExtension(fileRelativePath));
            webScanRepository.save(webScan);

            // 执行word转pdf
            convertTaskService.convertPdfByWord(webScan.getScanId());

            return new AjaxResult(true, MessageEnums.UPLOAD_SUCCESS);
        } catch (FileUpDownUtilException e) {
            throw e;
        } catch (Exception e) {
            throw new ItscyException(MessageEnums.SYSTEM_ERROR, e);
        }
    }

    /**
     * 图片预览
     *
     * @param scanId
     * @return
     * @throws IOException
     */
    public FileInputStream viewPhotoByScanId(Integer scanId) throws IOException {
        WebScanFile webScanFile = webScanFileRepository.findByScanId(scanId);
        if (webScanFile == null) {
            return null;
        }
        File file = new File(gloablProperties.getItscyRoot() + webScanFile.getName());
        return new FileInputStream(file);
    }

    /**
     * 文件下载
     *
     * @param scanId
     * @return
     */
    public ResponseEntity<byte[]> downScanFileByScanId(Integer scanId) {
        WebScanFile webScanFile = webScanFileRepository.findByScanId(scanId);
        if (webScanFile == null) {
            return null;
        }
        File file = new File(gloablProperties.getItscyRoot() + webScanFile.getName());
        return FileUpDownUtil.downloadFile(webScanFile.getName(), file);
    }

    /**
     * 获取系统检测状态码表
     *
     * @return
     */
    public List<WebScanStateEnum> listStateEnums() {
        return Arrays.asList(WebScanStateEnum.values());
    }

    /**
     * 获取上传文件类型
     *
     * @return
     */
    public List<WebScanFileTypeEnum> listScanFileTypEnums() {
        return Arrays.asList(WebScanFileTypeEnum.values());
    }

    /**
     * 将word转成html，用于页面预览
     *
     * @param scanId
     * @return
     */
    public File getPreviewPdf(Integer scanId) {
        WebScanFile webScanFile = webScanFileRepository.findByScanId(scanId);
        if (webScanFile != null) {
            return new File(gloablProperties.getItscyRoot() + webScanFile.getPreviewName());
        }
        return null;
    }
}
