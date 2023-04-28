package cn.iocoder.oauth.module.system.convert.auth;

import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.oauth.module.system.controller.admin.auth.vo.AuthMenuRespVO;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.MenuDO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-26T17:05:15+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class AuthConvertImpl implements AuthConvert {

    @Override
    public AuthLoginRespVO convert(OAuth2AccessTokenDO bean) {
        if ( bean == null ) {
            return null;
        }

        AuthLoginRespVO authLoginRespVO = new AuthLoginRespVO();

        authLoginRespVO.setUserId( bean.getUserId() );
        authLoginRespVO.setAccessToken( bean.getAccessToken() );
        authLoginRespVO.setRefreshToken( bean.getRefreshToken() );
        authLoginRespVO.setExpiresTime( bean.getExpiresTime() );

        return authLoginRespVO;
    }

    @Override
    public AuthMenuRespVO convertTreeNode(MenuDO menu) {
        if ( menu == null ) {
            return null;
        }

        AuthMenuRespVO authMenuRespVO = new AuthMenuRespVO();

        authMenuRespVO.setId( menu.getId() );
        authMenuRespVO.setParentId( menu.getParentId() );
        authMenuRespVO.setName( menu.getName() );
        authMenuRespVO.setPath( menu.getPath() );
        authMenuRespVO.setComponent( menu.getComponent() );
        authMenuRespVO.setIcon( menu.getIcon() );
        authMenuRespVO.setVisible( menu.getVisible() );
        authMenuRespVO.setKeepAlive( menu.getKeepAlive() );

        return authMenuRespVO;
    }
}
