package cn.iocoder.oauth.module.system.service.sms.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.framework.common.util.string.StrUtils;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.template.SmsTemplateCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.template.SmsTemplatePageReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsChannelDO;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsTemplateDO;
import cn.iocoder.oauth.module.system.dal.mysql.sms.SmsTemplateMapper;
import cn.iocoder.oauth.module.system.service.sms.SmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class SmsTemplateServiceImpl implements SmsTemplateService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    @Resource
    private SmsTemplateMapper smsTemplateMapper;

    /**
     * 缓存短信模板的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    /**
     * 短信模板缓存
     * key：短信模板编码 {@link SmsTemplateDO#getCode()}
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    private volatile Map<String, SmsTemplateDO> smsTemplateCache;

    @Override
    @PostConstruct
    public void initLocalCache() {
        //获取短信模板
        List<SmsTemplateDO> smsTemplateList = this.loadSmsTemplateIfUpdate( maxUpdateTime );
        if(CollUtil.isEmpty(smsTemplateList)){
           return;
        }
        // 写入缓存
        smsTemplateCache = CollectionUtils.convertMap(smsTemplateList, SmsTemplateDO::getCode);
        maxUpdateTime = CollectionUtils.getMaxValue(smsTemplateList, SmsTemplateDO::getUpdateTime);
        log.info("[initLocalCache][初始化 SmsTemplate 数量为 {}]", smsTemplateList.size());
    }

    private List<SmsTemplateDO> loadSmsTemplateIfUpdate(Date maxUpdateTime){
        //判断是否要更新
        if(maxUpdateTime==null){
            log.info("[loadSmsChannelIfUpdate][首次加载全量短信模板]");
        }else{
            //判断数据库是否有更新渠道的信息
            if(smsTemplateMapper.selectCountByUpdateTimeGt(maxUpdateTime)==0){
                return null;
            }
            log.info("[loadSmsChannelIfUpdate][增量加载全量短信模板]"); log.info("[loadSmsChannelIfUpdate][增量加载全量短信模板]");
        }
        //如果有更新，则从数据库加载所有模板信息
        return smsTemplateMapper.selectList();
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initLocalCache();
    }

    @Override
    public SmsTemplateDO getSmsTemplateByCodeFromCache(String code) {
        return smsTemplateCache.get(code);
    }

    @Override
    public String formatSmsTemplateContent(String content, Map<String, Object> params) {
        return StrUtil.format(content,params);
    }

    @Override
    public SmsTemplateDO getSmsTemplateByCode(String code) {
        return smsTemplateMapper.selectByCode(code);
    }

    @Override
    public Long createSmsTemplate(SmsTemplateCreateReqVO createReqVO) {
        return null;
    }

    @Override
    public SmsTemplateDO getSmsTemplate(Long id) {
        return null;
    }

    @Override
    public List<SmsTemplateDO> getSmsTemplateList(Collection<Long> ids) {
        return null;
    }

    @Override
    public PageResult<SmsTemplateDO> getSmsTemplatePage(SmsTemplatePageReqVO pageReqVO) {
        return null;
    }

    @Override
    public Long countByChannelId(Long channelId) {
        return null;
    }
}
