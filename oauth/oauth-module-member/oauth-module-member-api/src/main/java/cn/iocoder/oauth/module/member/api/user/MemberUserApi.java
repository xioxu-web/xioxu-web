package cn.iocoder.oauth.module.member.api.user;

import cn.iocoder.oauth.module.member.api.user.dto.UserRespDTO;

/**
 * 会员业务接口
 * @author xiaoxu123
 */
public interface MemberUserApi {

    /**
     * 获得会员用户信息
     *
     * @param id 用户编号
     * @return 用户信息
     */
    UserRespDTO getUser(Long id);

}
