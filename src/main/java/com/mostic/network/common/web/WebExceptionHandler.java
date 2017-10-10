package com.mostic.network.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LIQing
 * @create 2017-09-19 14:55
 */
@ControllerAdvice
public class WebExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public AjaxResult handle(Exception e) {
        if (e instanceof BindException) {
            // @valid注解在controller中使用时，如果与@RequestParam或HttpServletRequest一同使用，
            // 会导致controller方法中的BindingResult失效，方法直接抛出异常，因此选择在异常捕获类中处理
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            return new AjaxResult(false, bindingResult.getFieldError().getDefaultMessage());
        } else {
            logger.error("系统异常", e);
            return new AjaxResult(false, "系统异常，请联系管理员！");
        }
    }
}
