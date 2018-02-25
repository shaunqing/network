package com.mostic.network.itscy.service;

import com.mostic.network.common.properties.GloablProperties;
import com.mostic.network.common.util.FileUpDownUtil;
import com.mostic.network.itscy.domain.WebScanFile;
import com.mostic.network.itscy.repository.WebScanFileRepository;
import com.mostic.network.itscy.util.OfficeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * word转pdf线程
 * Created by LIQing
 * 2018/2/25 16:23
 */
@Service
public class ConvertTaskService {

    @Autowired
    private WebScanFileRepository webScanFileRepository;

    @Autowired
    private GloablProperties gloablProperties;


    @Async
    public void executeAsyncTask(Integer i){
        // 通过@Async注解表明该方法是一个异步方法，如果注解在类级别，表明该类下所有方法都是异步方法，
        // 而这里的方法自动被注入使用ThreadPoolTaskExecutor 作为 TaskExecutor
        System.out.println("执行异步任务：" + i);
    }

    /**
     * 将word文件转为pdf，并保存到web_scan_file表中的previewName字段
     * @param scanId
     */
    @Async
    public void convertPdfByWord(Integer scanId) {
        WebScanFile webScanFile = webScanFileRepository.findByScanId(scanId);
        if (webScanFile == null) {
            return;
        }
        String fileRelativePath = webScanFile.getName();
        if (!StringUtils.isEmpty(fileRelativePath) && (fileRelativePath.endsWith("doc") || fileRelativePath.endsWith("docx"))) {
            // 2. 执行转换程序
            String pdfRelativePath = OfficeUtil.generatePdfByWord(
                    gloablProperties.getItscyRoot(), fileRelativePath, FileUpDownUtil.SUBFOLDER_PDF);

            // 3 执行成功，将pdf相对路径写入previewName字段
            if (!pdfRelativePath.equals("")) {
                webScanFile.setPreviewName(pdfRelativePath);
                webScanFileRepository.save(webScanFile);
            }
        }
    }
}
