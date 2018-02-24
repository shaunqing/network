package com.mostic.network.itscy.util;

import java.io.*;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * Office工具
 * Created by LIQing
 * 2017/10/2 20:03
 */

public class OfficeUtil {

    private final static Logger log = LoggerFactory.getLogger(OfficeUtil.class);

    private static final int wdFormatPDF = 17;// PDF 格式

    /**
     * word转html
     * @param path
     * @param fileName
     * @return
     * @throws Throwable
     */
    public static String wordToHtml(String path, String fileName) throws Throwable {
        InputStream input = new FileInputStream(path + fileName);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType,
                                      String suggestedName, float widthInches, float heightInches) {
                return "img/" + suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        List pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(path
                            + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "gbk");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toString("UTF-8"));
        System.out.println(content);
        return content;
//        FileUtils.writeStringToFile(new File(path, "人员选择系分.html"), content, "utf-8");
    }

    /**
     * word转pdf，返回pdf文件相对路径（pdf/xxx.pdf）
     * @param rootPath 上传文件保存路径
     * @param fileRelativePath word文件相对路径（word/xxx.doc）
     * @param pdfPath pdf保存相对路径
     * @return
     */
    public static String generatePdfByWord(String rootPath, String fileRelativePath, String pdfPath) {
        log.info("启动Word...");
        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        Dispatch doc = null;
        String pdfRelativePath = "";
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();

            // 打开原word文档
            doc = Dispatch.call(docs, "Open", rootPath + fileRelativePath).toDispatch();
            // 生成pdf文件的相对路径（pdf/xxx.pdf）
            pdfRelativePath = initPdf(pdfPath, fileRelativePath);
            // 转换
            Dispatch.call(doc, "SaveAs", rootPath + pdfRelativePath, wdFormatPDF);

            long end = System.currentTimeMillis();
            log.info("转换完成..用时：" + (end - start) + "ms.");
        } catch (Exception e) {
            log.error("========Error:文档转换失败：" + e.getMessage());
        } finally {
            try {
                Dispatch.call(doc, "Close", false);
            } catch (Exception e) {
            }
            log.info("关闭文档");
            if (app != null)
                app.invoke("Quit", new Variant[]{});
        }
        ComThread.Release(); //如果没有这句话,winword.exe进程将不会关闭
        return pdfRelativePath;
    }

    /**
     * 初始化Pdf文件
     * @param pdfPath pdf保存相对路径
     * @param fileRelativePath word文件（word/xxx.doc）
     * @return
     */
    private static String initPdf(String pdfPath, String fileRelativePath) {
        // 获得doc的文件名，不带后缀
        String pdfName = fileRelativePath.substring(fileRelativePath.lastIndexOf('/') + 1, fileRelativePath.lastIndexOf('.')) + ".pdf";
        String toFileName = pdfPath + pdfName;
        File tofile = new File(toFileName);
        if (tofile.exists()) {
            tofile.delete();
        }
        return toFileName;
    }

}
