package com.iniestar.mark1.constant;

import lombok.Getter;

@Getter
public enum ApiReturnCode {

    SUCCESS("0000", "SUCCESS"),

    ERROR_ACCESS_DB("1000", "failed taccess database"),
    ERROR_DUPLICATION_DATA("1001", "[Error] duplication data"),

    ERROR_API_INVALID_PARAM("2000", "[Error] api invalid param"),
    ERROR_API_NOT_EXIST("2001", "[Error] api not exist"),
    ERROR_API_INVALID_REQUEST_KEY("2002", "[Error] api invalid request key"),

    ERROR_INTERNAL_SERVER("9999", "[Error] internal server");

    ApiReturnCode(String szCode, String msg) {
        this.szCode = szCode;
        this.msg = msg;
    }

    String szCode;
    String msg;
}
