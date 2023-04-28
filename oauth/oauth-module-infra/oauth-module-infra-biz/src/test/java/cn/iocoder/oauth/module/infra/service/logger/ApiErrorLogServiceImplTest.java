package cn.iocoder.oauth.module.infra.service.logger;
import cn.iocoder.oauth.module.infra.service.logger.Impl.ApiErrorLogServiceImpl;
import cn.iocoder.oauth.framework.common.enums.UserTypeEnum;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.oauth.framework.test.core.util.RandomUtils;
import cn.iocoder.oauth.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import cn.iocoder.oauth.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import cn.iocoder.oauth.module.infra.dal.mysql.logger.ApiErrorLogMapper;
import cn.iocoder.oauth.module.infra.enums.logger.ApiErrorLogProcessStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import javax.annotation.Resource;
import java.util.Date;
import static cn.iocoder.oauth.framework.common.util.date.DateUtils.buildTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(ApiErrorLogServiceImpl.class)
public class ApiErrorLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ApiErrorLogServiceImpl apiErrorLogService;

    @Resource
    private ApiErrorLogMapper infApiErrorLogMapper;

    @Test
    public void testGetApiErrorLogPage() {
        // 构造测试数据
        long userId = 2233L;
        int userType = UserTypeEnum.ADMIN.getValue();
        String applicationName = "oauth-test";
        String requestUrl = "foo";
        Date beginTime = buildTime(2021, 3, 13);
        int progressStatus = ApiErrorLogProcessStatusEnum.INIT.getStatus();

        ApiErrorLogDO infApiErrorLogDO = RandomUtils.randomPojo(ApiErrorLogDO.class, logDO -> {
            logDO.setUserId(userId);
            logDO.setUserType(userType);
            logDO.setApplicationName(applicationName);
            logDO.setRequestUrl(requestUrl);
            logDO.setExceptionTime(beginTime);
            logDO.setProcessStatus(progressStatus);
        });
        infApiErrorLogMapper.insert(infApiErrorLogDO);
        // 构造调用参数
        ApiErrorLogPageReqVO reqVO = new ApiErrorLogPageReqVO();
        reqVO.setUserId(userId);
        reqVO.setUserType(userType);
        reqVO.setApplicationName(applicationName);
        reqVO.setRequestUrl(requestUrl);
        reqVO.setBeginExceptionTime(buildTime(2021, 3, 12));
        reqVO.setEndExceptionTime(buildTime(2021, 3, 14));
        reqVO.setProcessStatus(progressStatus);

        // 调用service方法
        PageResult<ApiErrorLogDO> pageResult = apiErrorLogService.getApiErrorLogPage(reqVO);

        // 断言，只查到了一条符合条件的
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
    }


}
