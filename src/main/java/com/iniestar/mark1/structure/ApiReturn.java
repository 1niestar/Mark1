package com.iniestar.mark1.structure;

import com.iniestar.mark1.constant.ApiReturnCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiReturn {
    ApiReturnCode returnCode;
    String msg;
    String body;
    String reserved;
}
