package cn.iocoder.oauth.module.member.dal.mysql.user;
import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.module.member.dal.dataobject.user.MemberUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员 User Mapper
 *
 * @author admin
 */
@Mapper
public interface MemberUserMapper extends BaseMapperX<MemberUserDO> {

    default MemberUserDO selectByMobile(String mobile) {
        return selectOne(MemberUserDO::getMobile, mobile);
    }

}
