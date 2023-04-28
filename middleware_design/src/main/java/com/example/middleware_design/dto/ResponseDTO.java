package com.example.middleware_design.dto;

/**
 * @author xiaoxu123
 */
public class ResponseDTO<T> {
    /**
     * 统一返回码
     */
    public String code;

    /**
     * 统一错误消息
     */
    public String message;

    /**
     * 统一返回状态
     */
    public String success;

    /**
     * 结果对象
     */
    public T data;


    public ResponseDTO(String code, String message,String success, T data) {
        this.code = code;
        this.message=message;
        this.success=success;
        this.data = data;
    }

    public ResponseDTO() {

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

    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
