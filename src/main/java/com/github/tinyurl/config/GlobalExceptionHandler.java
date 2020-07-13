package com.github.tinyurl.config;

import com.github.tinyurl.constant.ErrorCode;
import com.github.tinyurl.domain.Response;
import com.github.tinyurl.exception.TinyUrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/09
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理应用程序抛出的异常
     * @param e 异常实例
     * @return 错误提示信息
     */
    @ResponseBody
    @ExceptionHandler({TinyUrlException.class})
    public Response handleTinyUrlException(TinyUrlException e) {
        log.error("", e);
        return Response.failure(e.getErrorCode());
    }

    /**
     * 处理其他错误
     * @param e 异常实例
     * @return 错误提示信息
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Response handleCommonException(Exception e) {
        log.error("Unknown error", e);
        return Response.failure(ErrorCode.SYSTEM_ERROR);
    }


}
