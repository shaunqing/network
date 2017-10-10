package com.mostic.network.itscy.service;

import com.mostic.network.common.enums.MessageEnums;
import com.mostic.network.common.properties.GloablProperties;
import com.mostic.network.common.service.BaseService;
import com.mostic.network.common.util.FileUpDownUtil;
import com.mostic.network.common.util.FileUpDownUtilException;
import com.mostic.network.common.util.SnowflakeIdWorker;
import com.mostic.network.common.util.SnowflakedEnums;
import com.mostic.network.common.web.AjaxResult;
import com.mostic.network.itscy.domain.WebScan;
import com.mostic.network.itscy.domain.WebSystem;
import com.mostic.network.itscy.domain.WebSystemVo;
import com.mostic.network.itscy.exception.ItscyException;
import com.mostic.network.itscy.repository.WebSystemRepository;
import com.mostic.network.itscy.repository.WebSystemVoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 系统相关业务
 *
 * @author LIQing
 * @create 2017-09-19 13:22
 */
@Service
public class WebSystemService extends BaseService {

    private final static Logger logger = LoggerFactory.getLogger(WebSystemService.class);

    @Autowired
    private WebSystemVoRepository webSystemVoRepository;

    @Autowired
    private WebSystemRepository webSystemRepository;

    @Autowired
    private GloablProperties gloablProperties;

    @Autowired
    private WebScanService webScanService;

    /**
     * 分页显示所有系统的信息
     *
     * @return
     */
    public Page<WebSystemVo> pageSystemVo() {
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "web_create_time"));
        Page<WebSystemVo> page = webSystemVoRepository.findPageGroupBySystemIdAndLastState(pageable);
        return page;
    }

    /**
     * 显示所有系统，以及系统最新状态
     *
     * @return
     */
    public List<WebSystemVo> listWebSystemVo() {
        return webSystemVoRepository.findGroupBySystemIdAndLastState();
    }

    /**
     * 根据Id显示一个系统的信息
     *
     * @param systemId
     * @return
     */
    public WebSystem getWebSystemById(String systemId) {
        WebSystem webSystem = webSystemRepository.findOne(systemId);
        if (webSystem == null) {
            return null;
        }
        webSystem.setUserId(null); // 不显示用户Id
        return webSystem;
    }

    /**
     * 添加一个系统信息，保存上传的文件，生成webscan和webscanfile记录
     *
     * @param webSystem 新添加的系统
     * @param webScan   系统的状态(包含上传文件的类型fileType字段)
     * @param file      上传的文件
     * @return
     */
    @Transactional
    public AjaxResult saveWebSystemAndScan(WebSystem webSystem, WebScan webScan, MultipartFile file)
            throws FileUpDownUtilException, ItscyException {
        try {
            // 检查并上传文件
            String newFileName = FileUpDownUtil.checkAndUploadFile(gloablProperties.getItscyRoot(), file, Arrays.asList("jpg", "png","doc"));

            // 判断时间是否为空
            if (null == webSystem.getCreateTime()) {
                webSystem.setCreateTime(new Date());
            }

            // 保存新创建的系统到WebSystem
            webSystem.setSystemId(String.valueOf(new SnowflakeIdWorker(SnowflakedEnums.ITSCY).nextId()));
            webSystem = webSystemRepository.save(webSystem);

            // 保存系统的状态到WebScan
            int fileCount = newFileName.equals(FileUpDownUtil.EMPTY) ? 0 : 1; // 上传文件：1；未上传：2
            webScan.setFileCount(fileCount);
            webScan.setSystemId(webSystem.getSystemId());
            webScan.setCreateTime(webSystem.getCreateTime());

            // 保存状态、文件记录和测试记录
            webScanService.saveScanAndFileAndRecord(webScan, webScan.getFileType(), newFileName);

            return new AjaxResult(true, MessageEnums.SAVE_SUCCESS);
        } catch (FileUpDownUtilException e) {
            logger.error("添加出现文件上传异常", e);
            throw e;
        } catch (Exception e) {
            logger.error("添加出现系统异常", e);
            throw new ItscyException(MessageEnums.SYSTEM_ERROR, e);
        }
    }

    /**
     * 更新一个系统信息
     *
     * @param webSystem
     * @return
     */
    public AjaxResult updateWebSystem(WebSystem webSystem) {
        try {
            webSystemRepository.save(webSystem);
            return new AjaxResult(true, MessageEnums.UPDATE_SUCCESS);
        } catch (Exception e) {
            throw new ItscyException(MessageEnums.SYSTEM_ERROR, e);
        }
    }
}
