package cn.iocoder.oauth.module.pay.service.merchant.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.framework.pay.core.client.PayClientConfig;
import cn.iocoder.oauth.framework.pay.core.client.PayClientFactory;
import cn.iocoder.oauth.framework.pay.core.enums.PayChannelEnum;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.channel.PayChannelCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.channel.PayChannelPageReqVO;
import cn.iocoder.oauth.module.pay.convert.channel.PayChannelConvert;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayChannelDO;
import cn.iocoder.oauth.module.pay.dal.mysql.merchant.PayChannelMapper;
import cn.iocoder.oauth.module.pay.enums.ErrorCodeConstants;
import cn.iocoder.oauth.module.pay.service.merchant.PayChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.oauth.module.pay.enums.ErrorCodeConstants.CHANNEL_EXIST_SAME_CHANNEL_ERROR;
import static cn.iocoder.oauth.module.pay.enums.ErrorCodeConstants.CHANNEL_NOT_EXISTS;

/**
 * 支付渠道 Service 实现类
 *
 * @author admin
 */
@Service
@Slf4j
@Validated
public class PayChannelServiceImpl implements PayChannelService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 缓存菜单的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Resource
    private PayClientFactory payClientFactory;

    @Resource
    private PayChannelMapper channelMapper;

    @Resource
    private Validator validator;

    @Resource
    @Lazy // 注入自己，所以延迟加载
    private PayChannelService self;

    @Override
    @PostConstruct
    public void initPayClients() {
        // 获取支付渠道，如果有更新
        List<PayChannelDO> payChannels = loadPayChannelIfUpdate(maxUpdateTime);
        if (CollUtil.isEmpty(payChannels)) {
            return;
        }

        // 创建或更新支付 Client
        payChannels.forEach(payChannel -> payClientFactory.createOrUpdatePayClient(payChannel.getId(),
                payChannel.getCode(), payChannel.getConfig()));

        // 写入缓存
        maxUpdateTime = CollectionUtils.getMaxValue(payChannels, PayChannelDO::getUpdateTime);
        log.info("[initPayClients][初始化 PayChannel 数量为 {}]", payChannels.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        self.initPayClients();
    }

    /**
     * 如果支付渠道发生变化，从数据库中获取最新的全量支付渠道。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前支付渠道的最大更新时间
     * @return 支付渠道列表
     */
    private List<PayChannelDO> loadPayChannelIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadPayChannelIfUpdate][首次加载全量支付渠道]");
        } else { // 判断数据库中是否有更新的支付渠道
            if (channelMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
                return null;
            }
            log.info("[loadPayChannelIfUpdate][增量加载全量支付渠道]");
        }
        // 第二步，如果有更新，则从数据库加载所有支付渠道
        return channelMapper.selectList();
    }

    /**
     * 根据条件获取渠道
     *
     * @param merchantId 商户编号
     * @param appid      应用编号
     * @param code       渠道编码
     * @return 数量
     */
    @Override
    public PayChannelDO getChannelByConditions(Long merchantId, Long appid, String code) {
        return this.channelMapper.selectOne(merchantId, appid, code);
    }

    @Override
    public Long createChannel(PayChannelCreateReqVO reqVO) {
        // 断言是否有重复的
        PayChannelDO channelDO = this.getChannelByConditions(reqVO.getMerchantId(), reqVO.getAppId(), reqVO.getCode());
        if (ObjectUtil.isNotNull(channelDO)) {
            throw exception(CHANNEL_EXIST_SAME_CHANNEL_ERROR);
        }
        // 新增渠道
        PayChannelDO channel = PayChannelConvert.INSTANCE.convert(reqVO);
        settingConfigAndCheckParam(channel, reqVO.getConfig());
        channelMapper.insert(channel);
        return channel.getId();
    }


    private void validateChannelExists(Long id) {
        if (channelMapper.selectById(id) == null) {
            throw exception(CHANNEL_NOT_EXISTS);
        }
    }

    @Override
    public PayChannelDO getChannel(Long id) {
        return channelMapper.selectById(id);
    }

    @Override
    public List<PayChannelDO> getChannelList(Collection<Long> ids) {
        return channelMapper.selectBatchIds(ids);
    }

    @Override
    public
    PageResult<PayChannelDO> getChannelPage(PayChannelPageReqVO pageReqVO) {
        return channelMapper.selectPage(pageReqVO);
    }


    /**
     * 根据支付应用ID集合获得支付渠道列表
     *
     * @param appIds 应用编号集合
     * @return 支付渠道列表
     */
    @Override
    public List<PayChannelDO> getChannelListByAppIds(Collection<Long> appIds) {
        return channelMapper.getChannelListByAppIds(appIds);
    }


    /**
     * 设置渠道配置以及参数校验
     *
     * @param channel   渠道
     * @param configStr 配置
     */
    private void settingConfigAndCheckParam(PayChannelDO channel, String configStr) {
        // 得到这个渠道是微信的还是支付宝的
        Class<? extends PayClientConfig> payClass = PayChannelEnum.getByCode(channel.getCode()).getConfigClass();
        if (ObjectUtil.isNull(payClass)) {
            throw exception( CHANNEL_NOT_EXISTS );
        }

        PayClientConfig config = JSONUtil.toBean(configStr, payClass);

        // 验证参数
        config.validate(validator);
        channel.setConfig(config);
    }

    @Override
    public PayChannelDO validPayChannel(Long id) {
        PayChannelDO channel = channelMapper.selectById(id);
        this.validPayChannel(channel);
        return channel;
    }

    @Override
    public PayChannelDO validPayChannel(Long appId, String code) {
        PayChannelDO channel = channelMapper.selectByAppIdAndCode(appId, code);
        this.validPayChannel(channel);
        return channel;
    }

    private void validPayChannel(PayChannelDO channel) {
        if (channel == null) {
            throw ServiceExceptionUtil.exception( ErrorCodeConstants.PAY_CHANNEL_NOT_FOUND);
        }
        if (CommonStatusEnum.DISABLE.getStatus().equals(channel.getStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PAY_CHANNEL_IS_DISABLE);
        }
    }
}
