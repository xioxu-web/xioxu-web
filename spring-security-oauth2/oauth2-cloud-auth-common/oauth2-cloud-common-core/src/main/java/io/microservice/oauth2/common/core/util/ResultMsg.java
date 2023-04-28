package io.microservice.oauth2.common.core.util;
import io.microservice.oauth2.common.core.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author xiaoxu123
 * 响应体信息
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultMsg<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> ResultMsg<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> ResultMsg<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> ResultMsg<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> ResultMsg<T> failed() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> ResultMsg<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> ResultMsg<T> failed(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> ResultMsg<T> failed(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    private static <T> ResultMsg<T> restResult(T data, int code, String msg) {
        ResultMsg<T> apiResult = new ResultMsg<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }


}
