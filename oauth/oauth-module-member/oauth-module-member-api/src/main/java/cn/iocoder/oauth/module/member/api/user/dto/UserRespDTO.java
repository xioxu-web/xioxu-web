package cn.iocoder.oauth.module.member.api.user.dto;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * @author xiaoxu123
 */
@Data
public class UserRespDTO {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 帐号状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * 手机
     */
    private String mobile;

}
