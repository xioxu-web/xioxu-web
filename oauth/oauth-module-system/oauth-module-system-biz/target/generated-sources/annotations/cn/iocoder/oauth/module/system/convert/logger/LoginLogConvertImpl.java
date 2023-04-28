package cn.iocoder.oauth.module.system.convert.logger;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.oauth.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.iocoder.oauth.module.system.dal.dataobject.logger.LoginLogDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-26T17:05:15+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class LoginLogConvertImpl implements LoginLogConvert {

    @Override
    public PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<LoginLogRespVO> pageResult = new PageResult<LoginLogRespVO>();

        pageResult.setList( loginLogDOListToLoginLogRespVOList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }

    @Override
    public LoginLogDO convert(LoginLogCreateReqDTO bean) {
        if ( bean == null ) {
            return null;
        }

        LoginLogDO loginLogDO = new LoginLogDO();

        loginLogDO.setLogType( bean.getLogType() );
        loginLogDO.setTraceId( bean.getTraceId() );
        loginLogDO.setUserId( bean.getUserId() );
        loginLogDO.setUserType( bean.getUserType() );
        loginLogDO.setUsername( bean.getUsername() );
        loginLogDO.setResult( bean.getResult() );
        loginLogDO.setUserIp( bean.getUserIp() );
        loginLogDO.setUserAgent( bean.getUserAgent() );

        return loginLogDO;
    }

    protected LoginLogRespVO loginLogDOToLoginLogRespVO(LoginLogDO loginLogDO) {
        if ( loginLogDO == null ) {
            return null;
        }

        LoginLogRespVO loginLogRespVO = new LoginLogRespVO();

        loginLogRespVO.setLogType( loginLogDO.getLogType() );
        loginLogRespVO.setTraceId( loginLogDO.getTraceId() );
        loginLogRespVO.setUsername( loginLogDO.getUsername() );
        loginLogRespVO.setResult( loginLogDO.getResult() );
        loginLogRespVO.setUserIp( loginLogDO.getUserIp() );
        loginLogRespVO.setUserAgent( loginLogDO.getUserAgent() );
        loginLogRespVO.setId( loginLogDO.getId() );
        loginLogRespVO.setUserId( loginLogDO.getUserId() );
        loginLogRespVO.setUserType( loginLogDO.getUserType() );
        loginLogRespVO.setCreateTime( loginLogDO.getCreateTime() );

        return loginLogRespVO;
    }

    protected List<LoginLogRespVO> loginLogDOListToLoginLogRespVOList(List<LoginLogDO> list) {
        if ( list == null ) {
            return null;
        }

        List<LoginLogRespVO> list1 = new ArrayList<LoginLogRespVO>( list.size() );
        for ( LoginLogDO loginLogDO : list ) {
            list1.add( loginLogDOToLoginLogRespVO( loginLogDO ) );
        }

        return list1;
    }
}
