package com.iniestar.mark1.config.handler;

import com.iniestar.mark1.config.exception.BusinessException;
import com.iniestar.mark1.config.response.ApiErrorResponse;
import com.iniestar.mark1.constant.ApiReturnCode;
import com.iniestar.mark1.controller.ext.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class CustomApiExceptionHandler {

    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ApiResult response = new ApiResult(ApiReturnCode.ERROR_API_INVALID_PARAM, e.getMessage());
        return ApiErrorResponse.toResponseEntity(response);
    }


    /**
     *  javax.sql.SQLException
     *  DB에 접근할 수 없는 환경이거나 접근이 불가능할 경우 발생
     */
    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<ApiErrorResponse> handleSQLException(SQLException e) {
        log.error("handleSQLException", e);
        final ApiResult response = new ApiResult(ApiReturnCode.ERROR_ACCESS_DB, e.getMessage());
        return ApiErrorResponse.toResponseEntity(response);
    }

    /**
     *  org.springframework.web.HttpMediaTypeException
     *  Method 타입이 맞지 않을 경우 발생
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    protected ResponseEntity<ApiErrorResponse> handleHttpMediaTypeException(HttpMediaTypeException e) {
        log.error("handleHttpMediaTypeException", e);
        final ApiResult response = new ApiResult(ApiReturnCode.ERROR_API_INVALID_PARAM, e.getMessage());
        return ApiErrorResponse.toResponseEntity(response);
    }


    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResult> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ApiResult response = new ApiResult(ApiReturnCode.ERROR_API_INVALID_PARAM, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResult> handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);
        final ApiResult response = new ApiResult(e.getApiReturnCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResult> handleException(Exception e) {
        log.error("handleException", e);
        final ApiResult response = new ApiResult(ApiReturnCode.ERROR_INTERNAL_SERVER, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

