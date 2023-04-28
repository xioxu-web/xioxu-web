package cn.iocoder.oauth.module.member.service.user.Impl;

import cn.hutool.core.util.IdUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.validation.Mobile;
import cn.iocoder.oauth.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.oauth.module.member.dal.mysql.user.MemberUserMapper;
import cn.iocoder.oauth.module.member.service.user.MemberUserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author xiaoxu123
 */
public class MemberUserServiceImpl implements MemberUserService {

    @Resource
    private MemberUserMapper memberUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public MemberUserDO getUserByMobile(String mobile) {
        return memberUserMapper.selectByMobile( mobile );
    }
    @Override
    public MemberUserDO createUserIfAbsent(@Mobile String mobile, String registerIp) {
        MemberUserDO memberUser = memberUserMapper.selectByMobile( mobile );
        if (memberUser != null) {
            return memberUser;
        }
        return this.createUser(mobile,registerIp);
    }

    private MemberUserDO createUser(String mobile,String registerIp){
     //生成密码
        String password= IdUtil.fastUUID();
      //插入用户
        MemberUserDO memberUserDO = new MemberUserDO();
        memberUserDO.setPassword(encodePassword(password));
        memberUserDO.setMobile( mobile);
        memberUserDO.setRegisterIp(registerIp);
        memberUserDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        memberUserMapper.insert(memberUserDO);
        return memberUserDO;
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {

    }

    @Override
    public MemberUserDO getUser(Long id) {
        return null;
    }

    @Override
    public void updateUserNickname(Long userId, String nickname) {

    }

    @Override
    public String updateUserAvatar(Long userId, InputStream inputStream) throws Exception {
        return null;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return false;
    }
}
