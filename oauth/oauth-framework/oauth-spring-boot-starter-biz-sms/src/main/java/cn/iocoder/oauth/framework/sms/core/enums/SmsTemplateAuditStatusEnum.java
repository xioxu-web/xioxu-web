package cn.iocoder.oauth.framework.sms.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信模板的审核状态枚举
 * @author xiaoxu123
 */
@Getter
@AllArgsConstructor
public enum SmsTemplateAuditStatusEnum {

    CHECKING(1),
    SUCCESS(2),
    FAIL(3);

    private final Integer status;

}
