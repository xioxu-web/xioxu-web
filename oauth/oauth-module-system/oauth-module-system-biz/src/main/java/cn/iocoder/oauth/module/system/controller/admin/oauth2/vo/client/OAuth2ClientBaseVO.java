package cn.iocoder.oauth.module.system.controller.admin.oauth2.vo.client;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.oauth.framework.common.util.json.JsonUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * OAuth2Client基准类
 * @author xiaoxu123
 */
@Data
public class OAuth2ClientBaseVO implements Serializable {

    @ApiModelProperty(value = "客户端编号", required = true, example = "tudou")
    @NotNull(message = "客户端编号不能为空")
    private String clientId;

    @ApiModelProperty(value = "客户端密钥", required = true, example = "fan")
    @NotNull(message = "客户端密钥不能为空")
    private String secret;

    @ApiModelProperty(value = "应用名", required = true, example = "土豆")
    @NotNull(message = "应用名不能为空")
    private String name;

    @ApiModelProperty(value = "应用图标", required = true, example = "https://www.iocoder.cn/xx.png")
    @NotNull(message = "应用图标不能为空")
    @URL(message = "应用图标的地址不正确")
    private String logo;

    @ApiModelProperty(value = "应用描述", example = "我是一个应用")
    private String description;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "参见 CommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "访问令牌的有效期", required = true, example = "8640")
    @NotNull(message = "访问令牌的有效期不能为空")
    private Integer accessTokenValiditySeconds;

    @ApiModelProperty(value = "刷新令牌的有效期", required = true, example = "8640000")
    @NotNull(message = "刷新令牌的有效期不能为空")
    private Integer refreshTokenValiditySeconds;

    @ApiModelProperty(value = "可重定向的 URI 地址", required = true, example = "https://www.iocoder.cn")
    @NotNull(message = "可重定向的 URI 地址不能为空")
    private List<@NotEmpty(message = "重定向的 URI 不能为空")
    @URL(message = "重定向的 URI 格式不正确") String> redirectUris;

    @ApiModelProperty(value = "授权类型", required = true, example = "password", notes = "参见 OAuth2GrantTypeEnum 枚举")
    @NotNull(message = "授权类型不能为空")
    private List<String> authorizedGrantTypes;

    @ApiModelProperty(value = "授权范围", example = "user_info")
    private List<String> scopes;

    @ApiModelProperty(value = "自动通过的授权范围", example = "user_info")
    private List<String> autoApproveScopes;

    @ApiModelProperty(value = "权限", example = "system:user:query")
    private List<String> authorities;

    @ApiModelProperty(value = "资源", example = "1024")
    private List<String> resourceIds;

    @ApiModelProperty(value = "附加信息", example = "{yunai: true}")
    private String additionalInformation;

    @AssertTrue(message = "附加信息必须是 JSON 格式")
    public boolean isAdditionalInformationJson() {
        return StrUtil.isEmpty(additionalInformation) || JsonUtils.isJson(additionalInformation);
    }
}
