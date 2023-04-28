package cn.iocoder.oauth.module.system.dal.mysql.oauth2;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2RefreshTokenDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoxu123
 */
@Mapper
public interface OAuth2RefreshTokenMapper extends BaseMapperX<OAuth2RefreshTokenDO> {

    default
    OAuth2RefreshTokenDO selectByRefreshToken(String refreshToken) {
        return selectOne(OAuth2RefreshTokenDO::getRefreshToken,refreshToken);
    }

    default int deleteByRefreshToken(String refreshToken) {
        return delete(new LambdaQueryWrapperX<OAuth2RefreshTokenDO>()
                .eq(OAuth2RefreshTokenDO::getRefreshToken, refreshToken));
    }

}
