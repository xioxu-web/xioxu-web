package com.orc.rpc.common.model;

import com.orc.rpc.common.constants.RpcStatusEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * rpc 响应对象
 * @author xiaoxu123
 */
public class RpcResponse<T> implements Serializable {
    private String requestId;

    private Map<String, String> headers = new HashMap<>();

    private T data;

    private Exception exception;

    private RpcStatusEnum rpcStatus;

    public RpcResponse() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RpcResponse(RpcStatusEnum rpcStatus) {
        this.rpcStatus = rpcStatus;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public RpcStatusEnum getRpcStatus() {
        return rpcStatus;
    }

    public void setRpcStatus(RpcStatusEnum rpcStatus) {
        this.rpcStatus = rpcStatus;
    }
}
