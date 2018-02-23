package com.mostic.network.common.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件上传下载工具类
 *
 * @author LIQing
 * @version 2017-6-26
 */
public class FileUpDownUtil {

    public final static String EMPTY = "EMPTY";

    private final static String UPLOAD_ERROR = "文件上传异常";
    private final static String FILE_NAME_ERROR = "文件名不合法";
    private final static String FILE_EXTENSION_ERROR = "文件类型不合法";

    private final static Logger logger = LoggerFactory.getLogger(FileUpDownUtil.class);

    // 保存正确文件头编码
    private final static Map<String, List<String>> FILE_CODE_MAP = new HashMap<>();

    // 非法16进制字符
    private final static List<String> FORBIDDEN_HEX = Arrays.asList("ff");

    static {
        // 初始化文件类型信息
        initFileCode();
    }

    // 各种文件的头信息
    private static void initFileCode() {
        FILE_CODE_MAP.put("ffd8ffe000104a464946", Arrays.asList("jpg"));
        FILE_CODE_MAP.put("89504e470d0a1a0a0000", Arrays.asList("png"));
        FILE_CODE_MAP.put("d0cf11e0a1b11ae10000", Arrays.asList("doc", "xls", "wps"));
        FILE_CODE_MAP.put("504b0304140006000800", Arrays.asList("docx", "xlsx"));
        FILE_CODE_MAP.put("255044462d312e350d0a", Arrays.asList("pdf"));
        FILE_CODE_MAP.put("526172211a0700cf9073", Arrays.asList("rar"));
    }

    /**
     * 检查并上传文件
     *
     * @param root           保存路径
     * @param multipartFile  上传文件
     * @param allowExtension 允许的后缀
     * @return 成功：返回文件名（若file是空，返回EMPTY）；失败：抛出FileUpDownUtilException异常
     */
    public static String checkAndUploadFile(String root, MultipartFile multipartFile, List<String> allowExtension) {
        try {
            if (multipartFile == null || multipartFile.getSize() == 0) {
                return EMPTY;
            }

            // 检查文件头与后缀名是否匹配
            String fileExtension = checkFileType(multipartFile.getInputStream(), multipartFile.getOriginalFilename());

            // 检查后缀是否在允许范围内
            if (!allowExtension.contains(fileExtension)) {
                throw new FileUpDownUtilException(FILE_EXTENSION_ERROR);
            }

            makeRootFolder(root); // 创建文件夹

            String newFileName = makeRandomName() + multipartFile.getOriginalFilename(); // 生成文件名，保留原始文件名（业务需要）
            String newFilePath = root + newFileName; // 拼接路径
            multipartFile.transferTo(new File(newFilePath)); // 上传

            return newFileName;
        } catch (FileUpDownUtilException e) {
            throw e;
        } catch (IOException e) {
            logger.error("获取文件异常", e);
            throw new FileUpDownUtilException(UPLOAD_ERROR);
        } catch (IllegalStateException e) {
            logger.error("上传文件错误", e);
            throw new FileUpDownUtilException(UPLOAD_ERROR);
        }
    }

    /* 生成保存文件夹 */
    private static void makeRootFolder(String root) {
        File rootFolder = new File(root);
        if (!rootFolder.exists() || !rootFolder.isDirectory()) {
            rootFolder.mkdirs();
        }
    }

    /*生成随机文件名*/
    private static String makeRandomName() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date()) + "_" + new Random().nextInt(10) + "_";
    }

    /**
     * 检查文件头和后缀是否匹配
     *
     * @param is       文件流
     * @param fileName 文件名称
     * @return 成功：返回文件后缀；失败：抛出FileUpDownUtilException异常
     */
    public static String checkFileType(InputStream is, String fileName) {
        if (is == null) {
            throw new FileUpDownUtilException(UPLOAD_ERROR);
        }

        // 检测文件名是否含有非法16进制字符
        /*
        if (hasForbiddenHex(fileName)) {
            throw new FileUpDownUtilException(FILE_NAME_ERROR);
        }
        */

        // 没有.或者有多个.都算不合法
        /*
        if (!fileName.contains(".") || fileName.split("\\.").length > 2) {
            throw new FileUpDownUtilException(FILE_NAME_ERROR);
        }
        */

        try {
            byte[] b = new byte[10];
            is.read(b, 0, b.length);

            String fileCode = bytesToHexString(b); // 获取文件头
            String fileExtension = getFileExtension(fileName); // 获取文件后缀

            if (FILE_CODE_MAP.get(fileCode).contains(fileExtension)) {
                return fileExtension;
            } else {
                throw new FileUpDownUtilException(FILE_EXTENSION_ERROR);
            }
        } catch (Exception e) {
            logger.error("获取文件InputStream失败", e);
            throw new FileUpDownUtilException(UPLOAD_ERROR);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    /*检测文件名是否含有非法16进制字符*/
    private static boolean hasForbiddenHex(String fileName) {
        // 获取文件名的16进制
        String hexFileName = toHexString(fileName);

        // 是否存在进制的16进制（lambda表达式使用 liqing）
        return FORBIDDEN_HEX.stream().anyMatch(hex -> hexFileName.contains(hex));
    }

    /*字符串转16进制*/
    private static String toHexString(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int ch = (int) str.charAt(i);
            String s4 = Integer.toHexString(ch);
            sb.append(s4);
        }
        return sb.toString();
    }

    /* 获取文件头 */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /* 获取文件后缀 */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
    }

    /**
     * 文件下载（Controller层返回类型为ResponseEntity<byte[]>）
     *
     * @param fileName
     * @param file
     * @return
     */
    public static ResponseEntity<byte[]> downloadFile(String fileName, File file) {
        ResponseEntity<byte[]> response = null;
        String dfileName = "";
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error("文件名转码错误", e);
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        try {
            // 将HttpStatus.CREATED改为HttpStatus.OK，用于支持ie下载
            response = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("文件下载异常", e);
            return null;
        }
        return response;
    }

    public static void main(String[] args) {
        System.out.println(hasForbiddenHex("20170904_163317_170904service、finance、program复测.jpg"));
    }
}
