package cn.iocoder.oauth.module.infra.service.logger;

import cn.hutool.core.util.RandomUtil;
import cn.iocoder.oauth.framework.common.enums.UserTypeEnum;
import cn.iocoder.oauth.framework.apilog.core.service.dto.ApiAccessLogCreateReqDTO;
import cn.iocoder.oauth.framework.common.enums.UserTypeEnum;
import cn.iocoder.oauth.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.object.ObjectUtils;
import cn.iocoder.oauth.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.oauth.framework.test.core.util.RandomUtils;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExportReqVO;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import cn.iocoder.oauth.module.infra.dal.mysql.logger.ApiAccessLogMapper;
import cn.iocoder.oauth.module.infra.service.logger.Impl.ApiAccessLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import static cn.iocoder.oauth.framework.common.util.date.DateUtils.buildTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(ApiAccessLogServiceImpl.class)
public class ApiAccessLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @Resource
    private ApiAccessLogMapper apiAccessLogMapper;

    @Test
    public void testGetApiAccessLogPage() {
        // 构造测试数据
        long userId = 2233L;
        int userType = UserTypeEnum.ADMIN.getValue();
        String applicationName = "oauth-test";
        String requestUrl = "foo";
        Date beginTime = buildTime(2021, 3, 13);
        int duration = 1000;
        int resultCode = GlobalErrorCodeConstants.SUCCESS.getCode();

        ApiAccessLogDO infApiAccessLogDO = RandomUtils.randomPojo(ApiAccessLogDO.class, dto -> {
            dto.setUserId(userId);
            dto.setUserType(userType);
            dto.setApplicationName(applicationName);
            dto.setRequestUrl(requestUrl);
            dto.setBeginTime(beginTime);
            dto.setDuration(duration);
            dto.setResultCode(resultCode);
        });
        apiAccessLogMapper.insert(infApiAccessLogDO);
        // 构造调用参数
        ApiAccessLogPageReqVO reqVO = new ApiAccessLogPageReqVO();
        reqVO.setUserId(userId);
        reqVO.setUserType(userType);
        reqVO.setApplicationName(applicationName);
        reqVO.setRequestUrl(requestUrl);
        reqVO.setBeginBeginTime(buildTime(2021, 3, 12));
        reqVO.setEndBeginTime(buildTime(2021, 3, 14));
        reqVO.setDuration(duration);
        reqVO.setResultCode(resultCode);

        // 调用service方法
        PageResult<ApiAccessLogDO> pageResult = apiAccessLogService.getApiAccessLogPage(reqVO);

        // 断言，只查到了一条符合条件的
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
    }

    @Test
    public void testGetApiAccessLogList() {
        // 构造测试数据
        long userId = 2233L;
        int userType = UserTypeEnum.ADMIN.getValue();
        String applicationName = "oauth-test";
        String requestUrl = "foo";
        Date beginTime = buildTime( 2021, 3, 13 );
        int duration = 1000;
        int resultCode = GlobalErrorCodeConstants.SUCCESS.getCode();

        ApiAccessLogDO infApiAccessLogDO = RandomUtils.randomPojo( ApiAccessLogDO.class, dto -> {
            dto.setUserId( userId );
            dto.setUserType( userType );
            dto.setApplicationName( applicationName );
            dto.setRequestUrl( requestUrl );
            dto.setBeginTime( beginTime );
            dto.setDuration( duration );
            dto.setResultCode( resultCode );
        } );
        apiAccessLogMapper.insert( infApiAccessLogDO );


        // 构造调用参数
        ApiAccessLogExportReqVO reqVO = new ApiAccessLogExportReqVO();
        reqVO.setUserId( userId );
        reqVO.setUserType( userType );
        reqVO.setApplicationName( applicationName );
        reqVO.setRequestUrl( requestUrl );
        reqVO.setBeginBeginTime( buildTime( 2021, 3, 12 ) );
        reqVO.setEndBeginTime( buildTime( 2021, 3, 14 ) );
        reqVO.setDuration( duration );
        reqVO.setResultCode( resultCode );

        // 调用service方法
        List<ApiAccessLogDO> list = apiAccessLogService.getApiAccessLogList( reqVO );

        // 断言，只查到了一条符合条件的
        assertEquals( 1, list.size() );
    }

    @Test
    public void testCreateApiAccessLogAsync() {
        // 准备参数
        ApiAccessLogCreateReqDTO createDTO = RandomUtils.randomPojo(ApiAccessLogCreateReqDTO.class,
                dto -> dto.setUserType(RandomUtil.randomEle(UserTypeEnum.values()).getValue()));

        // 调用
        apiAccessLogService.createApiAccessLogAsync(createDTO);
        // 断言
        ApiAccessLogDO infApiAccessLogDO = apiAccessLogMapper.selectOne(null);
        assertNotNull(infApiAccessLogDO);

    }


}
