package cn.iocoder.oauth.framework.pay.core.client.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author xiaoxu123
 */
@Data
@ToString
@Builder
public class PayNotifyDataDTO {

    /**
     *  HTTP 回调接口的 request body
     */
    private String body;


    /**
     * HTTP 回调接口 content type 为 application/x-www-form-urlencoded 的所有参数
     */
    private Map<String,String> params;
}
