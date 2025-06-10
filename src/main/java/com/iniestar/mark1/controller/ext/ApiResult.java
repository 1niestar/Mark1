package com.iniestar.mark1.controller.ext;

import com.iniestar.mark1.constant.ApiReturnCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult {

    String code;
    String msg;

    public ApiResult(ApiReturnCode apiReturnCode) {
        this.code = apiReturnCode.getSzCode();
        this.msg = apiReturnCode.getMsg();
    }

    public ApiResult(ApiReturnCode apiReturnCode, String msg) {
        this.code = apiReturnCode.getSzCode();
        this.msg = msg;
    }
}
