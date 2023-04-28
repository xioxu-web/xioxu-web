package com.alibaba.csp.sentinel.dashboard.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoxu123
 */
public class FakeAuthServiceImpl implements AuthService<HttpServletRequest>{

    private final Logger logger=LoggerFactory.getLogger(FakeAuthServiceImpl.class);

    public FakeAuthServiceImpl(){
      this.logger.warn("there is no auth, use {} by implementation {}", AuthService.class, this.getClass());
    }

    @Override
    public AuthUser getAuthUser(HttpServletRequest request) {
        return new AuthUserImpl();
    }

    static final class AuthUserImpl implements AuthUser{

        @Override
        public Boolean authTarget(String target, PrivilegeType privilegeType) {
            return true;
        }

        @Override
        public Boolean isSuperUser() {
            return true;
        }

        @Override
        public String getNickName() {
            return "FAKE_NICK_NAME";
        }

        @Override
        public String getLoginName() {
            return "FAKE_LOGIN_NAME";
        }

        @Override
        public String getId() {
            return "FAKE_EMP_ID";
        }
    }
}
