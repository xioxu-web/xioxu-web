package cn.iocoder.oauth.framework.pay.core.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 统一退款 Response DTO
 * @author xiaoxu123
 */
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayRefundUnifiedRespDTO {

    /**
     * 渠道退款单编号
     */
    private String channelRefundId;
}
