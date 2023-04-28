package cn.iocoder.oauth.module.system.dal.mysql.oauth2;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.module.system.dal.dataobject.oauth2.OAuth2CodeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoxu123
 */
@Mapper
public interface OAuth2CodeMapper extends BaseMapperX<OAuth2CodeDO> {


    default OAuth2CodeDO selectbyCode(String code) {
        return selectOne(OAuth2CodeDO::getCode,code);
    }
}
