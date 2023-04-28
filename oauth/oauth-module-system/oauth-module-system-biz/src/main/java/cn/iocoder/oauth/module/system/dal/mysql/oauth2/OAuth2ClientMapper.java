package cn.iocoder.oauth.module.system.dal.mysql.oauth2;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.oauth.module.system.controller.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author xiaoxu123
 */
@Mapper
public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientDO> {

    default
    PageResult<OAuth2ClientDO> selectPage(OAuth2ClientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OAuth2ClientDO>()
                .likeIfPresent(OAuth2ClientDO::getName, reqVO.getName())
                .eqIfPresent(OAuth2ClientDO::getStatus, reqVO.getStatus())
                .orderByDesc(OAuth2ClientDO::getId));
    }

    default OAuth2ClientDO selectByClientId(String clientId) {
        return selectOne(OAuth2ClientDO::getClientId, clientId);
    }

    @Select("SELECT COUNT(*) FROM system_oauth2_client WHERE update_time > #{maxUpdateTime}")
    int selectCountByUpdateTimeGt(Date maxUpdateTime);

}
