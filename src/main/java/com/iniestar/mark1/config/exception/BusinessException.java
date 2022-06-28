package com.iniestar.mark1.config.exception;

import com.iniestar.mark1.constant.ApiReturnCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    ApiReturnCode apiReturnCode;

    public BusinessException(ApiReturnCode apiReturnCode) {
        super(apiReturnCode.getMsg());
        this.apiReturnCode = apiReturnCode;
    }

    public BusinessException(Throwable e) {
       super(e);
    }
}
