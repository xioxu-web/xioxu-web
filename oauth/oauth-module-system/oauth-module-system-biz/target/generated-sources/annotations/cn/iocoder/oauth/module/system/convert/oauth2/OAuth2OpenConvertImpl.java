package cn.iocoder.oauth.module.system.convert.oauth2;

import cn.iocoder.oauth.module.system.controller.admin.oauth2.vo.open.OAuth2OpenAccessTokenRespVO;
import cn.iocoder.oauth.module.system.controller.admin.oauth2.vo.open.OAuth2OpenCheckTokenRespVO;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-26T17:05:15+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class OAuth2OpenConvertImpl implements OAuth2OpenConvert {

    @Override
    public OAuth2OpenAccessTokenRespVO convert0(OAuth2AccessTokenDO bean) {
        if ( bean == null ) {
            return null;
        }

        OAuth2OpenAccessTokenRespVO oAuth2OpenAccessTokenRespVO = new OAuth2OpenAccessTokenRespVO();

        oAuth2OpenAccessTokenRespVO.setAccessToken( bean.getAccessToken() );
        oAuth2OpenAccessTokenRespVO.setRefreshToken( bean.getRefreshToken() );

        return oAuth2OpenAccessTokenRespVO;
    }

    @Override
    public OAuth2OpenCheckTokenRespVO convert3(OAuth2AccessTokenDO bean) {
        if ( bean == null ) {
            return null;
        }

        OAuth2OpenCheckTokenRespVO oAuth2OpenCheckTokenRespVO = new OAuth2OpenCheckTokenRespVO();

        oAuth2OpenCheckTokenRespVO.setUserId( bean.getUserId() );
        oAuth2OpenCheckTokenRespVO.setUserType( bean.getUserType() );
        oAuth2OpenCheckTokenRespVO.setClientId( bean.getClientId() );
        List<String> list = bean.getScopes();
        if ( list != null ) {
            oAuth2OpenCheckTokenRespVO.setScopes( new ArrayList<String>( list ) );
        }
        oAuth2OpenCheckTokenRespVO.setAccessToken( bean.getAccessToken() );

        return oAuth2OpenCheckTokenRespVO;
    }
}
