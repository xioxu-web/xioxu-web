package cn.iocoder.oauth.framework.sms.core.client.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 短信发送 Response DTO
 *
 * @author admin
 */
@Data
@Accessors(chain = true)
public class SmsSendRespDTO {

    /**
     * 短信 API 发送返回的序号
     */
    private String serialNo;

}
