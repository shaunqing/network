package com.mostic.network.common.enums;

/**
 * @author LIQing
 * @create 2017-09-21 13:30
 */
public enum MessageEnums {
    SYSTEM_ERROR(-1, "执行异常，请稍后重试！"),
    SAVE_SUCCESS(100, "保存成功！"),
    UPDATE_SUCCESS(200, "更新成功！"),
    DELETE_SUCCESS(300, "删除成功！"),
    UPLOAD_SUCCESS(500, "上传成功！"),
    UPLOAD_ERROR(501, "上传失败！"),
    NOT_FOUND(404, "数据不存在！");


    private int code;
    private String msg;

    MessageEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
