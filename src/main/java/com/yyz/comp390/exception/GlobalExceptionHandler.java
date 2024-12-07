package com.yyz.comp390.exception;

import com.yyz.comp390.entity.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ApiResult exceptionHandler(BaseException ex){
        log.error("Error message: {}", ex.getMessage());
        return ApiResult.error(ex.getMessage());
    }

}
