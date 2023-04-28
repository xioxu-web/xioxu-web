package com.example.middleware_design.enums;

/**
 * @author xiaoxu123
 */

public enum ResponseEnum {
    Success("200","000000","请求成功"),
    FAIL("404", "100000","请求失败"),
    SERVER_ERROR("500","300000","服务器内部错误"),
    RATE_LIMIT("401","400000","限流中"),
    FAIL_BY_PARAMS("400","200000","请求参数异常"),
    ;
    public String status;
    public String code;
    public String message;

    ResponseEnum(String s, String s1, String s2) {
        this.status = s;
        this.code = s1;
        this.message = s2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
