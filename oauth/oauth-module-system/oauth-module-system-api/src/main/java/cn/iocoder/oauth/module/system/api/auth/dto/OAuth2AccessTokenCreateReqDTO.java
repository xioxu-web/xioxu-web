package cn.iocoder.oauth.module.system.api.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * OAuth2.0 访问令牌创建 Request DTO
 * @author xiaoxu123
 */
@Data
public class OAuth2AccessTokenCreateReqDTO {
    /**
     * 用户编号
     */
    @NotNull(message ="用户编号不能为空")
    private Long userId;

    /**
     * 用户类型
     */
    @NotNull(message ="用户类型不能为空")
    private Integer userType;

    /**
     *客户端编号
     */
    @NotNull(message ="客户端编号不能为空")
    private String clientId;

    /**
     * 授权范围
     */
    private List<String> scopes;

}