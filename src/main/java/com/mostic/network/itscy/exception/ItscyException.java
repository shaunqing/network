package com.mostic.network.itscy.exception;

import com.mostic.network.common.enums.MessageEnums;

/**
 * Itscy模块异常类
 *
 * @author LIQing
 * @create 2017-09-21 13:52
 */
public class ItscyException extends RuntimeException {
    public ItscyException(String message) {
        super(message);
    }

    public ItscyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItscyException(MessageEnums messageEnums) {
        super(messageEnums.getMsg());
    }

    public ItscyException(MessageEnums messageEnums, Throwable cause) {
        super(messageEnums.getMsg(), cause);
    }
}
