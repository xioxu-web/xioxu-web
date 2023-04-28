package com.orc.event;

/**
 * 自定义泛型事件调度
 * @author xiaoxu123
 */
public class GenericEvent<T> {

    private T data;

    private Boolean success;

    public GenericEvent(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    public GenericEvent() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
