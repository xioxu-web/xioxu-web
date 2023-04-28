package cn.iocoder.oauth.module.system.service.sms;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.util.collection.ArrayUtils;
import cn.iocoder.oauth.framework.sms.core.client.SmsClientFactory;
import cn.iocoder.oauth.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsChannelDO;
import cn.iocoder.oauth.module.system.dal.mysql.sms.SmsChannelMapper;
import cn.iocoder.oauth.module.system.service.mq.producer.SmsProducer;
import cn.iocoder.oauth.module.system.service.sms.Impl.SmsChannelServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import javax.annotation.Resource;
import java.util.Date;
import java.util.function.Consumer;
import static cn.hutool.core.util.RandomUtil.randomEle;
import static cn.iocoder.oauth.framework.common.util.date.DateUtils.max;
import static cn.iocoder.oauth.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.oauth.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

@Import(SmsChannelServiceImpl.class)
public class SmsChannelServiceTest extends BaseDbUnitTest {

    @Resource
    private SmsChannelServiceImpl smsChannelService;

    @Resource
    private SmsChannelMapper smsChannelMapper;

    @MockBean
    private SmsClientFactory smsClientFactory;
    @MockBean
    private SmsTemplateService smsTemplateService;
    @MockBean
    private SmsProducer smsProducer;

    @Test
    public void testInitLocalCache_success() {
        // mock 数据
        SmsChannelDO smsChannelDO01 = randomSmsChannelDO();
        smsChannelMapper.insert(smsChannelDO01);
        SmsChannelDO smsChannelDO02 = randomSmsChannelDO();
        smsChannelMapper.insert(smsChannelDO02);
        // 调用
        smsChannelService.initSmsClients();
        // 校验 maxUpdateTime 属性
        Date maxUpdateTime = (Date) BeanUtil.getFieldValue(smsChannelService, "maxUpdateTime");
        assertEquals(max(smsChannelDO01.getUpdateTime(), smsChannelDO02.getUpdateTime()), maxUpdateTime);
        // 校验调用
        Mockito.verify(smsClientFactory, times(1)).createOrUpdateSmsClient(
                argThat(properties -> isPojoEquals(smsChannelDO01, properties)));
        Mockito.verify(smsClientFactory, times(1)).createOrUpdateSmsClient(
                argThat(properties -> isPojoEquals(smsChannelDO02, properties)));
    }

    @Test
    public void testCreateSmsChannel_success() {
        // 准备参数
        SmsChannelCreateReqVO reqVO = randomPojo(SmsChannelCreateReqVO.class, o -> o.setStatus(randomCommonStatus()));

        // 调用
        Long smsChannelId = smsChannelService.createSmsChannel(reqVO);
        // 断言
        assertNotNull(smsChannelId);
        // 校验记录的属性是否正确
        SmsChannelDO smsChannel = smsChannelMapper.selectById(smsChannelId);
        assertPojoEquals(reqVO, smsChannel);
        //校验调用
        Mockito.verify(smsProducer, times(1)).sendSmsChannelRefreshMessage();
    }

    // ========== 随机对象 ==========
    @SafeVarargs
    private static SmsChannelDO randomSmsChannelDO(Consumer<SmsChannelDO>... consumers) {
        Consumer<SmsChannelDO> consumer = (o) -> {
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // 保证 status 的范围
        };
        return randomPojo(SmsChannelDO.class, ArrayUtils.append(consumer, consumers));
    }



}
