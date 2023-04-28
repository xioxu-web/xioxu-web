package cn.iocoder.oauth.module.system.dal.mysql.oauth2;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.oauth.module.system.controller.admin.oauth2.vo.token.OAuth2AccessTokenPageReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
public interface OAuth2AccessTokenMapper extends BaseMapperX<OAuth2AccessTokenDO> {

    default OAuth2AccessTokenDO selectByAccessToken(String accessToken) {
        return selectOne(OAuth2AccessTokenDO::getAccessToken, accessToken);
    }

    default
    List<OAuth2AccessTokenDO> selectListByRefreshToken(String refreshToken) {
        return selectList(OAuth2AccessTokenDO::getRefreshToken, refreshToken);
    }

    default
    PageResult<OAuth2AccessTokenDO> selectPage(OAuth2AccessTokenPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OAuth2AccessTokenDO>()
                .eqIfPresent(OAuth2AccessTokenDO::getUserId, reqVO.getUserId())
                .eqIfPresent(OAuth2AccessTokenDO::getUserType, reqVO.getUserType())
                .likeIfPresent(OAuth2AccessTokenDO::getClientId, reqVO.getClientId())
                .gt(OAuth2AccessTokenDO::getExpiresTime, new Date())
                .orderByDesc(OAuth2AccessTokenDO::getId));
    }

}
