package cn.iocoder.oauth.module.system.service.sms.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.framework.sms.core.client.SmsClientFactory;
import cn.iocoder.oauth.framework.sms.core.property.SmsChannelProperties;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelPageReqVO;
import cn.iocoder.oauth.module.system.convert.sms.SmsChannelConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsChannelDO;
import cn.iocoder.oauth.module.system.dal.mysql.sms.SmsChannelMapper;
import cn.iocoder.oauth.module.system.service.sms.SmsChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;
/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class SmsChannelServiceImpl implements SmsChannelService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    @Resource
    private SmsChannelMapper smsChannelMapper;

    @Resource
    private
    SmsClientFactory smsClientFactory;

    /**
     * 缓存渠道的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Override
    @PostConstruct
    public void initSmsClients() {
        //获取短信渠道
        List<SmsChannelDO> smsChannels = this.loadSmsChannelIfUpdate( maxUpdateTime );
        if(CollUtil.isEmpty(smsChannels)){
            return;
        }
        // 创建或更新短信 Client
        List<SmsChannelProperties> smsChannelProperties = SmsChannelConvert.INSTANCE.convertList02(smsChannels);
        smsChannelProperties.forEach(properties->smsClientFactory.createOrUpdateSmsClient(properties));
        // 写入缓存
        maxUpdateTime = CollectionUtils.getMaxValue(smsChannels, SmsChannelDO::getUpdateTime);
        log.info("[initSmsClients][初始化 SmsChannel 数量为 {}]", smsChannels.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initSmsClients();
    }

    @Override
    public Long createSmsChannel(@Valid SmsChannelCreateReqVO createReqVO) {
        // 插入
        SmsChannelDO smsChannelDO = SmsChannelConvert.INSTANCE.convert( createReqVO );
        smsChannelMapper.insert(smsChannelDO);
        //返回
        return smsChannelDO.getId();
    }

    private List<SmsChannelDO> loadSmsChannelIfUpdate(Date maxUpdateTime){
      //判断是否要更新
      if(maxUpdateTime==null){
          log.info("[loadSmsChannelIfUpdate][首次加载全量短信渠道]");
      }else{
      //判断数据库是否有更新渠道的信息
      if(smsChannelMapper.selectCountByUpdateTimeGt(maxUpdateTime)==0){
         return null;
      }
          log.info("[loadSmsChannelIfUpdate][增量加载全量短信渠道]"); log.info("[loadSmsChannelIfUpdate][增量加载全量短信渠道]");
      }
      //如果有更新，则从数据库加载所有渠道信息
        return smsChannelMapper.selectList();
    }

    @Override
    public SmsChannelDO getSmsChannel(Long id) {
        return smsChannelMapper.selectById(id);
    }

    @Override
    public List<SmsChannelDO> getSmsChannelList(Collection<Long> ids) {
        return smsChannelMapper.selectBatchIds(ids);
    }

    @Override
    public List<SmsChannelDO> getSmsChannelList() {
        return smsChannelMapper.selectList();
    }


    @Override
    public PageResult<SmsChannelDO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO) {
        return smsChannelMapper.selectPage(pageReqVO);
    }
}
