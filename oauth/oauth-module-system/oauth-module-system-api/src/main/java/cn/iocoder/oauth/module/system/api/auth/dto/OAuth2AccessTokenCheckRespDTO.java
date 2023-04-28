package cn.iocoder.oauth.module.system.api.auth.dto;

import lombok.Data;

import java.util.List;

/**
 * OAuth2.0 校验令牌创建 Response DTO
 * @author xiaoxu123
 */
@Data
public class OAuth2AccessTokenCheckRespDTO {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 授权范围的数组
     */
    private List<String> scopes;

}
