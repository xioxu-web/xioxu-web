package io.microservices.shop.common.resp;
import io.microservices.shop.common.constant.StatusCode;
import lombok.Data;
import java.io.Serializable;
/**
 * @author xiaoxu123
 */
@Data
public class Result<T> implements Serializable {

    private boolean flag;

    private Integer code;

    private String message;

    private T data;

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = (T) data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "操作成功!";
    }

}
