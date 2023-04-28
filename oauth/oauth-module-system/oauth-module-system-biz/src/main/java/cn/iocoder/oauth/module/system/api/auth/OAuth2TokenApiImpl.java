package cn.iocoder.oauth.module.system.api.auth;
import cn.iocoder.oauth.module.system.api.auth.dto.OAuth2AccessTokenCheckRespDTO;
import cn.iocoder.oauth.module.system.api.auth.dto.OAuth2AccessTokenCreateReqDTO;
import cn.iocoder.oauth.module.system.api.auth.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.oauth.module.system.convert.auth.OAuth2TokenConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.oauth.module.system.service.oauth2.OAuth2TokenService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author xiaoxu123
 */
@Service
public class OAuth2TokenApiImpl implements OAuth2TokenApi{

    @Resource
    private OAuth2TokenService oauth2TokenService;


    @Override
    public
    OAuth2AccessTokenRespDTO createAccessToken(@Valid OAuth2AccessTokenCreateReqDTO reqDTO) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(
                reqDTO.getUserId(), reqDTO.getUserType(), reqDTO.getClientId(), reqDTO.getScopes());
        return OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

    @Override
    public
    OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken) {
        return OAuth2TokenConvert.INSTANCE.convert(oauth2TokenService.checkAccessToken(accessToken));
    }

    @Override
    public
    OAuth2AccessTokenRespDTO deleteAccessToken(String accessToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(accessToken);
        return OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

    @Override
    public
    OAuth2AccessTokenRespDTO refreshAccessToken(String refreshAccessToken, String clientId) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshAccessToken, clientId);
        return OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO);
    }
}
